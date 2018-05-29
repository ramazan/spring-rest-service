package com.kou.rollcall.controllers;

import com.kou.rollcall.model.Academician;
import com.kou.rollcall.model.Department;
import com.kou.rollcall.model.Faculty;
import com.kou.rollcall.repositories.AcademicianRepository;
import com.kou.rollcall.repositories.LessonRepository;
import com.kou.rollcall.repositories.RollCallRepository;
import com.kou.rollcall.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;

@Controller
@CrossOrigin(origins = "http://localhost:51018")
@RequestMapping("/api/academician")
public class AcademicianController
{


    @Autowired
    private RollCallRepository rollCallRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private AcademicianRepository academicianRepository;

    @GetMapping("/getAcademicians")
    private ResponseEntity<Object> getAcademicians()
    {
        HashMap<String, List> academicianMap = new HashMap<>();

        academicianMap.put("academician", academicianRepository.findAll());

        return new ResponseEntity<Object>(academicianMap, HttpStatus.OK);
    }

    @GetMapping("/getAcademician/{id}")
    private ResponseEntity<Object> getAcademicianWithId(@PathVariable("id") String academicianId)
    {
        HashMap<String, Academician> academicianMap = new HashMap<>();

        academicianMap.put("academician", academicianRepository.findOne(Long.valueOf(academicianId)));

        return new ResponseEntity<Object>(academicianMap, HttpStatus.OK);
    }

    @PostMapping(value = "/changeProfile")
    private ResponseEntity<Object> changeProfile(@RequestBody Academician academician)
    {
        if (academician.getId() != null)
        {
            Academician akademisyen = academicianRepository.findOne(academician.getId());
            akademisyen.setUsername(academician.getUsername());
            akademisyen.setName(academician.getName());
            akademisyen.setSurname(academician.getSurname());
            academicianRepository.save(akademisyen);

            return new ResponseEntity<Object>(true, HttpStatus.OK);
        }

        return new ResponseEntity<>(false, HttpStatus.OK);
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
}
