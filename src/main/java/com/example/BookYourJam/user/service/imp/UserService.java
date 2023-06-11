package com.example.BookYourJam.user.service.imp;

import com.example.BookYourJam.common.exception.BaseException;
import com.example.BookYourJam.user.enums.UserRole;
import com.example.BookYourJam.user.model.entity.UserAccount;
import com.example.BookYourJam.user.model.request.SignUpRequest;
import com.example.BookYourJam.user.repository.UserRepository;
import com.example.BookYourJam.user.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void signUpUser(SignUpRequest signUpRequest) {

        validateRequest(signUpRequest);

        UserAccount userAccount = prepareUserModel(signUpRequest);

        userRepository.save(userAccount);
    }

    @Override
    public Optional<UserAccount> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    private void validateRequest(SignUpRequest signUpRequest) {
        if (Objects.isNull(signUpRequest) ||
                StringUtils.isEmpty(signUpRequest.getEmail()) ||
                StringUtils.isEmpty(signUpRequest.getPhoneNumber()) ||
                StringUtils.isEmpty(signUpRequest.getPassword()) ||
                StringUtils.isEmpty(signUpRequest.getUserName()) ||
                !isValidUserRole(signUpRequest.getUserRole())) {

            throw new BaseException(HttpStatus.BAD_REQUEST.value(), "Request body is not valid");
        }

        if (isEmailExists(signUpRequest.getEmail())) {
            throw new BaseException(HttpStatus.BAD_REQUEST.value(), "Email ID already used");
        }

        if (isUserNameExists(signUpRequest.getUserName())) {
            throw new BaseException(HttpStatus.BAD_REQUEST.value(), "Username already used");
        }

        if (isPhoneNumberExists(signUpRequest.getPhoneNumber())) {
            throw new BaseException(HttpStatus.BAD_REQUEST.value(), "Phone Number already used");
        }
    }

    private UserAccount prepareUserModel(SignUpRequest signUpRequest) {
        UserAccount userAccount = new UserAccount();

        userAccount.setUserCreationDate(new Date());
        userAccount.setUserName(signUpRequest.getUserName());
        userAccount.setEmail(signUpRequest.getEmail());
        userAccount.setPhoneNumber(signUpRequest.getPhoneNumber());
        userAccount.setRole(signUpRequest.getUserRole());
        userAccount.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

        return userAccount;
    }

    private boolean isEmailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    private boolean isUserNameExists(String userName) {
        return userRepository.existsByUserName(userName);
    }

    private boolean isPhoneNumberExists(String phoneNumber) {
        return userRepository.existsByPhoneNumber(phoneNumber);
    }

    private boolean isValidUserRole(int userRole) {
        for (UserRole role : UserRole.values()) {
            if (role.getValue() == userRole) {
                return true;
            }
        }
        return false;
    }
}
