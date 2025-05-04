package self.harin.hogwartsartifactonline.wizard.convertor;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import self.harin.hogwartsartifactonline.wizard.Wizard;
import self.harin.hogwartsartifactonline.wizard.dto.WizardDto;

@Component
public class WizardToWizardDtoConvertor implements Converter<Wizard, WizardDto> {
    @Override
    public WizardDto convert(Wizard source) {
        WizardDto wizardDto = new WizardDto(source.getId(),
                source.getName(),
                source.getNumberOfArtifacts());
        return wizardDto;
    }
}
