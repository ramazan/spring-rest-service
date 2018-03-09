package com.kou.rollcall.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
@ToString(exclude = "lessons")
@Data
public class Academician
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    private String username;

    @OneToMany(mappedBy = "academician")
    @JsonIgnoreProperties("academician")
    private Set<Lesson> lessons = new HashSet<>();

}
