package OMDB.OMDb_Liantis_Pinseel_Benjamin.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder()
public class UserCreateDto {
    @NotBlank
    private String firstName;
    private String lastName;
    private String nickName;
    private int age;
    private String email;
}
