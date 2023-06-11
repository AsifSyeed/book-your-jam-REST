package com.example.BookYourJam.studio.controller;

import com.example.BookYourJam.common.model.response.ApiResponse;
import com.example.BookYourJam.studio.model.request.AddStudioRequest;
import com.example.BookYourJam.studio.model.response.AddStudioResponse;
import com.example.BookYourJam.studio.service.IStudioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/studio")
public class StudioController {

    private final IStudioService studioService;

    public ResponseEntity<ApiResponse<AddStudioResponse>> addStudio(@RequestBody @Valid AddStudioRequest addStudioRequest) {
        AddStudioResponse addStudioResponse = studioService.addStudio(addStudioRequest);

        ApiResponse<AddStudioResponse> response = new ApiResponse<>(HttpStatus.OK.value(), "Studio added successfully", addStudioResponse);

        return ResponseEntity.ok(response);
    }
}
