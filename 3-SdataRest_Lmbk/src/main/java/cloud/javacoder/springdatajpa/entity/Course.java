package cloud.javacoder.springdatajpa.entity;

import javax.persistence.*;

import lombok.*;

@Data  // getters, setters, toString
@NoArgsConstructor
@RequiredArgsConstructor // id excluded; requires are those annotated with @NonNull
@AllArgsConstructor

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NonNull private String name;

    @NonNull private Integer workload; // @NonNull is meaningless on a primitive
    @NonNull private Integer rate; //  is meaningless on a primitive

    @NonNull
    // Many Courses to One Teacher
    @ManyToOne  // the Teacher will be the FOREIGN KEY (index) in Course
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_course_teacher"))
    private Teacher teacher;

    // no id filed in the constructor

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", workload=" + workload +
                ", rate=" + rate +
                ", teacher=" + teacher +
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

    public int getWorkload() {
        return workload;
    }

    public void setWorkload(int workload) {
        this.workload = workload;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
}
