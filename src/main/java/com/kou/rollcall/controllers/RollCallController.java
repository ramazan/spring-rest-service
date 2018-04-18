package com.kou.rollcall.controllers;

import com.kou.rollcall.model.Lesson;
import com.kou.rollcall.model.RollCall;
import com.kou.rollcall.model.RollCallInfo;
import com.kou.rollcall.repositories.LessonRepository;
import com.kou.rollcall.repositories.RollCallRepository;
import com.kou.rollcall.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class RollCallController
{
    private final static int WEEK = 14;

    @Autowired
    private RollCallRepository rollCallRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @PostMapping(value = "/rollcall")
    private ResponseEntity<Object> saveRollCall(@PathParam("lessonId") String lessonId,
                                                @PathParam("studentId") String studentId,
                                                @PathParam("beaconId") String beaconId)
    {
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        List<RollCall> yoklama = rollCallRepository.getRollCallByStudent_IdAndLesson_Id(Long.valueOf(studentId), Long.valueOf(lessonId));

        for (RollCall rolcall: yoklama)
        {
            if (rolcall.getDate().toString().equals(timeStamp))
                  return new ResponseEntity<>("Bu hafta için yoklaman bu derse zaten kaydedildi!", HttpStatus.OK);
        }

        Set<Lesson> lessons = studentRepository.findOne(studentRepository.findOne(Long.valueOf(studentId)).getId()).getLessons();
        Lesson lesson = lessonRepository.getLessonById(Long.valueOf(lessonId));

        if (lessons.contains(lesson))
        {
            try
            {
                RollCall rollCall = new RollCall();

                rollCall.setStudent(studentRepository.findOne(Long.valueOf(studentId)));
                rollCall.setLesson(lessonRepository.findOne(Long.valueOf(lessonId)));
                rollCall.setBeaconId(beaconId);

                rollCallRepository.save(rollCall);
                return new ResponseEntity<>(true, HttpStatus.OK);
            }
            catch (NumberFormatException e)
            {
                e.printStackTrace();
                return new ResponseEntity<>(false, HttpStatus.OK);
            }
        }
        else
        {
            return new ResponseEntity<>("Öğrenci derse kayıtlı değil!", HttpStatus.OK);
        }

    }


    @GetMapping(value = "/rollcall")
    private ResponseEntity<HashMap> getRollCall(@PathParam("lessonId") String lessonId, @PathParam("studentId") String studentId)
    {
        HashMap<String, List> rollcall = new HashMap<>();
        List<RollCall> yoklama = rollCallRepository.getRollCallByStudent_IdAndLesson_Id(Long.valueOf(studentId), Long.valueOf(lessonId));

        rollcall.put("yoklama", yoklama);

        List<Integer> rollcallSize = new ArrayList<>(Arrays.asList(yoklama.size()));
        rollcall.put("devamlilik_sayisi", rollcallSize);

        return new ResponseEntity<>(rollcall, HttpStatus.OK);
    }


    @GetMapping(value = "/rollcall/RollCallInfo")
    private ResponseEntity<HashMap> getRollCallInfo(@PathParam("studentId") String studentId)
    {
        HashMap<String, List> rollcallMap = new HashMap<>();
        List<RollCallInfo> rollCallInfoList = new ArrayList<>();
        Set<Lesson> lessons = studentRepository.findOne(studentRepository.findOne(Long.valueOf(studentId)).getId()).getLessons();

        for (Lesson lesson : lessons)
        {
            List<RollCall> yoklama = rollCallRepository.getRollCallByStudent_IdAndLesson_Id(Long.valueOf(studentId), lesson.getId());
            RollCallInfo rollCallInfo = new RollCallInfo();
            rollCallInfo.setDersAdi(lesson.getName());
            rollCallInfo.setDevamBilgisi(yoklama.size());
            rollCallInfo.setDevamsizlikBilgisi(WEEK - yoklama.size());
            rollCallInfoList.add(rollCallInfo);
        }

        rollcallMap.put("devam_bilgileri", rollCallInfoList);

        return new ResponseEntity<>(rollcallMap, HttpStatus.OK);
    }
}
