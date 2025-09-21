package com.beatbloid.backend.services;

import com.beatbloid.backend.models.ProfileModel;
import com.beatbloid.backend.models.UserModel;
import com.beatbloid.backend.repositories.ProfileRepository;
import com.beatbloid.backend.repositories.UserRepository;
import com.beatbloid.backend.exceptions.UserNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;

    public ProfileService(ProfileRepository profileRepository, UserRepository userRepository) {
        this.profileRepository = profileRepository;
        this.userRepository = userRepository;
    }

    public ProfileModel createProfile(Long userId, ProfileModel profile) {
        UserModel user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

        profile.setUser(user);
        return profileRepository.save(profile);
    }
}
