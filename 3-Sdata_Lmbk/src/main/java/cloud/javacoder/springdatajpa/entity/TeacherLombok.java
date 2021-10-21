package cloud.javacoder.springdatajpa.entity;

import lombok.*;

import javax.persistence.*;

@Data  // getters, setters, toString
@NoArgsConstructor
@RequiredArgsConstructor // id excluded; requires are those annotated with @NonNull

/*@Entity
@Table(uniqueConstraints = @UniqueConstraint(name = "un_teacher_email", columnNames = {"email"}))*/
public class TeacherLombok {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) //default anyway
    private long id;
    @NonNull private String name;
    @NonNull private String pictureURL;
    @NonNull private String email;


}
