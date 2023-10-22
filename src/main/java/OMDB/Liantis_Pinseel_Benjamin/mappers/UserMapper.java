package OMDB.Liantis_Pinseel_Benjamin.mappers;

import OMDB.Liantis_Pinseel_Benjamin.dto.PageDto;
import OMDB.Liantis_Pinseel_Benjamin.dto.UserCreateDto;
import OMDB.Liantis_Pinseel_Benjamin.dto.UserResponseDto;
import OMDB.Liantis_Pinseel_Benjamin.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserMapper {
    public PageDto<UserResponseDto> mapToPageDto(Page<User> usersPage) {
        Set<UserResponseDto> userResponseDtos = usersPage.getContent()
                .stream()
                .map(this::mapUserToUserResponseDto)
                .collect(Collectors.toSet());

        return new PageDto<UserResponseDto>(
                usersPage.getTotalPages(),
                usersPage.getTotalElements(),
                userResponseDtos

        );
    }

    public UserResponseDto mapUserToUserResponseDto(User user) {
        return UserResponseDto.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .nickName(user.getNickName())
                .age(user.getAge())
                .build();
    }

    public User mapUserCreateDtoToUser(UserCreateDto userCreateDto) {
        return User.builder()
                .firstName(userCreateDto.getFirstName())
                .lastName(userCreateDto.getLastName())
                .nickName(userCreateDto.getNickName())
                .age(userCreateDto.getAge())
                .build();
    }

}
