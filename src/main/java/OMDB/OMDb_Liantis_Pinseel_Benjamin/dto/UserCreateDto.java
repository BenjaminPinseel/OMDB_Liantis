package OMDB.OMDb_Liantis_Pinseel_Benjamin.dto;

import lombok.Data;

@Data
public class UserCreateDto {
    private String firstName;
    private String lastName;
    private String nickName;
    private int age;
    private String email;
}
