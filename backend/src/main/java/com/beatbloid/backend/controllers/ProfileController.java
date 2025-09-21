package com.beatbloid.backend.controllers;

import com.beatbloid.backend.models.ProfileModel;
import com.beatbloid.backend.services.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profiles")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping("/{userId}")
    public ResponseEntity<ResponseWrapper<ProfileModel>> createProfile(
            @PathVariable Long userId, @RequestBody ProfileModel profile) {
        
        ProfileModel createdProfile = profileService.createProfile(userId, profile);
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Profile created successfully", createdProfile));
    }
}
