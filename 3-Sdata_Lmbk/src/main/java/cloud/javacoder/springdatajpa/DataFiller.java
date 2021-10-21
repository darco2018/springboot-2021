package cloud.javacoder.springdatajpa;


import cloud.javacoder.springdatajpa.dao.CourseRepository;
import cloud.javacoder.springdatajpa.dao.TeacherRepository;
import cloud.javacoder.springdatajpa.entity.Course;
import cloud.javacoder.springdatajpa.entity.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Service
public class DataFiller {
    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;

    @Autowired
    public DataFiller(CourseRepository courseDAO, TeacherRepository teacherDAO) {
        this.courseRepository = courseDAO;
        this.teacherRepository = teacherDAO;
    }

    @PostConstruct // runs after context loaded
    @Transactional // @EnableTransactionManagement in main class is needed for transactions outside repository like this
    public void fillData(){
        Teacher pj = new Teacher("Profesor Jirafales","https://upload.wikimedia.org/wikipedia/commons/thumb/d/d1/Ruben2017.jpg/245px-Ruben2017.jpg","jirafales@example.com");
        Teacher px = new Teacher("Professor X","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS9uI1Cb-nQ2uJOph4_t96KRvLSMjczAKnHLJYi1nqWXagvqWc4","director@xproject_.com");
        teacherRepository.save(pj);
        teacherRepository.save(px);
        courseRepository.save(new Course("Mathematics", 20, 10, pj));
        courseRepository.save(new Course("Spanish", 20, 10, pj));
        courseRepository.save(new Course("Dealing with unknown", 10, 100, pj));
        courseRepository.save(new Course("Handling your mental power", 50, 100, pj));
        courseRepository.save(new Course("Introduction to psychology", 90, 100, pj));
    }
}
