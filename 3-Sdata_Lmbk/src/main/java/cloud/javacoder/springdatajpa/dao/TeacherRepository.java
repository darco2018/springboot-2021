package cloud.javacoder.springdatajpa.dao;

import cloud.javacoder.springdatajpa.entity.Teacher;
import org.hibernate.SessionFactory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends CrudRepository<Teacher, Long> {

}

