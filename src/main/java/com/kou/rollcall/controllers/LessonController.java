package com.kou.rollcall.controllers;

import com.kou.rollcall.model.Academician;
import com.kou.rollcall.model.Department;
import com.kou.rollcall.model.Faculty;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    private final static int WEEK = 14;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private RollCallRepository rollCallRepository;

    @Autowired
    private LessonServiceImpl lessonService;

    @Autowired
    private AcademicianRepository academicianRepository;

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
    private ResponseEntity<Object> getStudentsByLessonName(@PathVariable("name") String lessonName)
    {
        HashMap<String, Set> returnMap = new HashMap<>();

        Lesson lesson = lessonRepository.getLessonByName(lessonName);

        if (lesson != null)
        {
            returnMap.put("data", lesson.getStudents());

            return new ResponseEntity<>(returnMap, HttpStatus.OK);
        }

        return new ResponseEntity<>("false", HttpStatus.OK);

    }

    @GetMapping("/students/{id}/lessonId")
    private ResponseEntity<Object> getStudentsByLessonID(@PathVariable("id") String lessonId)
    {
        HashMap<String, Set> returnMap = new HashMap<>();

        Lesson lesson = lessonRepository.getLessonById(Long.valueOf(lessonId));

        if (lesson != null)
        {
            returnMap.put("data", lesson.getStudents());

            return new ResponseEntity<>(returnMap, HttpStatus.OK);
        }

        return new ResponseEntity<>("false", HttpStatus.OK);

    }

    @PostMapping(value = "/lesson/saveStudent")
    private ResponseEntity<Object> saveStudent(@RequestBody Student student, @PathParam("lessonId") Long lessonId)
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
        }

        if (rollCallInfoList.size() != 0)
        {
            rollcallMap.put("ogrenci_devam_bilgileri", rollCallInfoList);

            return new ResponseEntity<Object>(rollcallMap, HttpStatus.OK);
        }
        else // eğer hiç devamsızlık bilgisi yoksa
        {
            return new ResponseEntity<Object>("false", HttpStatus.OK);
        }
    }


    @PostMapping(value = "/saveAcademician")
    private ResponseEntity<Object> saveAcademician(@RequestBody Academician academician)
    {

        if (academician != null)
        {
            try
            {
                academician.setPassword("1");
                academician.setDepartment(Department.Bilgisiyar_Mühendisliği);
                academician.setFaculty(Faculty.Mühendislik);
                academicianRepository.save(academician);

                return new ResponseEntity<Object>(true, HttpStatus.OK);
            }
            catch (Exception e)
            {
                return new ResponseEntity<Object>(false, HttpStatus.OK);

            }
        }
        else
        {
            return new ResponseEntity<Object>(false, HttpStatus.OK);

        }

    }


    @PostMapping(value = "/lesson/saveAcademicianLessons")
    private ResponseEntity<Object> saveAcademicianLessons(@RequestParam("academicianId") String academicianId, @RequestParam("lessonId") Long lessonId)
    {
        Lesson lesson = lessonRepository.findOne(lessonId);
        Academician academician = lesson.getAcademician();

        if (academician == null)
        {
            academician = academicianRepository.findOne(Long.valueOf(academicianId));
            lesson.setAcademician(academician);
            lessonRepository.save(lesson);

            return new ResponseEntity<Object>(true, HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<Object>(false, HttpStatus.OK);

        }

    }

    @PostMapping(value = "/lesson/saveLesson")
    private ResponseEntity<Object> saveLessons(@RequestBody Lesson lesson)
    {
        if (lesson.getName() != null || lesson.getClock() != null || lesson.getDay() != null)
        {
            Academician academician = academicianRepository.findOne(lesson.getAcademician().getId());

            lesson.setAcademician(academician);
            lessonRepository.save(lesson);

            lesson = lessonRepository.getLessonByName(lesson.getName());

            academician.getLessons().add(lesson);
            academicianRepository.save(academician);

            return new ResponseEntity<Object>(true, HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<Object>(false, HttpStatus.OK);

        }

    }

    @PostMapping(value = "/lesson/updateLesson")
    private ResponseEntity<Object> updateLesson(@RequestBody Lesson lesson)
    {
        if (lesson.getId() != null)
        {
            Academician academician = academicianRepository.findOne(lesson.getAcademician().getId());

            lesson.setAcademician(academician);
            lessonRepository.save(lesson);

            lesson = lessonRepository.getLessonById(lesson.getId());

            academician.getLessons().add(lesson);
            academicianRepository.save(academician);

            return new ResponseEntity<Object>(true, HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<Object>(false, HttpStatus.OK);

        }

    }

    @GetMapping(value = "/lesson/getAll")
    private ResponseEntity<Object> getAllLesson()
    {

        HashMap<String, List> lessonMap = new HashMap<>();

        lessonMap.put("dersler", lessonRepository.findAll());

        return new ResponseEntity<Object>(lessonMap, HttpStatus.OK);
    }

    @PostMapping(value = "/lesson/deleteLesson")
    private ResponseEntity<Object> deleteLesson(@RequestParam("lessonId") String LessonId)
    {
        try
        {
            lessonRepository.delete(Long.valueOf(LessonId));
            return new ResponseEntity<Object>(true, HttpStatus.OK);

        }
        catch (Exception e)
        {
            return new ResponseEntity<>(false, HttpStatus.OK);
        }

    }


    @GetMapping(value = "/lesson/getLesson")
    private ResponseEntity<Object> getLesson(@RequestParam("lessonId") String lessonId)
    {

        HashMap<String, Lesson> lessonMap = new HashMap<>();

        lessonMap.put("ders", lessonRepository.findOne(Long.valueOf(lessonId)));

        return new ResponseEntity<Object>(lessonMap, HttpStatus.OK);
    }

}
