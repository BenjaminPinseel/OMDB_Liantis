package OMDB.Liantis_Pinseel_Benjamin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    public String firstName;
    public String lastName;
    public String nickName;
    public int age;
}
