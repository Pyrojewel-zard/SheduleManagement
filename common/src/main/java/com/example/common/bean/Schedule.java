package com.example.common.bean;

import java.io.Serializable;

public class Schedule implements Serializable {
    private int id;
    private String name;
    private int year;
    private int month;
    private int day;
    //true:finished, false:unfinished
    private int isfinished;
    private int diffLevel;
    private int finishtime;//上限完成预期时间

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFinished() {
        return isfinished;
    }

    public void setFinish(int isfinished) {
        this.isfinished = isfinished;
    }

    public int getDiffLevel() {
        return diffLevel;
    }

    public void setDiffLevel(int diffLevel) {
        this.diffLevel = diffLevel;
    }

    public int getFinishtime() {
        return finishtime;
    }

    public void setFinishtime(int finishtime) {
        this.finishtime = finishtime;
    }
}