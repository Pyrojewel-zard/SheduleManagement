package com.example.common.bean;

import java.io.Serializable;

/**
 * @author 28956
 */
public class Schedule implements Serializable {
    private int id;
    private String Title;
    private int year;
    private int month;
    private int day;
    private int isFinished;
    private int diffLevel;
    private int finishTime;//完成预期时间
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

    public String getTitle() {
        return Title;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setTitle(String title) {
        this.Title = title;
    }

    public int getFinished() {
        return isFinished;
    }

    public void setFinish(int isFinished) {
        this.isFinished = isFinished;
    }

    public int getDiffLevel() {
        return diffLevel;
    }

    public void setDiffLevel(int diffLevel) {
        this.diffLevel = diffLevel;
    }

    public int getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(int finishTime) {
        this.finishTime = finishTime;
    }

    public int Arrange() { // 多长时间
        return 5000;
    }
    public Schedule ( int id,String Title,int year,int month,int day,int diffLevel,int finishTime){
        this.id=id;
        this.Title=Title;
        this.year=year;
        this.month=month;
        this.day=day;
        this.diffLevel=diffLevel;
        this.finishTime=finishTime;

    }
    public Schedule(){}
    public void setWeekDayAndTime(int day, long time) {
        this.weekDay = day; // FIXME: 需要在这里从周适配到年月日
        this.timeSecond = time;
    }
    public void setWeekDay(int day) {
        this.weekDay = day;
    }
}