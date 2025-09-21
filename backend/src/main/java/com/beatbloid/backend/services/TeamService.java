import com.beatbloid.backend.models.TeamModel;
import com.beatbloid.backend.models.ArtistModel;
import com.beatbloid.backend.models.ArtistTeamModel;
import com.beatbloid.backend.repositories.TeamRepository;
import com.beatbloid.backend.repositories.ArtistRepository;
import com.beatbloid.backend.repositories.ArtistTeamRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private final ArtistRepository artistRepository;
    private final ArtistTeamRepository artistTeamRepository;

    public TeamService(TeamRepository teamRepository, ArtistRepository artistRepository, ArtistTeamRepository artistTeamRepository) {
        this.teamRepository = teamRepository;
        this.artistRepository = artistRepository;
        this.artistTeamRepository = artistTeamRepository;
    }

    public TeamModel createTeam(TeamModel team) {
        return teamRepository.save(team);
    }

    public ArtistTeamModel addArtistToTeam(Long artistId, Long teamId) {
        ArtistModel artist = artistRepository.findById(artistId)
                .orElseThrow(() -> new NotFoundException("Artist not found"));

        TeamModel team = teamRepository.findById(teamId)
                .orElseThrow(() -> new NotFoundException("Team not found"));

        ArtistTeamModel artistTeam = new ArtistTeamModel();
        artistTeam.setArtist(artist);
        artistTeam.setTeam(team);

        return artistTeamRepository.save(artistTeam);
    }

    public List<TeamModel> getAllTeamsWithArtists() {
        List<TeamModel> teams = teamRepository.findAll();

        return teams.stream().map(team -> {
            List<ArtistTeamModel> artistTeams = artistTeamRepository.findByTeam_TeamId(team.getTeamId());
            team.setArtistTeams(artistTeams);
            return team;
        }).collect(Collectors.toList());
    }

    public TeamModel getTeamById(Long teamId) {
        TeamModel team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Team not found"));

        List<ArtistTeamModel> artistTeams = artistTeamRepository.findByTeam_TeamId(teamId);
        team.setArtistTeams(artistTeams);
        return team;
    }
}
