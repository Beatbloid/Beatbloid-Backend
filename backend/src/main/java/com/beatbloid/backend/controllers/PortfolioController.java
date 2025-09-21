import com.beatbloid.backend.models.ArtistCreationModel;
import com.beatbloid.backend.models.EventModel;
import com.beatbloid.backend.services.PortfolioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/portfolio")
public class PortfolioController {

    private final PortfolioService portfolioService;

    public PortfolioController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    @PostMapping("/add/event/{eventId}")
    public ResponseEntity<ResponseWrapper<EventModel>> addEventToPortfolio(@PathVariable Long eventId) {
        EventModel updatedEvent = portfolioService.addEventToPortfolio(eventId);
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Event added to portfolio", updatedEvent));
    }

    @PostMapping("/add/creation/{creationId}")
    public ResponseEntity<ResponseWrapper<ArtistCreationModel>> addCreationToPortfolio(@PathVariable Long creationId) {
        ArtistCreationModel updatedCreation = portfolioService.addArtistCreationToPortfolio(creationId);
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Artist creation added to portfolio", updatedCreation));
    }

    @GetMapping("/featured")
    public ResponseEntity<ResponseWrapper<PortfolioResponse>> getFeaturedPortfolio() {
        List<EventModel> events = portfolioService.getFeaturedEvents();
        List<ArtistCreationModel> creations = portfolioService.getFeaturedArtistCreations();
        
        PortfolioResponse response = new PortfolioResponse(events, creations);
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Portfolio fetched successfully", response));
    }
}
