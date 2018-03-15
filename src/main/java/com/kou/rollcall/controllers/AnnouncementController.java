package com.kou.rollcall.controllers;

import com.kou.rollcall.model.Announcement;
import com.kou.rollcall.model.Lesson;
import com.kou.rollcall.repositories.AnnouncementRepository;
import com.kou.rollcall.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/announcement")
public class AnnouncementController
{

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private AnnouncementRepository announcementRepository;

    @PostMapping
    private ResponseEntity<Object> saveAnnouncement(@RequestBody Announcement announcement)
    {
        try
        {
            announcementRepository.save(announcement);
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        catch (NumberFormatException e)
        {
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.OK);
        }
    }


    @GetMapping("/student")
    private ResponseEntity<Object> studentAnnouncement(@PathParam("stundentId") String studentId)
    {
        HashMap<String, List<Announcement>> announcement = new HashMap<>();
        List<Announcement> announcementList = new ArrayList<>();

        try
        {
            Set<Lesson> lessons = studentRepository.findOne(Long.valueOf(studentId)).getLessons();

            for (Lesson lesson : lessons)
            {
                List<Announcement> announcementListByLessonId = announcementRepository.getAnnouncementsByLesson_Id(lesson.getId());

                if (announcementListByLessonId.size() > 0)
                {
                    for (Announcement tmpAnnouncement : announcementListByLessonId)
                    {
                        announcementList.add(tmpAnnouncement);
                    }
                }
            }

            announcementList.sort(Comparator.comparing(o -> o.getId()));
            Collections.reverse(announcementList);

            announcement.put("duyurular", announcementList);

            return new ResponseEntity<>(announcement, HttpStatus.OK);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.OK);
        }
    }

    @GetMapping
    private ResponseEntity<Object> allAnnouncement()
    {
        try
        {
            return new ResponseEntity<>(announcementRepository.findAll(), HttpStatus.OK);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.OK);
        }
    }

}
