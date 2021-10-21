package cloud.javacoder.springdatajpa.dao;

import cloud.javacoder.springdatajpa.entity.Course;
import org.hibernate.SessionFactory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends CrudRepository <Course, Long> {

}
