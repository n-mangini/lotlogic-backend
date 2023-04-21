package app.service;

import app.model.User;
import app.model.dto.UserEditForm;
import app.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

public class UserServiceTest {
    @Mock
    private UserRepository testRepository;
    @InjectMocks
    private UserService testService;

    private final static User OWNER1 = new User("43400400", "Martin", "Garabal", "austral");
    private final static User OWNER2 = new User("33200200", "Marcelo", "Gallardo", "river912");
    private final static User EMPLOYEE1 = new User("23100100", "Javier", "Milei", "1234");
    private final static User EMPLOYEE2 = new User("13000000", "Miguel", "Granados", "5678");

    @Test
    void saveOwner() {
        //given
        final User owner = new User("43400400", "Martin", "Garabal", "austral");

        //when
        this.testService.saveOwner(owner);

        //then
        final ArgumentCaptor<User> ownerArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(this.testRepository).save(ownerArgumentCaptor.capture());
        final User capturedOwner = ownerArgumentCaptor.getValue();
        Assertions.assertThat(capturedOwner).isEqualTo(owner);
        assertThat(capturedOwner).isEqualTo(owner);
    }

    @Test
    void saveOwnerWithIllegalPassword() {
        //given
        final User owner = new User("43400400", "Martin", "Garabal", "1234");

        //then
        assertThatThrownBy(() -> this.testService.saveOwner(owner))
                .isInstanceOf(ResponseStatusException.class).hasMessageContaining("password is too short");
        verify(this.testRepository, never()).save(any());
    }

    //TODO fix
/*    @Test
    void saveOwnerAlreadyExistsWithDni() {
        //given
        final User owner1 = new User("43400400", "Martin", "Garabal", "austral");
        final User owner2 = new User("43400400", "Marcelo", "Gallardo", "river912");

        //when
        this.testService.saveOwner(owner1);

        //then
        final ArgumentCaptor<User> owner1ArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(this.testRepository).save(owner1ArgumentCaptor.capture());
        when(this.testRepository.findByDni(owner2.getDni())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> this.testService.saveOwner(owner2))
                .isInstanceOf(ResponseStatusException.class).hasMessageContaining("owner with DNI " + owner1.getDni() + " already exists");
    }*/

    @Test
    void saveEmployee() {
        //given
        final User employee = new User("23100100", "Javier", "Milei", "1234");;

        //when
        this.testService.saveEmployee(employee);

        //then
        final ArgumentCaptor<User> employeeArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(this.testRepository).save(employeeArgumentCaptor.capture());
        final User capturedEmployee = employeeArgumentCaptor.getValue();
        Assertions.assertThat(capturedEmployee).isEqualTo(employee);
        assertThat(capturedEmployee).isEqualTo(employee);
    }

    @Test
    void saveEmployeeWithIllegalPassword() {
        //given
        final User employee = new User("23100100", "Javier", "Milei", "austral");

        //then
        assertThatThrownBy(() -> this.testService.saveEmployee(employee))
                .isInstanceOf(ResponseStatusException.class).hasMessageContaining("PIN must be 4 digit");
        verify(this.testRepository, never()).save(any());
    }

    //TODO fix
/*    @Test
    void saveEmployeeAlreadyExistsWithDni() {
        //given
        final User employee1 = new User("23100100", "Javier", "Milei", "1234");
        final User employee2 = new User("13000000", "Miguel", "Granados", "5678");

        //when
        this.testService.saveEmployee(employee1);

        //then
        assertThatThrownBy(() -> this.testService.saveEmployee(employee2))
                .isInstanceOf(ResponseStatusException.class).hasMessageContaining("employee with DNI " + employee1.getDni() + " already exists");
        verify(this.testRepository, never()).save(any());
    }*/

    @Test
    void deleteOwnerTest() {

    }

    @Test
    void deleteOwnerNotFound() {
        final Long userId = 1L;
        assertThatThrownBy(() -> this.testService.deleteOwner(userId))
                .isInstanceOf(ResponseStatusException.class).hasMessageContaining("user " + userId + " not found");
        verify(this.testRepository, never()).deleteById(userId);
    }

    //TODO fix
/*    @Test
    void deleteOwnerWhenUserIsEmployee() {
        //given
        final User employee = new User("23100100", "Javier", "Milei", "1234");;
        given(this.testRepository.existsById(employee.getUserId())).willReturn(true);
        this.testService.saveEmployee(employee);

        //then
        assertThatThrownBy(() -> this.testService.deleteOwner(employee.getUserId()))
                .isInstanceOf(ResponseStatusException.class).hasMessageContaining("user " + employee.getUserId() + " is not owner");
        verify(this.testRepository, never()).deleteById(employee.getUserId());
    }*/

    @Test
    void deleteEmployee() {

    }

    @Test
    void deleteEmployeeNotFound() {
        final Long userId = 1L;
        assertThatThrownBy(() -> this.testService.deleteEmployee(userId))
                .isInstanceOf(ResponseStatusException.class).hasMessageContaining("user " + userId + " not found");
        verify(this.testRepository, never()).deleteById(userId);
    }

    //TODO fix
/*    @Test
    void deleteEmployeeWhenUserIsOwner() {
        //given
        final User owner = new User("43400400", "Martin", "Garabal", "austral");
        this.testService.saveOwner(owner);

        //then
        assertThatThrownBy(() -> this.testService.deleteEmployee(owner.getUserId()))
                .isInstanceOf(ResponseStatusException.class).hasMessageContaining("user " + owner.getUserId() + " is not owner");
        verify(this.testRepository, never()).deleteById(owner.getUserId());
    }*/

    //TODO fix NOT FOUND
/*    @Test
    void updateOwner() {
        //given
        User owner = new User("43400400", "Martin", "Garabal", "austral");
        UserEditForm ownerEditForm = new UserEditForm(OWNER2.getDni(), OWNER2.getFirstName(), OWNER2.getLastName(), OWNER2.getPassword());
        this.testService.saveOwner(owner);

        //when
        this.testService.updateOwner(owner.getUserId(), ownerEditForm);
        ArgumentCaptor<User> ownerArgumentCaptor = ArgumentCaptor.forClass(User.class);
        User capturedUser = ownerArgumentCaptor.getValue();

        //then
        assertThat(capturedUser).isEqualTo(owner);
    }*/

    //TODO fix NOT FOUND
/*    @Test
    void updateOwnerWhenUserIsEmployee() {
        //given
        User owner = new User("43400400", "Martin", "Garabal", "austral");
        UserEditForm ownerEditForm = new UserEditForm(OWNER2.getDni(), OWNER2.getFirstName(), OWNER2.getLastName(), OWNER2.getPassword());
        this.testService.saveOwner(owner);

        //then
        assertThatThrownBy(() -> this.testService.updateOwner(owner.getUserId(), ownerEditForm))
                .isInstanceOf(ResponseStatusException.class).hasMessageContaining("user " + owner.getUserId() + " is not owner");
    }*/

    @Test
    void updateOwnerNotFound() {
        //given
        User owner = new User("43400400", "Martin", "Garabal", "austral");
        UserEditForm ownerEditForm = new UserEditForm(OWNER2.getDni(), OWNER2.getFirstName(), OWNER2.getLastName(), OWNER2.getPassword());
        this.testService.saveOwner(owner);

        //then
        assertThatThrownBy(() -> this.testService.updateOwner(owner.getId(), ownerEditForm))
                .isInstanceOf(ResponseStatusException.class).hasMessageContaining("user " + owner.getId() + " not found");
    }

    @Test
    void updateEmployee() {
    }

    //TODO fix NOT FOUND
/*    @Test
    void updateEmployeeWhenUserIsOwner() {
        //given
        User employee = new User("23100100", "Javier", "Milei", "1234");;
        UserEditForm employeeEditForm = new UserEditForm(EMPLOYEE2.getDni(), EMPLOYEE2.getFirstName(), EMPLOYEE2.getLastName(), EMPLOYEE2.getPassword());
        this.testService.saveEmployee(employee);

        //then
        assertThatThrownBy(() -> this.testService.updateEmployee(employee.getUserId(), employeeEditForm))
                .isInstanceOf(ResponseStatusException.class).hasMessageContaining("user " + employee.getUserId() + " is not employee");
    }*/

    @Test
    void updateEmployeeNotFound() {
        //given
        User employee = new User("23100100", "Javier", "Milei", "1234");
        UserEditForm employeeEditForm = new UserEditForm(EMPLOYEE2.getDni(), EMPLOYEE2.getFirstName(), EMPLOYEE2.getLastName(), EMPLOYEE2.getPassword());
        this.testService.saveEmployee(employee);

        //then
        assertThatThrownBy(() -> this.testService.updateEmployee(employee.getId(), employeeEditForm))
                .isInstanceOf(ResponseStatusException.class).hasMessageContaining("user " + employee.getId() + " not found");
    }

    @Test
    void findAllEmployees() {
    }

    @Test
    void findAllOwners() {
    }
}
