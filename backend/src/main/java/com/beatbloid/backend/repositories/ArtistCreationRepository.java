package com.beatbloid.backend.repositories;

import com.beatbloid.backend.models.ArtistCreationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

@Repository
public interface ArtistCreationRepository extends JpaRepository<ArtistCreationModel, Long> {
    
    @Query("SELECT ac FROM ArtistCreationModel ac JOIN FETCH ac.artist a JOIN FETCH a.profile WHERE a.artistId = :artistId")
    List<ArtistCreationModel> findByArtistIdWithProfile(@Param("artistId") Long artistId);
}
