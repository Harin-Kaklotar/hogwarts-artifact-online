package self.harin.hogwartsartifactonline.wizard;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import self.harin.hogwartsartifactonline.artifact.Artifact;
import self.harin.hogwartsartifactonline.artifact.ArtifactRepository;
import self.harin.hogwartsartifactonline.system.exception.ObjectNotFoundException;

import java.util.List;

@Service
@Transactional
public class WizardService {

    private final WizardRepository wizardRepository;
    private final ArtifactRepository artifactRepository;

    public WizardService(WizardRepository wizardRepository, ArtifactRepository artifactRepository) {
        this.wizardRepository = wizardRepository;
        this.artifactRepository = artifactRepository;
    }

    public Wizard findById(int wizardId) {
        return this.wizardRepository.findById(wizardId).orElseThrow(() ->
                new ObjectNotFoundException("wizard", wizardId)
        );
    }

    public List<Wizard> findAll() {
        return this.wizardRepository.findAll();
    }

    public Wizard save(Wizard newWizard) {
        return this.wizardRepository.save(newWizard);
    }

    public Wizard update(Integer wizardId, Wizard wizard) {
        return this.wizardRepository.findById(wizardId)
                .map(oldWizard -> {
                            oldWizard.setName(wizard.getName());
                            return this.wizardRepository.save(oldWizard);
                        }
                ).orElseThrow(() -> new ObjectNotFoundException("wizard", wizardId));
    }

    public void delete(Integer wizardId) {
        Wizard wizardToBeDeleted = this.wizardRepository.findById(wizardId)
                .orElseThrow(() -> new ObjectNotFoundException("wizard", wizardId));
        wizardToBeDeleted.removeAllArtifacts();
        this.wizardRepository.deleteById(wizardId);
    }

    public void assignArtifact(Integer wizardId, String artifactId) {
        // Find this artifact by ID from DB
        Artifact artifactToBeAssigned = this.artifactRepository.findById(artifactId)
                .orElseThrow(() -> new ObjectNotFoundException("artifact", artifactId));
        // Find this wizard by ID from DB
        Wizard wizard = this.wizardRepository.findById(wizardId)
                .orElseThrow(() -> new ObjectNotFoundException("wizard", wizardId));

        // Artifact assignment
        // We need to see if the artifact is already owned by some wizard.
        if (artifactToBeAssigned.getOwner() != null){
            artifactToBeAssigned.getOwner().removeArtifact(artifactToBeAssigned);
        }
        wizard.addArtifact(artifactToBeAssigned);
    }
}
