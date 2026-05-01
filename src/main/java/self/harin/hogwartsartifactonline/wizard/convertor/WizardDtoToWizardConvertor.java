package self.harin.hogwartsartifactonline.wizard.convertor;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import self.harin.hogwartsartifactonline.wizard.Wizard;
import self.harin.hogwartsartifactonline.wizard.dto.WizardDto;

@Component
public class WizardDtoToWizardConvertor implements Converter<WizardDto, Wizard> {
    @Override
    public Wizard convert(WizardDto source) {
        Wizard wizard = new Wizard();
        wizard.setId(source.id());
        wizard.setName(source.name());
        return wizard;
    }
}
