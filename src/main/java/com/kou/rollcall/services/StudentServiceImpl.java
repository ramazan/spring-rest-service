package com.kou.rollcall.services;

import com.kou.rollcall.model.Student;
import com.kou.rollcall.repositories.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class StudentServiceImpl implements StudentService
{
    @Autowired
    private StudentRepository studentRepository;

    @Override
    public List<Student> getStudentsListByLessonName(String lessonName)
    {
//        log.info("hey i am at service layer!");
        List<Student> studentList = studentRepository.getStudentsByLessons(lessonName);
        return studentList;
    }
}
