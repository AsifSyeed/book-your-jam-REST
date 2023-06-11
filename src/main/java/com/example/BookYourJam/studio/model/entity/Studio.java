package com.example.BookYourJam.studio.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "STUDIO")
public class Studio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "STUDIO_NAME")
    private String studioName;

    @Column(name = "STUDIO_CODE")
    private String studioCode;

    @Column(name = "LOCATION")
    private String location;

    @Column(name = "CREATED_BY")
    private String createdBy;
}
