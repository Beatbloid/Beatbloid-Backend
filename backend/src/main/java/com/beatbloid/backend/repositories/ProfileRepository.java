package com.beatbloid.backend.repositories;

import com.beatbloid.backend.models.ProfileModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfileRepository extends JpaRepository<ProfileModel, Long> {
    List<ProfileModel> findByUserUserId(Long userId);
}
