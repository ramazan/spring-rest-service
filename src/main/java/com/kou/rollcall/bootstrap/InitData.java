package com.kou.rollcall.bootstrap;

import com.kou.rollcall.model.Academician;
import com.kou.rollcall.model.Lesson;
import com.kou.rollcall.model.Student;
import com.kou.rollcall.repositories.AcademicianRepository;
import com.kou.rollcall.repositories.LessonRepository;
import com.kou.rollcall.repositories.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Slf4j
@Component
public class InitData implements ApplicationListener<ContextRefreshedEvent>
{
    public List<String> lessonNames = new ArrayList<>(Arrays.asList("Spring 5", "Android Firebase", "Asp.Net MVC5",
            "MongoDB Tutorial", "Linux Bash", "MySQL Tutorial"
            , "Hibernate & Spring Boot", "Spring Data Rest", "Entity Framework Tutorial", "Robotics"));

    @Autowired
    private AcademicianRepository academicianRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent)
    {

        Academician academician = academicianRepository.getAcademicianByName("Pınar");
        Academician academician1 = academicianRepository.getAcademicianByName("Ahmet");
        Academician academician2 = academicianRepository.getAcademicianByName("Burak");

        Student Student = studentRepository.getStudentByName("Ramazan");
        Student Student1 = studentRepository.getStudentByName("Umut");
        Student Student2 = studentRepository.getStudentByName("İsmail");

        Random rand = new Random();

        List<Academician> academicianList = new ArrayList<>(Arrays.asList(academician, academician1, academician2));


        for (String lessonName : lessonNames)
        {
            Lesson lesson = new Lesson();
            lesson.setAcademician(academicianList.get(rand.nextInt(3)));
            lesson.setName(lessonName);
            lesson.getStudents().add(Student);
            lesson.getStudents().add(Student1);
            lesson.getStudents().add(Student2);
            lessonRepository.save(lesson);
        }


        lessonRepository.findAll().forEach(System.out::println);
        System.out.println("Ahmetin dersleri " + lessonRepository.getLessonByAcademician_Name("Ahmet"));
        System.out.println("Pınarın dersleri " + lessonRepository.getLessonByAcademician_Name("Pınar"));
        System.out.println("Burak dersleri " + lessonRepository.getLessonByAcademician_Name("Burak"));

        System.out.println("------------------------");
        academicianRepository.findAll().forEach(System.out::println);
        System.out.println("------------------------");
        studentRepository.findAll().forEach(System.out::println);

    }
}
