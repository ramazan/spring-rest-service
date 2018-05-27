package com.kou.rollcall.controllers;

import com.kou.rollcall.model.Academician;
import com.kou.rollcall.model.Lesson;
import com.kou.rollcall.model.Student;
import com.kou.rollcall.repositories.AcademicianRepository;
import com.kou.rollcall.repositories.StudentRepository;
import com.kou.rollcall.services.LessonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:51018")
@RequestMapping("/api")
public class LoginController
{
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private AcademicianRepository academicianRepository;

    @Autowired
    private LessonService lessonService;


    /*
     * Kullanıcı bilgilerinin alınıp valide edilen method
     */
    @PostMapping(value = "/login")
    private ResponseEntity<HashMap> login(@PathParam("username") String username, @PathParam("password") String password)
    {
        HashMap<String, Set> returnMap = new HashMap<>();

        if (username.matches("[0-9]+") && username.length() > 2)
        {
            Student student = studentRepository.getStudentByNumberAndPassword(Long.valueOf(username), password);

            if (student != null)
            {
                Set<Lesson> lessons = studentRepository.findOne(student.getId()).getLessons();
                returnMap.put("lessons", lessons);
                Set<Student> students = new HashSet<>();
                students.add(student);
                returnMap.put("data", students);
                return new ResponseEntity<>(returnMap, HttpStatus.OK);
            }
        }
        else
        {
            Academician academician = academicianRepository.getAcademicianByUsernameAndPassword(username, password);

            if (academician != null)
            {
                Set<Lesson> lessons = academicianRepository.findOne(academician.getId()).getLessons();
                returnMap.put("lessons", lessons);
                Set<Academician> academicians = new HashSet<>();
                academicians.add(academician);
                returnMap.put("data", academicians);
                return new ResponseEntity<>(returnMap, HttpStatus.OK);
            }

        }

        return new ResponseEntity<>(returnMap, HttpStatus.OK);
    }

    @PostMapping(value = "/login/changePassword")
    private ResponseEntity<Object> changePassword(@PathParam("username") String username, @PathParam("password") String password,
                                                  @PathParam("newPassword") String newPassword)
    {

        if (username.matches("[0-9]+") && username.length() > 2)
        {
            Student student = studentRepository.getStudentByNumberAndPassword(Long.valueOf(username), password);

            if (student != null)
            {
                student.setPassword(newPassword);
                studentRepository.save(student);
                return new ResponseEntity<>(true, HttpStatus.OK);

            }
        }
        else
        {
            Academician academician = academicianRepository.getAcademicianByUsernameAndPassword(username, password);

            if (academician != null)
            {
                academician.setPassword(newPassword);
                academicianRepository.save(academician);

                return new ResponseEntity<>(true, HttpStatus.OK);

            }
        }
        return new ResponseEntity<>(false, HttpStatus.OK);
    }
}
