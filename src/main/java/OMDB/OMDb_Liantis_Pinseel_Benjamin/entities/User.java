package OMDB.OMDb_Liantis_Pinseel_Benjamin.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Builder(builderClassName = "UserBuilder")
@Entity
@Table(name = "app_user", schema = "omdb")
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @NotNull
    private String ID;

    @Setter
    @NotNull
    @Size(min = 1, max = 50)
    public String firstName;

    @Setter
    @NotNull
    @Size(min = 1, max = 50)
    public String lastName;

    @Setter
    @NotNull
    @Size(min = 1, max = 50)
    public String nickName;

    @Setter
    @Min(0)
    public int age;

    @Setter
    @Email
    public String email;

}
