package OMDB.OMDb_Liantis_Pinseel_Benjamin.services;

import OMDB.OMDb_Liantis_Pinseel_Benjamin.dto.UserCreateDto;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.dto.UserUpdateRequestDto;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.entities.User;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.mappers.UserMapper;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
        assertEquals(page, result);
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
    }

    // Test for saving a user
    @Test
    void saveTest() {
        // Arrange
        UserCreateDto userCreateDto = new UserCreateDto();

        // Act
        userService.save(userCreateDto);

        // Assert
        verify(userRepository, times(1)).save(any(User.class));
    }

    // Test for updating a user
    @Test
    void updateTest() {
        // Arrange
        UserUpdateRequestDto userUpdateRequestDto = new UserUpdateRequestDto();
        userUpdateRequestDto.setFirstName("John");

        User user = User.builder()
                .id("1")
                .build();
        when(userRepository.findById(eq("1"))).thenReturn(Optional.of(user));

        // Act
        User result = userService.update("1", userUpdateRequestDto);

        // Assert
        verify(userRepository, times(1)).findById("1");
        verify(userRepository, times(1)).save(user);
        assertEquals(user, result);
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
