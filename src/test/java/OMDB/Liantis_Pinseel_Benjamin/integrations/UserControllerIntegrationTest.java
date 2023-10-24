package OMDB.Liantis_Pinseel_Benjamin.integrations;

import OMDB.Liantis_Pinseel_Benjamin.dto.UserCreateDto;
import OMDB.Liantis_Pinseel_Benjamin.dto.UserResponseDto;
import OMDB.Liantis_Pinseel_Benjamin.dto.UserUpdateRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/it.properties")
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private Flyway flyway;

    @BeforeEach
    void clearDatabase(){
        flyway.clean();
        flyway.migrate();
    }


    @Test
    @Sql("/userData.sql")
    public void givenValidUserId_whenRequestingUserController_thenUserControllerProvidesCorrectUserInformation() throws Exception {
        final MvcResult result = mockMvc.perform(get("/user/{id}", "id"))
                .andExpect(status().is(200))
                .andReturn();

        final UserResponseDto user = objectMapper.readValue(result.getResponse().getContentAsString(), UserResponseDto.class);

        assertNotNull("User response should not be null", user);
        assertEquals( "firstname", user.getFirstName(), "Expected firstName not found");
        assertEquals( "lastname", user.getLastName(), "Expected lastName not found");
        assertEquals(30, user.getAge(), "Expected age not found");
        assertEquals("nickname", user.getNickName(), "Expected nickname not found");

    }
    @Test
    @Sql("/userData.sql")
    public void givenValidUserCreateDto_whenCreateEndpointCalled_thenUserShouldBeCreated() throws Exception {
        UserCreateDto userCreateDto = UserCreateDto.builder()
                .firstName("Jan")
                .lastName("Deman")
                .age(25)
                .nickName("JD")
                .email("jandeman@random.com")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userCreateDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }
    @Test
    @Sql("/userData.sql")
    public void givenValidUserUpdateRequestDto_whenUpdateEndpointCalled_thenUserShouldBeUpdated() throws Exception {
        UserUpdateRequestDto userUpdateRequestDto = UserUpdateRequestDto.builder()
                .firstName("UpdatedFirstName")
                .lastName("UpdatedLastName")
                .age(30)
                .nickName("updatedNickname")
                .email("updatedEmail@random.com")
                .build();

        String id = "id";
        mockMvc.perform(MockMvcRequestBuilders.put("/user/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userUpdateRequestDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }
    @Test
    @Sql("/userData.sql")
    public void givenValidUserId_whenDeleteEndpointCalled_thenUserShouldBeDeleted() throws Exception {
        String id = "id";

        mockMvc.perform(MockMvcRequestBuilders.delete("/user/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

    }
}