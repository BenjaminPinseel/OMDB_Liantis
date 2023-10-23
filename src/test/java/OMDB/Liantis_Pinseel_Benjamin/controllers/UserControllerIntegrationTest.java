package OMDB.Liantis_Pinseel_Benjamin.controllers;

import OMDB.Liantis_Pinseel_Benjamin.dto.PageDto;
import OMDB.Liantis_Pinseel_Benjamin.dto.UserCreateDto;
import OMDB.Liantis_Pinseel_Benjamin.dto.UserResponseDto;
import OMDB.Liantis_Pinseel_Benjamin.dto.UserUpdateRequestDto;
import OMDB.Liantis_Pinseel_Benjamin.entities.User;
import OMDB.Liantis_Pinseel_Benjamin.mappers.UserMapper;
import OMDB.Liantis_Pinseel_Benjamin.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerIntegrationTest {

    @Mock
    UserService userService;

    @Mock
    UserMapper userMapper;

    @InjectMocks
    UserController userController;

    // Test for finding all users
    @Test
    void findAllTest() {
        // Arrange
        Set<UserResponseDto> userResponseDtos = Set.of(UserResponseDto.builder()
                .firstName("Jan")
                .lastName("Deman")
                .nickName("JD")
                .age(30)
                .build());

        PageDto<UserResponseDto> pageDto = PageDto.<UserResponseDto>builder()
                .totalPages(1)
                .totalElements(1)
                .responseDtos(userResponseDtos)
                .build();
        when(userService.findAll(1, 10 )).thenReturn(Page.empty());
        when(userMapper.mapToPageDto(any())).thenReturn(pageDto);

        // Act
        PageDto<UserResponseDto> result = userController.findAll(1, 10);

        // Assert
        verify(userService, times(1)).findAll(1, 10);
        verify(userMapper, times(1)).mapToPageDto(any());
        assertEquals(pageDto, result);
    }

    // Test for finding a user by ID
    @Test
    void findByIdTest() {
        // Arrange
        UserResponseDto userResponseDto = UserResponseDto.builder()
                .firstName("Jan")
                .lastName("Deman")
                .nickName("JD")
                .age(30)
                .build();
        when(userService.findById(anyString())).thenReturn(Optional.of(new User()));
        when(userMapper.mapUserToUserResponseDto(any())).thenReturn(userResponseDto);

        // Act
        UserResponseDto result = userController.findById("1");

        // Assert
        verify(userService, times(1)).findById("1");
        verify(userMapper, times(1)).mapUserToUserResponseDto(any());
        assertEquals(userResponseDto, result);
    }

    // Test for saving a user
    @Test
    void createTest() {
        // Arrange
        UserCreateDto userCreateDto = UserCreateDto.builder()
                .firstName("Jan")
                .lastName("Deman")
                .nickName("JD")
                .age(30)
                .build();

        // Act
        userController.create(userCreateDto);

        // Assert
        verify(userService, times(1)).save(eq(userCreateDto));
    }

    // Test for updating a user
    @Test
    void updateTest() {
        // Arrange
        User user = User.builder()
                .firstName("Jan")
                .lastName("Deman")
                .nickName("JD")
                .age(30)
                .build();
        UserResponseDto userResponseDto = UserResponseDto.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .nickName(user.getNickName())
                .age(user.getAge())
                .build();
        String userId = "userId";
        UserUpdateRequestDto userUpdateRequestDto = UserUpdateRequestDto.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .age(user.getAge())
                .nickName(user.getNickName())
                .build();
        when(userService.update(userId, userUpdateRequestDto)).thenReturn(user);
        when(userMapper.mapUserToUserResponseDto(any())).thenReturn(userResponseDto);

        // Act
        UserResponseDto result = userController.update(userId, userUpdateRequestDto);

        // Assert
        verify(userService, times(1)).update(userId, userUpdateRequestDto);
        verify(userMapper, times(1)).mapUserToUserResponseDto(any());
        assertEquals(userResponseDto, result);
    }

    // Test for deleting a user by ID
    @Test
    void deleteTest() {
        // Act
        userController.delete("1");

        // Assert
        verify(userService, times(1)).deleteById("1");
    }
}
