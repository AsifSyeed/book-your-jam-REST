package com.example.BookYourJam.studio.repository;

import com.example.BookYourJam.studio.model.entity.Studio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudioRepository extends JpaRepository<Studio, Long> {
    boolean existsByStudioName(String studioName);
}
