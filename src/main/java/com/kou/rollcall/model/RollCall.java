package com.kou.rollcall.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
@Data
public class RollCall
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String beaconId;

    @OneToOne
    private Student student;

    @OneToOne
    @JsonIgnoreProperties(value = "academician")
    private Lesson lesson;
}
