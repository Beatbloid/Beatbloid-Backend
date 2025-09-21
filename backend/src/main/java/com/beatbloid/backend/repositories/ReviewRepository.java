import com.beatbloid.backend.models.ReviewModel;
import com.beatbloid.backend.models.ArtistModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewModel, Long> {
    @Query("SELECT r.order.artist, AVG(r.rating) as avgRating " +
           "FROM ReviewModel r GROUP BY r.order.artist ORDER BY avgRating DESC")
    List<Object[]> findArtistsByAverageRating();

    List<ReviewModel> findByOrder_Artist(ArtistModel artist);
}
