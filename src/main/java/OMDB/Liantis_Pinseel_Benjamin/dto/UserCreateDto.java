package OMDB.Liantis_Pinseel_Benjamin.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder()
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateDto {
    @NotBlank
    private String firstName;
    private String lastName;
    private String nickName;
    private int age;
    private String email;
}
