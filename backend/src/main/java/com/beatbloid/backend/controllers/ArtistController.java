@RestController
@RequestMapping("/api/artists")
public class ArtistController {

    private final ArtistRepository artistRepository;
    private final ArtistCreationService artistCreationService;

    public ArtistController(ArtistRepository artistRepository, ArtistCreationService artistCreationService) {
        this.artistRepository = artistRepository;
        this.artistCreationService = artistCreationService;
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseWrapper<List<ArtistModel>>> getAllArtists() {
        List<ArtistModel> artists = artistRepository.findAll();
        return ResponseEntity.ok(new ResponseWrapper<>(200, "All artists fetched successfully", artists));
    }

}