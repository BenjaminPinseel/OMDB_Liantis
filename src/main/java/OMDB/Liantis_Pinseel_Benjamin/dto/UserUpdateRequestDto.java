package OMDB.Liantis_Pinseel_Benjamin.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class UserUpdateRequestDto {
    private String firstName;
    private String lastName;
    private String nickName;
    private int age;
    private String email;
}