package OMDB.Liantis_Pinseel_Benjamin.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponseDto {
    private String id;
    private String firstName;
    private String lastName;
    private String nickName;
    private int age;
}
