package com.kou.rollcall.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.sql.Date;

@Data
@Entity
public class Announcement
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    @CreationTimestamp
    private Date date;

    @OneToOne
    private Academician academician;

    @OneToOne
    @JsonIgnoreProperties("academician")
    private Lesson lesson;
}
