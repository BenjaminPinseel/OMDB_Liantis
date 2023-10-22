package OMDB.Liantis_Pinseel_Benjamin.mappers;

import OMDB.Liantis_Pinseel_Benjamin.dto.UserCreateDto;
import OMDB.Liantis_Pinseel_Benjamin.dto.UserResponseDto;
import OMDB.Liantis_Pinseel_Benjamin.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserMapperTests {

    private final UserMapper userMapper = new UserMapper();

    @Test
    void testMapToPageDto() {
        User user = User.builder()
                .firstName("Jan")
                .lastName("Deman")
                .nickName("JDem")
                .age(27)
                .email("jandeman@hotmail.com")
                .build();
        Page<User> userPage = new PageImpl<>(Collections.singletonList(user));

        assertEquals(1, userMapper.mapToPageDto(userPage).getTotalElements());
    }

    @Test
    void testMapUserToUserResponseDto() {
        User user = User.builder()
                .firstName("Jeff")
                .lastName("Deman")
                .nickName("JeffDem")
                .age(29)
                .build();

        UserResponseDto userResponseDto = userMapper.mapUserToUserResponseDto(user);
        assertEquals("Jeff", userResponseDto.getFirstName());
        assertEquals("Deman", userResponseDto.getLastName());
        assertEquals("JeffDem", userResponseDto.getNickName());
        assertEquals(29, userResponseDto.getAge());
    }

    @Test
    void testMapUserCreateDtoToUser() {
        UserCreateDto userCreateDto = UserCreateDto.builder()
                .firstName("Jos")
                .lastName("Deman")
                .nickName("JosDem")
                .age(28)
                .build();

        User user = userMapper.mapUserCreateDtoToUser(userCreateDto);
        assertEquals("Jos", user.getFirstName());
        assertEquals("Deman", user.getLastName());
        assertEquals("JosDem", user.getNickName());
        assertEquals(28, user.getAge());
    }
}