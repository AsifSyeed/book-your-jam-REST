package com.example.BookYourJam.auth.controller;

import com.example.BookYourJam.auth.model.request.AuthRequest;
import com.example.BookYourJam.auth.model.response.AuthResponse;
import com.example.BookYourJam.auth.service.IAuthService;
import com.example.BookYourJam.common.model.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final IAuthService authService;
    @PostMapping("/token")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@RequestBody @Valid AuthRequest authRequest) {

        AuthResponse authResponse = authService.login(authRequest);

        ApiResponse<AuthResponse> response = new ApiResponse<>(HttpStatus.OK.value(), "User logged in successfully!", authResponse);

        return ResponseEntity.ok(response);
    }
}
