package OMDB.Liantis_Pinseel_Benjamin.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Builder()
@Entity
@Table(name = "app_user")
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @NotNull
    private String id;

    @Setter
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "firstname")
    public String firstName;
    @Setter
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "lastname")
    public String lastName;
    @Setter
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nickname")
    public String nickName;
    @Setter
    @Min(0)
    public int age;
    @Setter
    @Email
    public String email;

}
