package com.kou.rollcall.repositories;

import com.kou.rollcall.model.Lesson;
import com.kou.rollcall.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LessonRepository extends CrudRepository<Lesson, Long>, JpaRepository<Lesson, Long>
{
    List<Lesson> getLessonByAcademician_Name(String name);

    Lesson getLessonByName(String name);
}
