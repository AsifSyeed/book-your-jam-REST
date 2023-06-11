package com.example.BookYourJam.slots.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity(name = "DAILY_SLOT")
public class DailySlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "STUDIO_CODE")
    private String studioCode;

    @Column(name = "SLOT_CODE")
    private String slotCode;

    @Column(name = "START_TIME")
    private Date startTime;

    @Column(name = "END_TIME")
    private Date endTime;
}
