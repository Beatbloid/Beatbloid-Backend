import com.beatbloid.backend.models.ArtistCreationModel;
import com.beatbloid.backend.models.EventModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PortfolioRepository extends JpaRepository<EventModel, Long> {
    List<EventModel> findByFeaturedTrue();
}

@Repository
public interface ArtistCreationRepository extends JpaRepository<ArtistCreationModel, Long> {
    List<ArtistCreationModel> findByFeaturedTrue();
}

