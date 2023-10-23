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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

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
        PageDto<UserResponseDto> pageDto = new PageDto<UserResponseDto>();
        when(userService.findAll(anyInt(), anyInt())).thenReturn(Page.empty());
        when(userMapper.mapToPageDto(any())).thenReturn(pageDto);

        // Act
        PageDto<UserResponseDto> result = userController.findAll(0, 10);

        // Assert
        verify(userService, times(1)).findAll(0, 10);
        verify(userMapper, times(1)).mapToPageDto(any());
        assertEquals(pageDto, result);
    }

    // Test for finding a user by ID
    @Test
    void findByIdTest() {
        // Arrange
        UserResponseDto userResponseDto = new UserResponseDto();
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
        String userId = "user1";
        UserCreateDto userCreateDto = new UserCreateDto();

        // Act
        userController.create(userCreateDto);

        // Assert
        verify(userService, times(1)).save(any());
    }

    // Test for updating a user
    @Test
    void updateTest() {
        // Arrange
        UserResponseDto userResponseDto = new UserResponseDto();
        UserUpdateRequestDto userUpdateRequestDto = UserUpdateRequestDto.builder().build();
        when(userService.update(anyString(), any())).thenReturn(new User());
        when(userMapper.mapUserToUserResponseDto(any())).thenReturn(userResponseDto);

        // Act
        UserResponseDto result = userController.update("1", userUpdateRequestDto);

        // Assert
        verify(userService, times(1)).update("1", userUpdateRequestDto);
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
