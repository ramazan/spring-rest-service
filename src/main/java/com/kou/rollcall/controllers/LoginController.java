package com.kou.rollcall.controllers;

import com.kou.rollcall.model.Student;
import com.kou.rollcall.repositories.AcademicianRepository;
import com.kou.rollcall.repositories.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

@Slf4j
@RestController
@RequestMapping("/api")
public class LoginController
{
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private AcademicianRepository academicianRepository;


    /*
     * Kullanıcı bilgilerinin alınıp valide edilen method
     */
    @PostMapping(value = "/login")
    private ResponseEntity<Object> login(@PathParam("username") String username, @PathParam("password") String password)
    {
        if (username.matches("[0-9]+") && username.length() > 2)
        {
            Student student = studentRepository.getStudentByNumberAndPassword(Long.valueOf(username), password);

            if (student != null)
                return new ResponseEntity<Object>(student, HttpStatus.OK);
        }
        else
        {
            Object academician = academicianRepository.getAcademicianByUsernameAndPassword(username, password);

            if (academician != null)
            {
                return new ResponseEntity<Object>(academician, HttpStatus.OK);
            }

        }

        return new ResponseEntity<Object>("{}", HttpStatus.OK);
    }
}
