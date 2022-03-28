package com.example.a15017498.app1_task_manager;

import java.io.Serializable;

/**
 * Created by 15017498 on 10/11/2016.
 */

public class TaskClass implements Serializable {
    private String id;
    private String name;
    private String subject;
    private String desc;
    private String time;
    private String date;



    public TaskClass(String name, String subject, String desc, String time, String date) {
        this.name=name;
        this.subject=subject;
       this.desc=desc;
        this.time=time;
        this.date=date;


    }

    public TaskClass(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return
                "Name='" + name + '\'' + "\n"+
                "Subject='" + subject + '\'' + "\n"+
                "Desc='" + desc + '\'' + "\n"+
                "Time='" + time + '\'' + "\n"+
                "Date='" + date + '\'' + "\n";

    }
}
