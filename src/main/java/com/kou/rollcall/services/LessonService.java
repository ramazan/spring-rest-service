package com.kou.rollcall.services;

import com.kou.rollcall.model.Lesson;
import com.kou.rollcall.model.Student;

import java.util.List;

public interface LessonService
{
    List<Lesson> getLessonByAcademician_Name(String name);
    Lesson getLessonByName(String name);



}
