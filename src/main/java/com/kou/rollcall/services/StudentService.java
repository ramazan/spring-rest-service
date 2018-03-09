package com.kou.rollcall.services;

import com.kou.rollcall.model.Student;

import java.util.List;


public interface StudentService
{
    List<Student> getStudentsListByLessonName(String lessonName);
}
