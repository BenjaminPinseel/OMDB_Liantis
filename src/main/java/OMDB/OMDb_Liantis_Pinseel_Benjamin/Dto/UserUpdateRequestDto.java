package OMDB.OMDb_Liantis_Pinseel_Benjamin.Dto;

import lombok.Data;

@Data
public class UserUpdateRequestDto {
    private String firstName;
    private String lastName;
    private String nickName;
    private int age;
}