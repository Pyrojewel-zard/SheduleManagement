package com.pyrojewel.Course;//设计课程的格式
import java.io.Serializable;

/**
 * @author Pyrojewel
 */
public class CourseModel implements Serializable {

    int id;
    String name;
    String teacher;
    /**起始周*/
    int weekStart;
    /**持续周长度*/
    int weekLength;
    String place;
    /**哪一天上课*/
    int dayOfWeek;
    /**开始时间节次*/
    int timeStart;
    /**节次持续长度*/
    int timeLength;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public void setWeekStart(int weekStart) {
        this.weekStart = weekStart;
    }

    public void setWeekLength(int weekLength) {
        this.weekLength = weekLength;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public void setTimeStart(int timeStart) {
        this.timeStart = timeStart;
    }

    public void setTimeLength(int timeLength) {
        this.timeLength = timeLength;
    }

    public String getName() {
        return name;
    }

    public String getTeacher() {
        return teacher;
    }

    public int getWeekStart() {
        return weekStart;
    }

    public int getWeekLength() {
        return weekLength;
    }

    public String getPlace() {
        return place;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public int getTimeStart() {
        return timeStart;
    }

    public int getTimeLength() {
        return timeLength;
    }
    public void printAll(){
        System.out.println(name+"+"+teacher+"+"+weekStart+"+"+weekLength+"+"+place+"+"+dayOfWeek+"+"+timeStart+"+"+timeLength);
    }
    @Override
    public boolean equals(Object obj) {
        if(obj==null) {
            return false;
        }
        if(this==obj) {
            return true;
        }
        if(obj instanceof CourseModel) {
            CourseModel t = (CourseModel)obj;
            if(t.getName().equals(this.getName())&&t.getTeacher().equals(this.getTeacher())&&
            t.weekStart==this.weekStart&&t.weekLength==this.weekLength&&t.getPlace().equals(this.getPlace())&&
            t.dayOfWeek==this.dayOfWeek&&t.timeStart==this.timeStart&&t.timeLength==this.timeLength) {
                return true;
            }else {
                return false;
            }
        }
        return false;
    }
}
