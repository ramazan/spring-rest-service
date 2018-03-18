package com.kou.rollcall.controllers;

import com.kou.rollcall.model.Department;
import com.kou.rollcall.model.Lesson;
import com.kou.rollcall.model.RollCall;
import com.kou.rollcall.model.RollCallInfo;
import com.kou.rollcall.model.Student;
import com.kou.rollcall.model.StudentRollCall;
import com.kou.rollcall.repositories.AcademicianRepository;
import com.kou.rollcall.repositories.LessonRepository;
import com.kou.rollcall.repositories.RollCallRepository;
import com.kou.rollcall.repositories.StudentRepository;
import com.kou.rollcall.services.LessonServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/api")
public class LessonController
{

    private final static int WEEK = 17;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private RollCallRepository rollCallRepository;

    @Autowired
    private LessonServiceImpl lessonService;

    @GetMapping("/lessons/{username}/academicianUsername")
    private List<Lesson> getLessonsByAcademician(@PathVariable("username") String username)
    {
        List<Lesson> lessons = lessonRepository.getLessonByAcademician_Username(username);
        return lessons;
    }

    @GetMapping("/lessons/{id}/academicianId")
    private List<Lesson> getLessonsByAcademicianId(@PathVariable("id") String id)
    {
        List<Lesson> lessons = lessonRepository.getLessonsByAcademician_Id(Long.parseLong(id));
        return lessons;
    }

    @GetMapping("/lessons")
    private HashMap<String, List<Lesson>> getLessonsByAcademician()
    {
        HashMap<String, List<Lesson>> returnMap = new HashMap<>();
        List<Lesson> lessons = lessonRepository.findAll();
        returnMap.put("lessons", lessons);
        return returnMap;
    }

    @GetMapping("/lessons/{department}/department")
    private List<Lesson> getLessonsByFaculty(@PathVariable("department") Department department)
    {
        List<Lesson> lessons = lessonService.getLessonsByDepartment(department);
        return lessons;
    }

    @GetMapping("/students/{name}/lessonName")
    private Set<Student> getStudentsByLessonName(@PathVariable("name") String lessonName)
    {
        Lesson lesson = lessonRepository.getLessonByName(lessonName);
        return lesson.getStudents();
    }

    @PostMapping(value = "/lesson/saveStudent")
    private ResponseEntity<Object> saveBook(@RequestBody Student student, @PathParam("lessonId") Long lessonId)
    {
        Lesson lesson = lessonRepository.findOne(lessonId);

        Student studentByNumber = studentRepository.getStudentByNumber(student.getNumber());

        if (studentByNumber != null)
        {
            if (!lesson.getStudents().contains(studentByNumber))
            {
                lesson.getStudents().add(studentByNumber);
                lessonRepository.save(lesson);

                return new ResponseEntity<Object>(true, HttpStatus.OK);
            }

            return new ResponseEntity<Object>("öğrenci zaten kayıtlı!", HttpStatus.OK);
        }
        else
        {
            student.setPassword(student.getNumber().toString());
            studentRepository.save(student);

            studentByNumber = studentRepository.getStudentByNumber(student.getNumber());

            lesson.getStudents().add(studentByNumber);
            lessonRepository.save(lesson);

            return new ResponseEntity<Object>(true, HttpStatus.OK);
        }
    }

    @GetMapping("/lesson/rollcall")
    private ResponseEntity<Object> getStudentsRollCallByLessonId(@PathParam("lessonId") Long lessonId)
    {
        Lesson lesson = lessonRepository.getLessonById(lessonId);
        List<Student> studentList = new ArrayList<>(lesson.getStudents());

        HashMap<String, List> rollcallMap = new HashMap<>();
        List<StudentRollCall> rollCallInfoList = new ArrayList<>();

        for (Student student : studentList)
        {
            List<RollCall> rollCall = rollCallRepository.getRollCallByStudent_IdAndLesson_Id(student.getId(), lessonId);
            StudentRollCall studentRollCall = new StudentRollCall();

            if (rollCall.size() != 0)
            {
                studentRollCall.setOgrenci(rollCall.get(0).getStudent());

                RollCallInfo rollCallInfo = new RollCallInfo();
                rollCallInfo.setDersAdi(lesson.getName());
                rollCallInfo.setDevamBilgisi(rollCall.size());
                rollCallInfo.setDevamsizlikBilgisi(WEEK - rollCall.size());

                studentRollCall.setDevamsizlik(rollCallInfo);
                rollCallInfoList.add(studentRollCall);
            }
            else
            {
                return new ResponseEntity<Object>("false", HttpStatus.OK);
            }
        }

        rollcallMap.put("ogrenci_devam_bilgileri", rollCallInfoList);

        return new ResponseEntity<Object>(rollcallMap, HttpStatus.OK);
    }


//    @PostMapping(value = "/saveStudent")
//    private ResponseEntity<Book> saveBook(@RequestBody Book book)
//    {
//        bookRepository.save(book);
//        log.info("Books Saved  : " + book);
//        return new ResponseEntity<Book>(book, HttpStatus.OK);
//    }


}
