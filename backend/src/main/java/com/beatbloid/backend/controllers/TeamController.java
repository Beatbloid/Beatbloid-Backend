import com.beatbloid.backend.models.TeamModel;
import com.beatbloid.backend.models.ArtistTeamModel;
import com.beatbloid.backend.services.TeamService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teams")
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseWrapper<TeamModel>> createTeam(@RequestBody TeamModel team) {
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Team created successfully", teamService.createTeam(team)));
    }

    @PostMapping("/{teamId}/add-artist/{artistId}")
    public ResponseEntity<ResponseWrapper<ArtistTeamModel>> addArtistToTeam(@PathVariable Long teamId, @PathVariable Long artistId) {
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Artist added to team", teamService.addArtistToTeam(artistId, teamId)));
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseWrapper<List<TeamModel>>> getAllTeamsWithArtists() {
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Teams fetched successfully", teamService.getAllTeamsWithArtists()));
    }

    @GetMapping("/{teamId}")
    public ResponseEntity<ResponseWrapper<TeamModel>> getTeamById(@PathVariable Long teamId) {
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Team fetched successfully", teamService.getTeamById(teamId)));
    }
}
