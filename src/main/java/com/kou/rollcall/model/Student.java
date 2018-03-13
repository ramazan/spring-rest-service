package com.kou.rollcall.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.context.annotation.Primary;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
@EqualsAndHashCode(exclude = {"lessons"})
@ToString(exclude = {"lessons"})
@Data
public class Student
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    private Long number;
    private String surname;
    private String macId;

    @JsonIgnore
    private String password;


    @ManyToMany(mappedBy = "students",fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = "students")
    @JsonIgnore
    private Set<Lesson> lessons = new HashSet<>();
}
