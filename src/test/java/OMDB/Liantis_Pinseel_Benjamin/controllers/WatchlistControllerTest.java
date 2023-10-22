package OMDB.Liantis_Pinseel_Benjamin.controllers;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import OMDB.Liantis_Pinseel_Benjamin.dto.WatchlistCreateDto;
import OMDB.Liantis_Pinseel_Benjamin.dto.WatchlistResponseDto;
import OMDB.Liantis_Pinseel_Benjamin.dto.WatchlistUpdateRequestDto;
import OMDB.Liantis_Pinseel_Benjamin.entities.Watchlist;
import OMDB.Liantis_Pinseel_Benjamin.mappers.WatchlistMapper;
import OMDB.Liantis_Pinseel_Benjamin.services.WatchlistService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class WatchlistControllerTest {

    @Mock
    WatchlistService watchlistService;

    @Mock
    WatchlistMapper watchlistMapper;

    @InjectMocks
    WatchlistController watchlistController;

    @Test
    void testAddMovie() {
        // Arrange
        WatchlistResponseDto watchlistResponseDto = new WatchlistResponseDto();
        String watchlistId = "1";
        String movieId = "123";
        String userId = "user1";
        watchlistResponseDto.setUserId(userId);
        when(watchlistService.findById(watchlistId)).thenReturn(watchlistResponseDto);
        when(watchlistService.addMovie(watchlistId, movieId)).thenReturn(watchlistResponseDto);

        // Act
        ResponseEntity<?> result = watchlistController.addMovie(watchlistId, movieId, userId);

        // Assert
        assertEquals(ResponseEntity.ok(watchlistResponseDto), result);
        verify(watchlistService, times(1)).findById(watchlistId);
        verify(watchlistService, times(1)).addMovie(watchlistId, movieId);
    }

    @Test
    void testRemoveMovie() {
        // Arrange
        WatchlistResponseDto watchlistResponseDto = new WatchlistResponseDto();
        String watchlistId = "1";
        String movieId = "123";
        String userId = "user1";
        watchlistResponseDto.setUserId(userId);
        when(watchlistService.findById(watchlistId)).thenReturn(watchlistResponseDto);
        when(watchlistService.removeMovie(watchlistId, movieId)).thenReturn(watchlistResponseDto);

        // Act
        ResponseEntity<?> result = watchlistController.removeMovie(watchlistId, movieId, userId);

        // Assert
        assertEquals(ResponseEntity.ok(watchlistResponseDto), result);
        verify(watchlistService, times(1)).findById(watchlistId);
        verify(watchlistService, times(1)).removeMovie(watchlistId, movieId);
    }

    @Test
    void testFindByUserId() {
        // Arrange
        List<WatchlistResponseDto> watchlistResponseDtos = new ArrayList<>();
        String userId = "user1";
        when(watchlistService.findByUserId(userId)).thenReturn(watchlistResponseDtos);

        // Act
        List<WatchlistResponseDto> result = watchlistController.findByUserId(userId);

        // Assert
        assertEquals(watchlistResponseDtos, result);
        verify(watchlistService, times(1)).findByUserId(userId);
    }

    @Test
    void testFindById() {
        // Arrange
        WatchlistResponseDto watchlistResponseDto = new WatchlistResponseDto();
        String id = "1";
        when(watchlistService.findById(id)).thenReturn(watchlistResponseDto);

        // Act
        WatchlistResponseDto result = watchlistController.findById(id);

        // Assert
        assertEquals(watchlistResponseDto, result);
        verify(watchlistService, times(1)).findById(id);
    }

    @Test
    void testPost() {
        // Arrange
        WatchlistCreateDto watchlistCreateDto = new WatchlistCreateDto();
        String userId = "user1";

        // Act
        watchlistController.post(watchlistCreateDto, userId);

        // Assert
        verify(watchlistService, times(1)).save(watchlistCreateDto, userId);
    }

    @Test
    void testUpdate() {
        // Arrange
        WatchlistUpdateRequestDto watchlistUpdateRequestDto = new WatchlistUpdateRequestDto();
        WatchlistResponseDto watchlistResponseDto = new WatchlistResponseDto();
        Watchlist watchlist = new Watchlist();
        when(watchlistService.update(watchlistUpdateRequestDto)).thenReturn(watchlist);
        when(watchlistMapper.mapWatchlistToWatchlistResponseDto(watchlist)).thenReturn(watchlistResponseDto);

        // Act
        WatchlistResponseDto result = watchlistController.update(watchlistUpdateRequestDto);

        // Assert
        assertEquals(watchlistResponseDto, result);
        verify(watchlistService, times(1)).update(watchlistUpdateRequestDto);
        verify(watchlistMapper, times(1)).mapWatchlistToWatchlistResponseDto(watchlist);
    }

    @Test
    void testDelete() {
        // Arrange
        String id = "1";

        // Act
        watchlistController.delete(id);

        // Assert
        verify(watchlistService, times(1)).deleteById(id);
    }

}
