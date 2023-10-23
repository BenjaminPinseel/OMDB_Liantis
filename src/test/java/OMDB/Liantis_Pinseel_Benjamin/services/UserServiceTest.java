package OMDB.Liantis_Pinseel_Benjamin.services;

import OMDB.Liantis_Pinseel_Benjamin.dto.UserCreateDto;
import OMDB.Liantis_Pinseel_Benjamin.dto.UserUpdateRequestDto;
import OMDB.Liantis_Pinseel_Benjamin.entities.User;
import OMDB.Liantis_Pinseel_Benjamin.mappers.UserMapper;
import OMDB.Liantis_Pinseel_Benjamin.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    UserMapper userMapper;

    @InjectMocks
    UserService userService;

    // Test for finding all users
    @Test
    void findAllTest() {
        // Arrange
        Page<User> page = new PageImpl<>(List.of(new User(), new User()));
        when(userRepository.findAll(any(PageRequest.class))).thenReturn(page);

        // Act
        Page<User> result = userService.findAll(0, 10);

        // Assert
        verify(userRepository, times(1)).findAll(any(PageRequest.class));
        assertEquals(page.getTotalPages(), result.getTotalPages());
        assertEquals(page.getTotalElements(), result.getTotalElements());
        assertEquals(page.getNumber(), result.getNumber());
        assertEquals(page.getSize(), result.getSize());
        assertEquals(page.getNumberOfElements(), result.getNumberOfElements());
        assertEquals(page.getContent().size(), result.getContent().size());

        // Assert user details
        for (int i = 0; i < page.getContent().size(); i++) {
            User originalUser = page.getContent().get(i);
            User resultUser = result.getContent().get(i);
            assertEquals(originalUser.getId(), resultUser.getId());
            assertEquals(originalUser.getFirstName(), resultUser.getFirstName());
            assertEquals(originalUser.getLastName(), resultUser.getLastName());
            assertEquals(originalUser.getNickName(), resultUser.getNickName());
            assertEquals(originalUser.getAge(), resultUser.getAge());
            assertEquals(originalUser.getEmail(), resultUser.getEmail());
        }
    }

    // Test for finding a user by ID
    @Test
    void findByIdTest() {
        // Arrange
        User user = User.builder()
                .id("1")
                .build();
        when(userRepository.findById(eq("1"))).thenReturn(Optional.of(user));

        // Act
        Optional<User> result = userService.findById("1");

        // Assert
        verify(userRepository, times(1)).findById("1");
        assertEquals(Optional.of(user), result);

        if (result.isPresent()) {
            User foundUser = result.get();
            assertEquals(user.getId(), foundUser.getId());
            assertEquals(user.getFirstName(), foundUser.getFirstName());
            assertEquals(user.getLastName(), foundUser.getLastName());
            assertEquals(user.getNickName(), foundUser.getNickName());
            assertEquals(user.getAge(), foundUser.getAge());
            assertEquals(user.getEmail(), foundUser.getEmail());
        }
    }

    // Test for saving a user
    @Test
    void saveTest() {
        // Arrange
        UserCreateDto userCreateDto = UserCreateDto.builder()
                .firstName("Jan")
                .lastName("Deman")
                .nickName("JD")
                .age(30)
                .email("jandeman@gmail.com")
                .build();

        // Act
        userService.save(userCreateDto);

        // Assert
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository, times(1)).save(userCaptor.capture());
        User capturedUser = userCaptor.getValue();

        assertEquals(userCreateDto.getFirstName(), capturedUser.getFirstName());
        assertEquals(userCreateDto.getLastName(), capturedUser.getLastName());
        assertEquals(userCreateDto.getNickName(), capturedUser.getNickName());
        assertEquals(userCreateDto.getAge(), capturedUser.getAge());
        assertEquals(userCreateDto.getEmail(), capturedUser.getEmail());
    }

    // Test for updating a user
    @Test
    void updateTest() {
        // Arrange
        UserUpdateRequestDto userUpdateRequestDto = UserUpdateRequestDto.builder()
                .firstName("Jeff")
                .lastName("Deman")
                .nickName("JeffD")
                .age(27)
                .build();

        User user = User.builder()
                .id("1")
                .firstName("InitialFirstName")
                .lastName("InitialLastName")
                .nickName("InitialNickName")
                .age(25)
                .build();

        when(userRepository.findById(eq("1"))).thenReturn(Optional.of(user));

        // Act
        User result = userService.update("1", userUpdateRequestDto);

        // Assert
        verify(userRepository, times(1)).findById("1");
        verify(userRepository, times(1)).save(user);
        assertEquals(user.getId(), result.getId());
        assertEquals(userUpdateRequestDto.getFirstName(), result.getFirstName());
        assertEquals(userUpdateRequestDto.getLastName(), result.getLastName());
        assertEquals(userUpdateRequestDto.getNickName(), result.getNickName());
        assertEquals(userUpdateRequestDto.getAge(), result.getAge());
    }

    // Test for deleting a user by ID
    @Test
    void deleteByIdTest() {
        // Act
        userService.deleteById("1");

        // Assert
        verify(userRepository, times(1)).deleteById("1");
    }
}
