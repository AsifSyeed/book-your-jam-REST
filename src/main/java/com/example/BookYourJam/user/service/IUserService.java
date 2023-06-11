package com.example.BookYourJam.user.service;


import com.example.BookYourJam.user.model.entity.UserAccount;
import com.example.BookYourJam.user.model.request.SignUpRequest;

import java.util.Optional;

public interface IUserService {
    void signUpUser(SignUpRequest signUpRequest);

    Optional<UserAccount> findByEmail(String email);
}
