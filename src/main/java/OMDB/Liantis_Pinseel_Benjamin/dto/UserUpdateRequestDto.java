package OMDB.Liantis_Pinseel_Benjamin.dto;

import lombok.Data;

@Data
public class UserUpdateRequestDto {
    private String firstName;
    private String lastName;
    private String nickName;
    private int age;
}