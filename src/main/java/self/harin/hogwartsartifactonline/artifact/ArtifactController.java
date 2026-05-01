package self.harin.hogwartsartifactonline.artifact;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import self.harin.hogwartsartifactonline.artifact.convertor.ArtifactDtoToArtifactConverter;
import self.harin.hogwartsartifactonline.artifact.convertor.ArtifactToArtifactDtoConvertor;
import self.harin.hogwartsartifactonline.artifact.dto.ArtifactDto;
import self.harin.hogwartsartifactonline.system.Result;
import self.harin.hogwartsartifactonline.system.StatusCode;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.endpoint.base-url}/artifacts")
public class ArtifactController {

    private final ArtifactService artifactService;

    private final ArtifactToArtifactDtoConvertor artifactToArtifactDtoConvertor;

    private final ArtifactDtoToArtifactConverter artifactDtoToArtifactConverter;

    public ArtifactController(ArtifactService artifactService, ArtifactToArtifactDtoConvertor artifactToArtifactDtoConvertor, ArtifactDtoToArtifactConverter artifactDtoToArtifactConverter) {
        this.artifactService = artifactService;
        this.artifactToArtifactDtoConvertor = artifactToArtifactDtoConvertor;
        this.artifactDtoToArtifactConverter = artifactDtoToArtifactConverter;
    }


    @GetMapping("/{artifactId}")
    public Result findArtifactById(@PathVariable String artifactId) {
        Artifact foundArtifact = this.artifactService.findById(artifactId);
        ArtifactDto artifactDto = this.artifactToArtifactDtoConvertor.convert(foundArtifact);
        return new Result(true, StatusCode.SUCCESS, "Find One Success", artifactDto);
    }

    @GetMapping
    public Result findAllArtifact() {
        List<Artifact> foundArtifacts = this.artifactService.findAll();

        // convert foundArtifacts to list of artifactDto
        List<ArtifactDto> artifactDtos = foundArtifacts.stream()
                .map(this.artifactToArtifactDtoConvertor::convert)
                .collect(Collectors.toList());

        return new Result(true, StatusCode.SUCCESS, "Found All Success", artifactDtos);
    }


    @PostMapping
    public Result addArtifact(@Valid @RequestBody ArtifactDto artifactDto) {
        Artifact artifact = this.artifactDtoToArtifactConverter.convert(artifactDto);
        Artifact savedArtifact = this.artifactService.save(artifact);
        ArtifactDto savedArtifactDto = this.artifactToArtifactDtoConvertor.convert(savedArtifact);
        return new Result(true, StatusCode.SUCCESS, "Add Success", savedArtifactDto);
    }


    @PutMapping("/{artifactId}")
    public Result updateArtifact(@PathVariable String artifactId, @Valid @RequestBody ArtifactDto artifactDto) {
        Artifact update = this.artifactDtoToArtifactConverter.convert(artifactDto);
        Artifact updatedArtifact = this.artifactService.update(artifactId, update);
        ArtifactDto updatedArtifactDto = this.artifactToArtifactDtoConvertor.convert(updatedArtifact);
        return new Result(true, StatusCode.SUCCESS, "Update Success", updatedArtifactDto);
    }

    @DeleteMapping("/{artifactId}")
    public Result deleteArtifact(@PathVariable String artifactId) {
        this.artifactService.delete(artifactId);
        return new Result(true, StatusCode.SUCCESS, "Delete Success");
    }


}
