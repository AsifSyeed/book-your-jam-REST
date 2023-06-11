package com.example.BookYourJam.studio.service.impl;

import com.example.BookYourJam.common.exception.BaseException;
import com.example.BookYourJam.studio.model.entity.Studio;
import com.example.BookYourJam.studio.model.request.AddStudioRequest;
import com.example.BookYourJam.studio.model.response.AddStudioResponse;
import com.example.BookYourJam.studio.repository.StudioRepository;
import com.example.BookYourJam.studio.service.IStudioService;
import com.example.BookYourJam.user.enums.UserRole;
import com.example.BookYourJam.user.model.entity.UserAccount;
import com.example.BookYourJam.user.service.IUserService;
import org.springframework.security.access.AccessDeniedException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.Objects;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class StudioService implements IStudioService {

    private final StudioRepository studioRepository;
    private final IUserService userService;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public AddStudioResponse addStudio(AddStudioRequest addStudioRequest) {

        try {
            validateRequest(addStudioRequest);

            Studio studio = prepareStudioResponse(addStudioRequest);

            studioRepository.save(studio);

            return AddStudioResponse.builder()
                    .studioName(studio.getStudioName())
                    .location(studio.getLocation())
                    .studioCode(studio.getStudioCode())
                    .build();

        } catch (AccessDeniedException e) {
            throw new BaseException(HttpStatus.UNAUTHORIZED.value(), "Unauthorized Access");
        }
    }

    private Studio prepareStudioResponse(AddStudioRequest addStudioRequest) {
        Studio studio = new Studio();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String createdByUserEmail = authentication.getName();

        UserAccount createdBy = userService.findByEmail(createdByUserEmail)
                .orElseThrow(() -> new BaseException(HttpStatus.NOT_FOUND.value(), "User not found"));

        studio.setStudioName(addStudioRequest.getStudioName());
        studio.setStudioCode(generateRandomString(addStudioRequest.getStudioName()));
        studio.setLocation(addStudioRequest.getLocation());
        studio.setCreatedBy(createdBy.getEmail());

        return studio;
    }

    private void validateRequest(AddStudioRequest addStudioRequest) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userRole = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse(null);

        if (userRole == null || Integer.parseInt(userRole) != UserRole.OWNER.getValue()) {
            throw new BaseException(HttpStatus.BAD_REQUEST.value(), "You are not authorized to add an studio");
        }

        if (Objects.isNull(addStudioRequest) ||
                StringUtils.isEmpty(addStudioRequest.getStudioName()) ||
                StringUtils.isEmpty(addStudioRequest.getLocation()) ||
                Objects.isNull(addStudioRequest.getDailySlot())) {

            throw new BaseException(HttpStatus.BAD_REQUEST.value(), "Request body is not valid");
        }

        if (isStudioNameExists(addStudioRequest.getStudioName())) {
            throw new BaseException(HttpStatus.BAD_REQUEST.value(), "Studio name already exists");
        }
    }

    private boolean isStudioNameExists(String studioName) {
        return studioRepository.existsByStudioName(studioName);
    }

    private String generateRandomString(String str) {
        StringBuilder randomString = new StringBuilder();

        // Split the event name by whitespace to get individual words
        String[] words = str.split("\\s+");

        // Iterate over each word and append its capitalized first letter to the random string
        for (String word : words) {
            if (!word.isEmpty()) {
                randomString.append(Character.toUpperCase(word.charAt(0)));
            }
        }

        // Calculate the number of remaining characters needed
        int remainingCharacters = 10 - randomString.length();

        // Generate random numbers to fill up the remaining characters
        Random random = new Random();
        for (int i = 0; i < remainingCharacters; i++) {
            randomString.append(random.nextInt(10));
        }

        return randomString.toString();
    }
}
