package com.kou.rollcall.controllers;

import com.kou.rollcall.model.Department;
import com.kou.rollcall.model.Lesson;
import com.kou.rollcall.model.Student;
import com.kou.rollcall.repositories.AcademicianRepository;
import com.kou.rollcall.repositories.LessonRepository;
import com.kou.rollcall.services.LessonServiceImpl;
import com.kou.rollcall.services.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Enumerated;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Slf4j
@RestController("/api/lessons")
public class LessonController
{

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private StudentService studentService;

    @Autowired
    private AcademicianRepository academicianRepository;

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

//    @PostMapping(value = "/saveBook")
//    private ResponseEntity<Book> saveBook(@RequestBody Book book)
//    {
//        bookRepository.save(book);
//        log.info("Books Saved  : " + book);
//        return new ResponseEntity<Book>(book, HttpStatus.OK);
//    }
//
//    @GetMapping(value = "/getBooks")
//    private List<Book> getBooks()
//    {
//        return bookRepository.findAll();
//    }
//
//    @GetMapping(value = "/getAuthors")
//    private List<Author> getAuthors()
//    {
//        return authorRepository.findAll();
//    }
//
//    @GetMapping(value = {"/getBooks/{authorName}", "/getAuthors/{authorName}"})
//    private List<Book> getBooks(@PathVariable("authorName") String author)
//    {
//        return bookRepository.getAllByAuthor(authorRepository.getAuthorByName(author));
//    }
//
//    @GetMapping(value = "/getBooks/{authorName}/{bookName}")
//    private Book getBooks(@PathVariable("authorName") String author, @PathVariable(value = "bookName") String name)
//    {
//        return bookRepository.getBookByAuthorAndBookName(authorRepository.getAuthorByName(author), name);
//    }

//
//    @GetMapping("/lesson/academician/{name}")
//    private Set<Lesson> getStudentsByLesson(@PathVariable("name") String lessonName)
//    {
//        Lesson lesson = lessonRepository.getLessonByName(lessonName);
//        return lesson.getStudents();
//    }

}
