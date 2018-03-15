package com.kou.rollcall.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class indexController
{

    @RequestMapping({"", "/", "/index"})
    private String index()
    {
        return "index";
    }

}
