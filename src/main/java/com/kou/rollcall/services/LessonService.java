package com.kou.rollcall.services;

import com.kou.rollcall.model.Department;
import com.kou.rollcall.model.Lesson;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LessonService
{
    List<Lesson> getLessonByAcademician_Name(String name);

    Lesson getLessonByName(String name);

    List<Lesson> getLessonsByDepartment(@Param("department") Department department);


}
