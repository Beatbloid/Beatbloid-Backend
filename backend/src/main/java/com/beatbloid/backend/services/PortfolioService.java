import com.beatbloid.backend.models.ArtistCreationModel;
import com.beatbloid.backend.models.EventModel;
import com.beatbloid.backend.repositories.ArtistCreationRepository;
import com.beatbloid.backend.repositories.PortfolioRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final ArtistCreationRepository artistCreationRepository;

    public PortfolioService(PortfolioRepository portfolioRepository, ArtistCreationRepository artistCreationRepository) {
        this.portfolioRepository = portfolioRepository;
        this.artistCreationRepository = artistCreationRepository;
    }

    public EventModel addEventToPortfolio(Long eventId) {
        EventModel event = portfolioRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event not found"));
        event.setFeatured(true);
        return portfolioRepository.save(event);
    }

    public ArtistCreationModel addArtistCreationToPortfolio(Long creationId) {
        ArtistCreationModel creation = artistCreationRepository.findById(creationId)
                .orElseThrow(() -> new NotFoundException("Artist creation not found"));
        creation.setFeatured(true);
        return artistCreationRepository.save(creation);
    }

    public List<EventModel> getFeaturedEvents() {
        return portfolioRepository.findByFeaturedTrue();
    }

    public List<ArtistCreationModel> getFeaturedArtistCreations() {
        return artistCreationRepository.findByFeaturedTrue();
    }
}
