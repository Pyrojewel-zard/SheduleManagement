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
    private int finishDurationUp;//上限完成预期时间
    private int finishDurationDown;//下限
    private int weekDay;// 周几
    private long timeSecond;// 几点

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

    public int getFinishDurationUp() {
        return finishDurationUp;
    }

    public void setFinishDurationUp(int finishDurationUp) {
        this.finishDurationUp = finishDurationUp;

    }

    public int getFinishDurationDown() {
        return finishDurationDown;
    }

    public void setFinishDurationDown(int finishDurationDown) {
        this.finishDurationDown = finishDurationDown;
    }

    public int Arrange() { // 多长时间
        return 5000;
    }

    public void setWeekDayAndTime(int day, long time) {
        this.weekDay = day; // FIXME: 需要在这里从周适配到年月日
        this.timeSecond = time;
    }
    public void setWeekDay(int day) {
        this.weekDay = day;
    }
}