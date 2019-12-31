package com.example.notepad;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;



public class Note {

    String name;
    long time;
    String note;
    //String time;


    public Note(String name, long time, String note) {
        this.name = name;
        this.time = time;
        this.note = note;

    }


    public String getName() {
        return name;
    }

    public String getTime() {
        return String.valueOf(System.currentTimeMillis());

//        DateFormat dateFormat = new SimpleDateFormat("yy/MM/dd HH:mm");
//        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
//        Date date = new Date(time);
//        String currentTime = dateFormat.format(date);
//
//        return currentTime;
    }

    public String getNote() {
        return note;
    }



}
