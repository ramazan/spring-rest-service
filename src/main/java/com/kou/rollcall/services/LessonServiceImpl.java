package com.kou.rollcall.services;

import com.kou.rollcall.model.Department;
import com.kou.rollcall.model.Lesson;
import com.kou.rollcall.repositories.LessonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class LessonServiceImpl implements LessonService
{
    @Autowired
    private LessonRepository lessonRepository;
    @Override
    public List<Lesson> getLessonByAcademician_Name(String name)
    {
        return null;
    }

    @Override
    public Lesson getLessonByName(String name)
    {
        log.info("hey i am at service layer!");
        return getLessonByName(name);
    }

    @Override
    public List<Lesson> getLessonsByDepartment(Department department)
    {
        log.info("hey i am at service layer!");
        return lessonRepository.getLessonsByDepartment(department);
    }
}
