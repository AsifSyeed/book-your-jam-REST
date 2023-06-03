package com.example.BookYourJam.user.repository;

import com.example.BookYourJam.user.model.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserAccount, Long> {
    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByUserName(String userName);

    Optional<UserAccount> findByEmail(String email);
    Optional<UserAccount> findByUserName(String username);
}
