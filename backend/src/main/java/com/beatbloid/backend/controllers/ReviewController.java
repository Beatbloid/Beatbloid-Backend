import com.beatbloid.backend.models.ReviewModel;
import com.beatbloid.backend.models.ArtistModel;
import com.beatbloid.backend.services.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/add/{orderId}")
    public ResponseEntity<ResponseWrapper<ReviewModel>> addReview(@PathVariable Long orderId, @RequestBody ReviewModel review) {
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Review added successfully", reviewService.addReview(orderId, review)));
    }

    @GetMapping("/artist/{artistId}")
    public ResponseEntity<ResponseWrapper<List<ReviewModel>>> getReviewsByArtist(@PathVariable Long artistId) {
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Reviews fetched successfully", reviewService.getReviewsByArtist(artistId)));
    }

    @GetMapping("/top-rated")
    public ResponseEntity<ResponseWrapper<List<ArtistModel>>> getTopArtistsByRating() {
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Top-rated artists fetched", reviewService.getTopArtistsByRating()));
    }

    @GetMapping("/featured")
    public ResponseEntity<ResponseWrapper<List<ArtistModel>>> getManuallyFeaturedArtists() {
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Featured artists fetched", reviewService.getManuallyFeaturedArtists()));
    }

    @GetMapping("/top-all")
    public ResponseEntity<ResponseWrapper<List<ArtistModel>>> getAllTopArtists() {
        return ResponseEntity.ok(new ResponseWrapper<>(200, "All top artists fetched", reviewService.getAllTopArtists()));
    }

    @PutMapping("/{artistId}/set-top")
    public ResponseEntity<ResponseWrapper<String>> setArtistAsTop(@PathVariable Long artistId, @RequestParam boolean isTop) {
        reviewService.setArtistAsTop(artistId, isTop);
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Artist updated successfully", "Artist " + artistId + " set as top: " + isTop));
    }
}
