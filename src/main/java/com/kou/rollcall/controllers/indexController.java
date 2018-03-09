package com.kou.rollcall.controllers;

import com.kou.rollcall.repositories.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class indexController
{

    @Autowired
    private LessonRepository lessonRepository;

    @RequestMapping({"", "/", "/index"})
    private String index()
    {
        return "index";
    }

}
