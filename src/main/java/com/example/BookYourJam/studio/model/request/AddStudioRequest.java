package com.example.BookYourJam.studio.model.request;

import com.example.BookYourJam.slots.model.entity.DailySlot;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AddStudioRequest {
    private String studioName;
    private String location;
    private List<DailySlot> dailySlot;
}
