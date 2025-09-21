import com.beatbloid.backend.models.ArtistTeamModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ArtistTeamRepository extends JpaRepository<ArtistTeamModel, Long> {
    List<ArtistTeamModel> findByTeam_TeamId(Long teamId);
}
