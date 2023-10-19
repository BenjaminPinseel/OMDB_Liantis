package OMDB.OMDb_Liantis_Pinseel_Benjamin.Dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponseDto {
    public String firstName;
    public String lastName;
    public String nickName;
    public int age;
}
