package com.example.BookYourJam.studio.service;

import com.example.BookYourJam.studio.model.request.AddStudioRequest;
import com.example.BookYourJam.studio.model.response.AddStudioResponse;

public interface IStudioService {
    AddStudioResponse addStudio(AddStudioRequest addStudioRequest);
}
