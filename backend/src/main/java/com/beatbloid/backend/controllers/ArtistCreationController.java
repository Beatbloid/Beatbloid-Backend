package com.beatbloid.backend.controllers;

import com.beatbloid.backend.models.ArtistCreationModel;
import com.beatbloid.backend.services.ArtistCreationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/artists/creations")
public class ArtistCreationController {

    private final ArtistCreationService artistCreationService;

    public ArtistCreationController(ArtistCreationService artistCreationService) {
        this.artistCreationService = artistCreationService;
    }

    @PostMapping("/{artistId}/upload")
    public ResponseEntity<ResponseWrapper<ArtistCreationModel>> uploadCreation(
            @PathVariable Long artistId,
            @RequestParam("file") MultipartFile file,
            @RequestParam("type") String type) {
        return ResponseEntity.ok(new ResponseWrapper(200, "Artist creation added to portfolio", artistCreationService.createArtistCreation(artistId, file, type)));
    }

    @GetMapping("/{artistId}/profile-creations")
    public ResponseEntity<ResponseWrapperc<Map<String, Object>>> getArtistProfileAndCreations(@PathVariable Long artistId) {
        return ResponseEntity.ok(new ResponseWrapper(200, "Artist details fetched successfully", artistService.getArtistProfileAndCreations(artistId)));
    }
}
