package com.example.BookYourJam.auth.service.impl;

import com.example.BookYourJam.auth.jwt.utlis.JwtUtil;
import com.example.BookYourJam.auth.model.request.AuthRequest;
import com.example.BookYourJam.auth.model.response.AuthResponse;
import com.example.BookYourJam.auth.service.IAuthService;
import com.example.BookYourJam.common.exception.BaseException;
import com.example.BookYourJam.user.model.entity.UserAccount;
import com.example.BookYourJam.user.repository.UserRepository;
import com.example.BookYourJam.user.service.imp.UserDetailsServiceImplementation;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {

    private final AuthenticationManager authenticationManager;

    private final UserDetailsServiceImplementation userDetailsService;

    private final UserRepository userRepository;

    private final JwtUtil jwtUtil;

    @Override
    public AuthResponse login(AuthRequest authRequest) {

        try {
            validateRequest(authRequest);

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
            );

            UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getEmail());
            String token = jwtUtil.generateToken(userDetails, authRequest.getUserRole());

            return AuthResponse.builder()
                    .token(token)
                    .build();

        } catch (AccessDeniedException e) {
            throw new IllegalArgumentException("Unauthorized", e);
        }
    }

    private void validateRequest(AuthRequest authRequest) {
        if (Objects.isNull(authRequest) ||
                StringUtils.isEmpty(authRequest.getEmail()) ||
                StringUtils.isEmpty(authRequest.getPassword())) {

            throw new BaseException(HttpStatus.BAD_REQUEST.value(), "Request body is not valid");
        }

        Optional<UserAccount> userAccountOptional = userRepository.findByEmail(authRequest.getEmail());

        if (userAccountOptional.isEmpty()) {
            throw new BaseException(HttpStatus.NOT_FOUND.value(), "User not found");
        }

        if (authRequest.getUserRole() != userAccountOptional.get().getRole()) {
            throw new BaseException(HttpStatus.BAD_REQUEST.value(), "User role is not valid");
        }
    }
}
