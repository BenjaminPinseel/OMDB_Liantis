package OMDB.Liantis_Pinseel_Benjamin.integrations;

import OMDB.Liantis_Pinseel_Benjamin.clients.Movie;
import OMDB.Liantis_Pinseel_Benjamin.clients.MovieClient;
import OMDB.Liantis_Pinseel_Benjamin.dto.MovieListDto;
import OMDB.Liantis_Pinseel_Benjamin.dto.MovieResponseDto;
import OMDB.Liantis_Pinseel_Benjamin.dto.PageDto;
import OMDB.Liantis_Pinseel_Benjamin.helpers.EncryptionUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/it.properties")
public class MovieControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieClient movieClient;

    @MockBean
    private EncryptionUtils encryptionUtils;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void givenValidMovieId_whenRequestingMovieController_thenMovieControllerProvidesCorrectInformation() throws Exception {
        Movie movie = Movie.builder()
                .imdbID("id")
                .title("title")
                .poster("poster")
                .response("True")
                .build();

        when(encryptionUtils.decrypt(any())).thenReturn("decryptedValue");
        when(movieClient.findById(eq("decryptedValue"), eq("id"))).thenReturn(movie);

        final MvcResult result = mockMvc.perform(get("/movie/id/{id}", "id"))
                .andExpect(status().is(200))
                .andReturn();

        final MovieResponseDto response = objectMapper.readValue(result.getResponse().getContentAsString(), MovieResponseDto.class);

        assertEquals(response.getId(), movie.getImdbID());
        assertEquals(response.getTitle(), movie.getTitle());
        assertEquals(response.getPoster(), movie.getPoster());
    }

    @Test
    public void givenValidMovieTitle_whenRequestingMovieController_thenMovieControllerProvidesCorrectInformation() throws Exception {
        Movie movie = Movie.builder()
                .imdbID("id")
                .title("title")
                .poster("poster")
                .response("True")
                .build();

        when(encryptionUtils.decrypt(any())).thenReturn("decryptedValue");
        when(movieClient.findByTitle(eq("decryptedValue"), eq("title"), eq(null), eq(null), eq(null), eq(null))).thenReturn(movie);

        final MvcResult result = mockMvc.perform(get("/movie/title/{title}", "title"))
                .andExpect(status().is(200))
                .andReturn();

        final MovieResponseDto response = objectMapper.readValue(result.getResponse().getContentAsString(), MovieResponseDto.class);

        assertEquals(response.getId(), movie.getImdbID());
        assertEquals(response.getTitle(), movie.getTitle());
        assertEquals(response.getPoster(), movie.getPoster());
    }

    @Test
    public void givenValid_whenRequestingMovieController_thenMovieControllerProvidesCorrectInformation() throws Exception {
        Movie movie = Movie.builder()
                .imdbID("id")
                .title("title")
                .poster("poster")
                .response("True")
                .build();

        MovieListDto movieListDto = new MovieListDto();
        movieListDto.setSearch(new ArrayList<>());
        movieListDto.getSearch().add(movie);
        movieListDto.setTotalResults(1);
        movieListDto.setResponse(true);

        when(encryptionUtils.decrypt(any())).thenReturn("decryptedValue");
        when(movieClient.findAll(eq("decryptedValue"), eq("title"), eq(null), eq(null), eq(1))).thenReturn(movieListDto);

        final MvcResult result = mockMvc.perform(get("/movie").param("title", "title"))
                .andExpect(status().is(200))
                .andReturn();

        final PageDto<MovieResponseDto> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        });

        assertEquals(response.getResponseDtos().size(), 1);
        assertEquals(response.getTotalElements(), 1);
        assertEquals(response.getTotalPages(), 1);

        MovieResponseDto item = (MovieResponseDto) response.getResponseDtos().toArray()[0];
        assertEquals(item.getId(), movie.getImdbID());
        assertEquals(item.getTitle(), movie.getTitle());
    }
}