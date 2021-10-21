package cloud.javacoder.springdatajpa.entity;

import javax.persistence.*;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(name = "un_teacher_email", columnNames = {"email"}))
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) //default anyway
    private long id;
    private String name;
    @Column(unique=true)
    private String pictureURL;
    private String email;

    public Teacher(String name, String pictureURL, String email) {
        this.name = name;
        this.pictureURL = pictureURL;
        this.email = email;
    }

    public Teacher() {
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pictureURL='" + pictureURL + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPictureURL() {
        return pictureURL;
    }

    public void setPictureURL(String pictureURL) {
        this.pictureURL = pictureURL;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
