package com.kou.rollcall.repositories;

import com.kou.rollcall.model.Department;
import com.kou.rollcall.model.Lesson;
import com.kou.rollcall.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LessonRepository extends CrudRepository<Lesson, Long>, JpaRepository<Lesson, Long>
{
    List<Lesson> getLessonByAcademician_Name(String name);

    @Query("from Lesson where department = :department")
    List<Lesson> getLessonsByDepartment(@Param("department") Department department);

    Lesson getLessonByName(String name);


}
