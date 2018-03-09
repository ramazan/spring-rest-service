package com.kou.rollcall.repositories;

import com.kou.rollcall.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

public interface StudentRepository extends CrudRepository<Student, Long>, JpaRepository<Student, Long>
{
    Student getStudentByName(String name);

    Set<Student> getAllBy();

    List<Student> getStudentsByLessons(String lessonName);

}
