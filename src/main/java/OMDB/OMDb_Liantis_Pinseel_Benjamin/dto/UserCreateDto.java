package OMDB.OMDb_Liantis_Pinseel_Benjamin.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserCreateDto {
    @NotBlank
    private String firstName;
    private String lastName;
    private String nickName;
    private int age;
    private String email;
}
