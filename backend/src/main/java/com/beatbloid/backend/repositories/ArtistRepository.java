import com.beatbloid.backend.models.ArtistModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

@Repository
public interface ArtistRepository extends JpaRepository<ArtistModel, Long> {
    List<ArtistModel> findByIsTopArtistTrue();
}
