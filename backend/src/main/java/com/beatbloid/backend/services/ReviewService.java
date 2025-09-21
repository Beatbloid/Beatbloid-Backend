import com.beatbloid.backend.models.ReviewModel;
import com.beatbloid.backend.models.OrderModel;
import com.beatbloid.backend.exceptions.AlreadyExistsException;
import com.beatbloid.backend.models.ArtistModel;
import com.beatbloid.backend.repositories.ReviewRepository;
import com.beatbloid.backend.repositories.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.OptionalDouble;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final OrderRepository orderRepository;

    public ReviewService(ReviewRepository reviewRepository, OrderRepository orderRepository) {
        this.reviewRepository = reviewRepository;
        this.orderRepository = orderRepository;
    }

    @Transactional
    public ReviewModel addReview(Long orderId, ReviewModel review) {
        OrderModel order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order not found"));
        
        if (reviewRepository.findById(orderId).isPresent()) {
            throw new AlreadyExistsException("Review already exists for this order");
        }

        review.setOrder(order);
        review.setClient(order.getClientOrder()); 
        return reviewRepository.save(review);
    }

    public List<ReviewModel> getReviewsByArtist(Long artistId) {
        ArtistModel artist = new ArtistModel();
        artist.setArtistId(artistId);
        return reviewRepository.findByOrder_Artist(artist);
    }

    public List<ArtistModel> getTopArtistsByRating() {
        List<Object[]> results = reviewRepository.findArtistsByAverageRating();

        return results.stream()
                .map(obj -> (ArtistModel) obj[0]) 
                .collect(Collectors.toList());
    }

    public List<ArtistModel> getManuallyFeaturedArtists() {
        return artistRepository.findByIsTopArtistTrue();
    }

    public List<ArtistModel> getAllTopArtists() {
        List<ArtistModel> reviewBased = getTopArtistsByRating();
        List<ArtistModel> manualTop = getManuallyFeaturedArtists();

        return reviewBased.stream()
                .distinct()
                .collect(Collectors.toList());
    }

    public void setArtistAsTop(Long artistId, boolean isTopArtist) {
        ArtistModel artist = artistRepository.findById(artistId)
                .orElseThrow(() -> new NotFoundException("Artist not found"));

        artist.setTopArtist(isTopArtist);
        artistRepository.save(artist);
    }
}
