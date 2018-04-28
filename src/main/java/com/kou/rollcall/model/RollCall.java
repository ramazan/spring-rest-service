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
public class RollCall
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @JsonIgnore
    private String beaconId;

    @JsonIgnore
    @OneToOne
    private Student student;

    @JsonIgnore
    @OneToOne
    @JsonIgnoreProperties(value = "academician")
    private Lesson lesson;

    @CreationTimestamp
    private Date date;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getBeaconId()
    {
        return beaconId;
    }

    public void setBeaconId(String beaconId)
    {
        this.beaconId = beaconId;
    }

    public Student getStudent()
    {
        return student;
    }

    public void setStudent(Student student)
    {
        this.student = student;
    }

    public Lesson getLesson()
    {
        return lesson;
    }

    public void setLesson(Lesson lesson)
    {
        this.lesson = lesson;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }
}
