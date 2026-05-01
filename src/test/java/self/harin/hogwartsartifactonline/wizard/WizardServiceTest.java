package self.harin.hogwartsartifactonline.wizard;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import self.harin.hogwartsartifactonline.system.exception.ObjectNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WizardServiceTest {

    @Mock
    WizardRepository wizardRepository;

    @InjectMocks
    WizardService wizardService;

    List<Wizard> wizards;

    @BeforeEach
    void setUp() {
        Wizard w1 = new Wizard();
        w1.setId(1);
        w1.setName("Albus Dumbledore");

        Wizard w2 = new Wizard();
        w2.setId(2);
        w2.setName("Harry Potter");

        Wizard w3 = new Wizard();
        w3.setId(3);
        w3.setName("Neville Longbottom");

        this.wizards = new ArrayList<>();
        this.wizards.add(w1);
        this.wizards.add(w2);
        this.wizards.add(w3);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void testFindByIdSuccess() {
        // Given
        Wizard wizard = new Wizard();
        wizard.setId(1);
        wizard.setName("");

        given(this.wizardRepository.findById(1)).willReturn(Optional.of(wizard));

        //When
        Wizard result = this.wizardService.findById(1);

        //Then
        assertThat(result.getId()).isEqualTo(wizard.getId());
        assertThat(result.getName()).isEqualTo(wizard.getName());
        assertThat(result.getNumberOfArtifacts()).isEqualTo(wizard.getNumberOfArtifacts());
        verify(wizardRepository, times(1)).findById(1);
    }

    @Test
    void testFindByIdNotFound() {
        // Given
        given(this.wizardRepository.findById(1)).willReturn(Optional.empty());

        // When
        Throwable thrown = catchThrowable(() -> {
            Wizard returnWizard = this.wizardService.findById(1);
        });

        // Then
        assertThat(thrown)
                .isInstanceOf(ObjectNotFoundException.class)
                .hasMessage("Could not find wizard with Id 1 :(");
        verify(this.wizardRepository, times(1)).findById(1);
    }

    @Test
    public void testFindAllSuccess() {
        // Given
        given(this.wizardRepository.findAll()).willReturn(this.wizards);

        // When
        List<Wizard> foundWizards = this.wizardService.findAll();

        // Then
        assertThat(foundWizards.size()).isEqualTo(this.wizards.size());
        verify(wizardRepository, times(1)).findAll();
    }

    @Test
    void tetSaveSuccess() {
        // Given
        Wizard newWizard = new Wizard();
        newWizard.setName("Ronn");

        given(this.wizardRepository.save(newWizard)).willReturn(newWizard);

        // When
        Wizard savedWizard = this.wizardService.save(newWizard);

        // Then
        assertThat(savedWizard.getName()).isEqualTo(newWizard.getName());
        verify(this.wizardRepository, times(1)).save(newWizard);
    }

    @Test
    void testUpdateSuccess() {
        // Given
        Wizard oldWizard = new Wizard();
        oldWizard.setId(1);
        oldWizard.setName("Albus Dumbledore");

        Wizard update = new Wizard();
        oldWizard.setName("Albus Dumbledore - Updated");

        given(this.wizardRepository.findById(1)).willReturn(Optional.of(oldWizard));
        given(this.wizardRepository.save(oldWizard)).willReturn(oldWizard);

        // When
        Wizard updatedWizard = this.wizardService.update(1, update);

        // Then
        assertThat(updatedWizard.getId()).isEqualTo(1);
        assertThat(updatedWizard.getName()).isEqualTo(update.getName());
        verify(this.wizardRepository, times(1)).findById(1);
        verify(this.wizardRepository, times(1)).save(oldWizard);
    }

    @Test
    void testUpdateNotFound() {
        Wizard wizard = new Wizard();
        wizard.setId(0);
        wizard.setName("Voldemort");
        // Given
        given(this.wizardRepository.findById(0)).willReturn(Optional.empty());

        // When
        Throwable thrown = catchThrowable(() -> {
            Wizard foundWizard = this.wizardService.update(0, wizard);
        });

        // Then
        assertThat(thrown).isInstanceOf(ObjectNotFoundException.class);
        verify(this.wizardRepository, times(1)).findById(0);
    }

    @Test
    void testDeleteSuccess() {
        // Given
        Wizard wizard = new Wizard();
        wizard.setId(1);
        wizard.setName("Albus Dumbledore");
        given(this.wizardRepository.findById(1)).willReturn(Optional.of(wizard));
        doNothing().when(this.wizardRepository).deleteById(1);

        // When
        this.wizardService.delete(1);

        // Then
        verify(this.wizardRepository, times(1)).deleteById(1);
    }

    @Test
    void testDeleteNotFound() {
        // Given
        given(this.wizardRepository.findById(10)).willReturn(Optional.empty());
        // When
        assertThrows(ObjectNotFoundException.class, () -> {
            this.wizardService.delete(10);
        });
        // Then
        verify(this.wizardRepository, times(1)).findById(10);
    }
}
