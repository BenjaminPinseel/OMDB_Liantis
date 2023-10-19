package OMDB.OMDb_Liantis_Pinseel_Benjamin.Helpers;

import OMDB.OMDb_Liantis_Pinseel_Benjamin.Dto.*;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.Entities.User;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.Entities.Watchlist;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class Mapper {
    public PageDto<UserResponseDto> mapToPageDto(Page<User> usersPage) {
        List<UserResponseDto> userResponseDtos = usersPage.getContent()
                .stream()
                .map(this::mapUserToUserResponseDto)
                .collect(Collectors.toList());

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
    public User mapUserCreateDtoToUser(UserCreateDto userCreateDto){
        return User.builder()
                .firstName(userCreateDto.getFirstName())
                .lastName(userCreateDto.getLastName())
                .nickName(userCreateDto.getNickName())
                .age(userCreateDto.getAge())
                .build();
    }

    public WatchlistResponseDto MapWatchlistToWatchlistResponseDto(Watchlist watchlist) {
        return WatchlistResponseDto.builder()
                .title(watchlist.getTitle())
                .description(watchlist.getDescription())
                .build();
    }

    public Watchlist mapWatchlistCreateDtoToWatchlist(WatchlistCreateDto watchlistCreateDto) {
            return Watchlist.builder()
                    .title(watchlistCreateDto.getTitle())
                    .description(watchlistCreateDto.getDescription())
                    .build();
    }

    public WatchlistResponseDto mapWatchlistToWatchlistResponseDto(Watchlist updatedWatchlist) {
        return WatchlistResponseDto.builder()
                .title(updatedWatchlist.getTitle())
                .description(updatedWatchlist.getDescription())
                .build();
    }
}
