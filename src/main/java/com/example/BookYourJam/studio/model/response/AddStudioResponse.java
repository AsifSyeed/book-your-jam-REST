package com.example.BookYourJam.studio.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class AddStudioResponse {
    private String studioName;
    private String location;
    private String studioCode;
}
