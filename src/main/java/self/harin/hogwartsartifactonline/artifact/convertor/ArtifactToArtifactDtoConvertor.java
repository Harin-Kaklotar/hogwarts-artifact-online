package self.harin.hogwartsartifactonline.artifact.convertor;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import self.harin.hogwartsartifactonline.artifact.Artifact;
import self.harin.hogwartsartifactonline.artifact.dto.ArtifactDto;
import self.harin.hogwartsartifactonline.wizard.convertor.WizardToWizardDtoConvertor;

@Component
public class ArtifactToArtifactDtoConvertor implements Converter<Artifact, ArtifactDto> {

    private final WizardToWizardDtoConvertor wizardToWizardDtoConvertor;

    public ArtifactToArtifactDtoConvertor(WizardToWizardDtoConvertor wizardToWizardDtoConvertor) {
        this.wizardToWizardDtoConvertor = wizardToWizardDtoConvertor;
    }

    @Override
    public ArtifactDto convert(Artifact source) {
        ArtifactDto artifactDto = new ArtifactDto(
                source.getId(),
                source.getName(),
                source.getDescription(),
                source.getImageUrl(),
                source.getOwner() != null ? this.wizardToWizardDtoConvertor.convert(source.getOwner()) : null
        );
        return artifactDto;
    }
}
