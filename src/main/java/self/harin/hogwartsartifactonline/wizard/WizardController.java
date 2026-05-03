package self.harin.hogwartsartifactonline.wizard;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import self.harin.hogwartsartifactonline.system.Result;
import self.harin.hogwartsartifactonline.system.StatusCode;
import self.harin.hogwartsartifactonline.wizard.convertor.WizardDtoToWizardConvertor;
import self.harin.hogwartsartifactonline.wizard.convertor.WizardToWizardDtoConvertor;
import self.harin.hogwartsartifactonline.wizard.dto.WizardDto;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.endpoint.base-url}/wizards")
public class WizardController {

    private final WizardService wizardService;
    private final WizardToWizardDtoConvertor wizardToWizardDtoConvertor;
    private final WizardDtoToWizardConvertor wizardDtoToWizardConvertor;

    public WizardController(WizardService wizardService, WizardToWizardDtoConvertor wizardToWizardDtoConvertor, WizardDtoToWizardConvertor wizardDtoToWizardConvertor) {
        this.wizardService = wizardService;
        this.wizardToWizardDtoConvertor = wizardToWizardDtoConvertor;
        this.wizardDtoToWizardConvertor = wizardDtoToWizardConvertor;
    }

    @GetMapping("/{wizardId}")
    public Result findWizardById(@PathVariable int wizardId) {
        Wizard foundWizard = this.wizardService.findById(wizardId);
        WizardDto wizardDto = this.wizardToWizardDtoConvertor.convert(foundWizard);
        return new Result(true, StatusCode.SUCCESS, "Find One Success", wizardDto);
    }

    @GetMapping
    public Result findAllWizard() {
        List<Wizard> foundWizards = this.wizardService.findAll();

        List<WizardDto> wizardsDtos = foundWizards.stream()
                .map(this.wizardToWizardDtoConvertor::convert)
                .collect(Collectors.toList());

        return new Result(true, StatusCode.SUCCESS, "Found All Success", wizardsDtos);
    }

    @PostMapping
    public Result addWizard(@Valid @RequestBody WizardDto wizardDto) {
        Wizard wizard = this.wizardDtoToWizardConvertor.convert(wizardDto);
        Wizard savedWizard = this.wizardService.save(wizard);
        WizardDto savedWizardDto = this.wizardToWizardDtoConvertor.convert(savedWizard);
        return new Result(true, StatusCode.SUCCESS, "Add Success", savedWizardDto);
    }

    @PutMapping("/{wizardId}")
    public Result updateWizard(@PathVariable Integer wizardId, @Valid @RequestBody WizardDto wizardDto) {
        Wizard wizard = this.wizardDtoToWizardConvertor.convert(wizardDto);
        Wizard updatedWizard = this.wizardService.update(wizardId, wizard);
        WizardDto updatedWizardDto = this.wizardToWizardDtoConvertor.convert(updatedWizard);
        return new Result(true, StatusCode.SUCCESS, "Update Success", updatedWizardDto);
    }

    @DeleteMapping("/{wizardId}")
    public Result deleteWizard(@PathVariable Integer wizardId) {
        this.wizardService.delete(wizardId);
        return new Result(true, StatusCode.SUCCESS, "Delete Success");
    }

    @PutMapping("/{wizardId}/artifacts/{artifactId}")
    public Result assignArtifact(@PathVariable Integer wizardId, @PathVariable String artifactId) {
        this.wizardService.assignArtifact(wizardId, artifactId);
        return new Result(true, StatusCode.SUCCESS, "Artifact Assignment Success");
    }

}