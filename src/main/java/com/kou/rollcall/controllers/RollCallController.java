package com.kou.rollcall.controllers;

import com.kou.rollcall.model.Lesson;
import com.kou.rollcall.model.RollCall;
import com.kou.rollcall.model.RollCallInfo;
import com.kou.rollcall.model.Student;
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
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

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
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        date.setTimeZone(TimeZone.getTimeZone("Europe/Istanbul"));
        String timeStamp = date.format(Calendar.getInstance().getTime());

        Long ogrenciId;

        if (studentId.length() > 3)
        {
            Student student = studentRepository.getStudentByNumber(Long.valueOf(studentId));
            ogrenciId = student.getId();
        }
        else
        {
            ogrenciId = Long.valueOf(studentId);
        }

        List<RollCall> yoklama = rollCallRepository.getRollCallByStudent_IdAndLesson_Id(ogrenciId, Long.valueOf(lessonId));

        for (RollCall rolcall : yoklama)
        {
            if (rolcall.getDate().toString().equals(timeStamp))
                return new ResponseEntity<>("false", HttpStatus.OK);
        }

        Set<Lesson> lessons = studentRepository.findOne(studentRepository.findOne(ogrenciId).getId()).getLessons();
        Lesson lesson = lessonRepository.getLessonById(Long.valueOf(lessonId));

        if (lessons.contains(lesson))
        {
            try
            {
                RollCall rollCall = new RollCall();

                rollCall.setStudent(studentRepository.findOne(ogrenciId));
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
            return new ResponseEntity<>("false", HttpStatus.OK);
        }

    }

    @PostMapping(value = "/rollcall/getRollcall")
    private ResponseEntity<Object> getRollCallAcademician(@PathParam("lessonId") String lessonId,
                                                          @PathParam("academicianId") String academicianId)
    {
        List<Lesson> lessons = lessonRepository.getLessonsByAcademician_Id(Long.valueOf(academicianId));
        Lesson lesson = lessonRepository.getLessonById(Long.valueOf(lessonId));

        if (lessons.contains(lesson))
        {

            HashMap<String, List> returnMap = new HashMap<>();

            List<RollCall> yoklama = rollCallRepository.getRollCallByLessonIdAndDate(Long.valueOf(lessonId), Calendar.getInstance().getTime());

            if (yoklama.size() > 0)
            {
                returnMap.put("data", yoklama);
                return new ResponseEntity<>(returnMap, HttpStatus.OK);
            }
            else
                return new ResponseEntity<>("false", HttpStatus.OK);

        }
        else
        {
            return new ResponseEntity<>(lesson.getName() + " isimli ders için yetkiniz bulunmamaktadır!.", HttpStatus.OK);
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
