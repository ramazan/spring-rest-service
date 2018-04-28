package com.kou.rollcall.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class main
{
    public static void main(String[] args) throws ParseException
    {
        //1. Create a Date from String
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-d-MM hh:mm:ss");
        String dateInString = "2018-26-04 10:20:56";
        Date date = sdf.parse(dateInString);
        main obj = new main();

        //2. Test - Convert Date to Calendar
        Calendar calendar = obj.dateToCalendar(date);
        System.out.println(calendar.getTime());

    }

    //Convert Date to Calendar
    private Calendar dateToCalendar(Date date)
    {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;

    }
}

