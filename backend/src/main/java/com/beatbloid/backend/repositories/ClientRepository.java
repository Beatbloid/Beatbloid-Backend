package com.beatbloid.backend.repositories;

import com.beatbloid.backend.models.ClientModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface clientRepository extends JpaRepository<ClientModel, Long> {
}