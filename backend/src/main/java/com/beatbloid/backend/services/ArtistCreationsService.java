package com.beatbloid.backend.services;

import com.beatbloid.backend.models.ArtistCreationModel;
import com.beatbloid.backend.models.ArtistModel;
import com.beatbloid.backend.repositories.ArtistCreationRepository;
import com.beatbloid.backend.repositories.ArtistRepository;
import com.beatbloid.backend.utils.GoogleDriveUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ArtistCreationService {

    private final ArtistCreationRepository artistCreationRepository;
    private final ArtistRepository artistRepository;
    private final GoogleDriveUtil googleDriveUtil;

    public ArtistCreationService(ArtistCreationRepository artistCreationRepository, ArtistRepository artistRepository, GoogleDriveUtil googleDriveUtil) {
        this.artistCreationRepository = artistCreationRepository;
        this.artistRepository = artistRepository;
        this.googleDriveUtil = googleDriveUtil;
    }

    public ArtistCreationModel createArtistCreation(Long artistId, MultipartFile mediaFile, String type) {
        ArtistModel artist = artistRepository.findById(artistId)
                .orElseThrow(() -> new RuntimeException("Artist not found"));

        String fileUrl = googleDriveUtil.uploadFile(mediaFile);

        ArtistCreationModel creation = new ArtistCreationModel();
        creation.setArtist(artist);
        creation.setUrl(fileUrl);
        creation.setType(CreationType.valueOf(type.toUpperCase()));

        return artistCreationRepository.save(creation);
    }

    public Map<String, Object> getArtistProfileAndCreations(Long artistId) {
        List<ArtistCreationModel> creations = artistCreationRepository.findByArtistIdWithProfile(artistId);
        
        if (creations.isEmpty()) {
            throw new NotFoundException("Artist not found or has no creations.");
        }
        ProfileModel profile = creations.get(0).getArtist().getProfile();

        Map<String, Object> response = new HashMap<>();
        response.put("profile", profile);
        response.put("creations", creations);

        return response;
    }
}
