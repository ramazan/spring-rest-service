package com.kou.rollcall.bootstrap;

import com.kou.rollcall.model.Academician;
import com.kou.rollcall.model.Announcement;
import com.kou.rollcall.model.Department;
import com.kou.rollcall.model.Faculty;
import com.kou.rollcall.model.Lesson;
import com.kou.rollcall.model.RollCall;
import com.kou.rollcall.model.Student;
import com.kou.rollcall.repositories.AcademicianRepository;
import com.kou.rollcall.repositories.AnnouncementRepository;
import com.kou.rollcall.repositories.LessonRepository;
import com.kou.rollcall.repositories.RollCallRepository;
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
    public List<String> lessonNames = new ArrayList<>(Arrays.asList("Java Programlama", "Dağıtık Sistemler", "Nesne Yönelimli Programlama",
            "Yazılım Mühendisliği", "Linux Ağ Yönetimi", "Programlama I"
            , "Yazılım Proje Yönetimi", "Kontrol Sistemleri", "Sayısal Veri İletişimi", "Robotik Sistemler"));

    public List<String> days = new ArrayList<>(Arrays.asList("Pazartesi", "Salı", "Çarşamba", "Perşembe", "Cuma"));

    public List<String> location = new ArrayList<>(Arrays.asList("305", "303", "Amfi-B", "Amfi-A", "301"));

    public List<String> clock = new ArrayList<>(Arrays.asList("10:30", "09:00", "14:00", "19:45", "13:30"));

    public List<Faculty> facultyList = new ArrayList<>(Arrays.asList(Faculty.Mühendislik, Faculty.Mühendislik, Faculty.Mühendislik));
    public List<Department> departmentList = new ArrayList<>(Arrays.asList(Department.Bilgisiyar_Mühendisliği, Department.Makine_Mühendisliği, Department.Endüstri_Mühendisliği));

    @Autowired
    private AcademicianRepository academicianRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private RollCallRepository rollCallRepository;

    @Autowired
    private AnnouncementRepository announcementRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent)
    {

        Academician academician = academicianRepository.getAcademicianByUsername("ponaydurdu");
        Academician academician1 = academicianRepository.getAcademicianByUsername("asayar");
        Academician academician2 = academicianRepository.getAcademicianByUsername("binner");

        Student Student = studentRepository.getStudentByName("Ramazan");
        Student Student1 = studentRepository.getStudentByName("Umut");
        Student Student2 = studentRepository.getStudentByName("İsmail");

        Random rand = new Random();

        List<Academician> academicianList = new ArrayList<>(Arrays.asList(academician, academician1, academician2));


        for (String lessonName : lessonNames)
        {
            int rnd = rand.nextInt(3);
            Lesson lesson = new Lesson();
            lesson.setAcademician(academicianList.get(rnd));
            lesson.setName(lessonName);
            lesson.getStudents().add(Student);
            lesson.getStudents().add(Student1);
            lesson.getStudents().add(Student2);
//            lesson.setDepartment(departmentList.get(rnd));
//            lesson.setFaculty(facultyList.get(rnd));
            lesson.setClock(clock.get(rand.nextInt(5)));
            lesson.setDay(days.get(rand.nextInt(5)));
            lesson.setLocation(location.get(rand.nextInt(5)));
            lessonRepository.save(lesson);
        }


        lessonRepository.findAll().forEach(System.out::println);
        System.out.println("Ahmetin dersleri " + lessonRepository.getLessonByAcademician_Username("asayar"));
        System.out.println("Pınarın dersleri " + lessonRepository.getLessonByAcademician_Username("ponaydurdu"));
        System.out.println("Burak dersleri " + lessonRepository.getLessonByAcademician_Username("binner"));

        System.out.println("------------------------");
        academicianRepository.findAll().forEach(System.out::println);
        System.out.println("------------------------");
        studentRepository.findAll().forEach(System.out::println);

        RollCall rollCall = new RollCall();
        rollCall.setBeaconId("AZ1231CVDFG");
        rollCall.setLesson(lessonRepository.findOne(1L));
        rollCall.setStudent(Student);

        RollCall rollCall1 = new RollCall();
        rollCall1.setBeaconId("AZ1231CVDFG");
        rollCall1.setLesson(lessonRepository.findOne(1L));
        rollCall1.setStudent(Student1);

        RollCall rollCall2 = new RollCall();
        rollCall2.setBeaconId("AZ1231CVDFG");
        rollCall2.setLesson(lessonRepository.findOne(1L));
        rollCall2.setStudent(Student2);

        RollCall rollCall6 = new RollCall();
        rollCall6.setBeaconId("AZ1231CVDFG");
        rollCall6.setLesson(lessonRepository.findOne(1L));
        rollCall6.setStudent(Student);

        RollCall rollCall7 = new RollCall();
        rollCall7.setBeaconId("AZ1231CVDFG");
        rollCall7.setLesson(lessonRepository.findOne(1L));
        rollCall7.setStudent(Student1);

        RollCall rollCall8 = new RollCall();
        rollCall8.setBeaconId("AZ1231CVDFG");
        rollCall8.setLesson(lessonRepository.findOne(1L));
        rollCall8.setStudent(Student2);

        RollCall rollCall3 = new RollCall();
        rollCall3.setBeaconId("29RD57SV");
        rollCall3.setLesson(lessonRepository.findOne(2L));
        rollCall3.setStudent(Student);

        RollCall rollCall4 = new RollCall();
        rollCall4.setBeaconId("29RD57SV");
        rollCall4.setLesson(lessonRepository.findOne(2L));
        rollCall4.setStudent(Student1);

        RollCall rollCall5 = new RollCall();
        rollCall5.setBeaconId("29RD57SV");
        rollCall5.setLesson(lessonRepository.findOne(2L));
        rollCall5.setStudent(Student2);

        rollCallRepository.save(rollCall8);
        rollCallRepository.save(rollCall7);
        rollCallRepository.save(rollCall6);
        rollCallRepository.save(rollCall5);
        rollCallRepository.save(rollCall4);
        rollCallRepository.save(rollCall3);
        rollCallRepository.save(rollCall2);
        rollCallRepository.save(rollCall1);
        rollCallRepository.save(rollCall);

        Announcement announcement = new Announcement();
        announcement.setAcademician(academician);
        Lesson lesson = lessonRepository.getLessonByAcademician_Username(academician.getUsername()).get(0);
        announcement.setLesson(lesson);
        announcement.setTitle(lesson.getName() + " Hakkında");
        announcement.setContent(lesson.getName() + " Sunumları 28 Nisan Çarşamba günü 303 nolu sınıfta alınacaktır. Teşekkürler!");
        announcementRepository.save(announcement);

        Announcement announcement1 = new Announcement();
        announcement1.setAcademician(academician1);
        Lesson lesson1 = lessonRepository.getLessonByAcademician_Username(academician1.getUsername()).get(0);
        announcement1.setLesson(lesson1);
        announcement1.setTitle(lesson1.getName() + " Dersi ");
        announcement1.setContent(lesson1.getName() + "  asdhkashdgalsdj gasjhd ahj gdahj ghjsdg asjhdg asjhg das shdgalınacaktır. Teşekkürler!");
        announcementRepository.save(announcement1);

    }
}
