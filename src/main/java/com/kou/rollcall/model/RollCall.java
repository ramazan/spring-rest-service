package com.kou.rollcall.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.sql.Date;

@Entity
@Data
public class RollCall
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @JsonIgnore
    private String beaconId;

    @OneToOne
    private Student student;

    @JsonIgnore
    @OneToOne
    @JsonIgnoreProperties(value = "academician")
    private Lesson lesson;

    @CreationTimestamp
    private Date date;
}
