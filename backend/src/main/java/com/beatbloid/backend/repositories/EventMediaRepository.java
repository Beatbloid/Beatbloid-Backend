package com.beatbloid.backend.repositories;

import com.beatbloid.backend.models.EventMediaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventMediaRepository extends JpaRepository<EventMediaModel, Long> {
}
