package com.example.BookYourJam.auth.service;

import com.example.BookYourJam.auth.model.request.AuthRequest;
import com.example.BookYourJam.auth.model.response.AuthResponse;

public interface IAuthService {
    AuthResponse login(AuthRequest authRequest);
}
