package com.kou.rollcall.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Lesson
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "academician_id")
    @JsonIgnoreProperties("lessons")
//    @JsonIgnore
    private Academician academician;

    @ManyToMany(fetch = FetchType.LAZY)
    @JsonIgnoreProperties("lessons")
    @JsonIgnore
    private Set<Student> students = new HashSet<>();

//    @Enumerated(value = EnumType.STRING)
//    private Department department;
//
//    @Enumerated(value = EnumType.STRING)
//    private Faculty faculty;

    private String clock;
    private String day;
    private String location;


//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(name = "lessons_students",
//            joinColumns = @JoinColumn(name = "lesson_id"),
//            inverseJoinColumns = @JoinColumn(name = "student_id"))
//    private Set<Student> students = new HashSet<>();


    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Academician getAcademician()
    {
        return academician;
    }

    public void setAcademician(Academician academician)
    {
        this.academician = academician;
    }

    public Set<Student> getStudents()
    {
        return students;
    }

    public void setStudents(Set<Student> students)
    {
        this.students = students;
    }

    public String getClock()
    {
        return clock;
    }

    public void setClock(String clock)
    {
        this.clock = clock;
    }

    public String getDay()
    {
        return day;
    }

    public void setDay(String day)
    {
        this.day = day;
    }

    public String getLocation()
    {
        return location;
    }

    public void setLocation(String location)
    {
        this.location = location;
    }
}
