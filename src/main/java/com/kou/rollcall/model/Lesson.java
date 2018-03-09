package com.kou.rollcall.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.Entity;
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
@EqualsAndHashCode(exclude = {"academician"})
@ToString(exclude = {"academician", "students"})
@Data
public class Lesson
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "academician_id")
    @JsonIgnoreProperties("lessons")
    private Academician academician;

    @ManyToMany
    @JsonIgnoreProperties("lessons")
    private Set<Student> students = new HashSet<>();


//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(name = "lessons_students",
//            joinColumns = @JoinColumn(name = "lesson_id"),
//            inverseJoinColumns = @JoinColumn(name = "student_id"))
//    private Set<Student> students = new HashSet<>();

}
