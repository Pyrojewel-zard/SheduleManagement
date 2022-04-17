package com.pyrojewel.Course;

import com.zhuangfei.timetable.model.Schedule;
import com.zhuangfei.timetable.model.ScheduleEnable;

import java.util.List;

;

/**
 * @author Pyrojewel
 */
public class CourseModel implements ScheduleEnable {

    public static final String EXTRAS_ID="extras_id";

    private int id;
    private String name;
    private String teacher;
    /**当前年月日*/
    private String time;
    /**
     * 起始周
     */
    private int weekStart;
    /**
     * 持续周长度
     */
    private int weekLength;
    /**
     * 第几周至第几周上
     */
    private List<Integer> weekList;
    private String place;
    //mark一下room=place
    /**
     * 哪一天上课
     */
    private int dayOfWeek;
    //即day
    /**
     * 开始时间节次
     */
    private int timeStart;
    /**
     * 节次持续长度
     */
    private int timeLength;
    /**
     * 课程难度0-10,默认值为5
     */
    private String term;
    /**
     *  一个随机数，用于对应课程的颜色
     */
    private int colorRandom = 0;
    private int diff = 5;


    public int getColorRandom() {
        return colorRandom;
    }

    public void setColorRandom(int colorRandom) {
        this.colorRandom = colorRandom;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public List<Integer> getWeekList() {
        return weekList;
    }

    public void setWeekList(List<Integer> weekList) {
        this.weekList = weekList;
    }

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

    public int getDiff() {
        return diff;
    }

    public void setDiff(int diff) {
        this.diff = diff;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void printAll() {
        System.out.println(name + "+" + teacher + "+" + weekStart + "+" + weekLength + "+" + place + "+" + dayOfWeek + "+" + timeStart + "+" + timeLength);
    }
    public CourseModel(String term,String name, String place, String teacher, List<Integer> weekList, int timeStart, int timeLength, int dayOfWeek, int colorRandom,String time) {
        super();
        this.term=term;
        this.name = name;
        this.place = place;
        this.teacher = teacher;
        this.weekList=weekList;
        this.timeStart = timeStart;
        this.timeLength = timeLength;
        this.dayOfWeek = dayOfWeek;
        this.colorRandom = colorRandom;
        this.time=time;
    }
    public CourseModel(){
        super();
    }
    @Override
    public Schedule getSchedule() {
        Schedule schedule=new Schedule();
        schedule.setDay(getDayOfWeek());
        schedule.setName(getName());
        schedule.setRoom(getPlace());
        schedule.setStart(getTimeStart());
        schedule.setStep(getTimeLength());
        schedule.setTeacher(getTeacher());
        schedule.setWeekList(getWeekList());
        schedule.setColorRandom(2);
        schedule.putExtras(EXTRAS_ID,getId());
        return schedule;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (obj instanceof CourseModel) {
            CourseModel t = (CourseModel) obj;
            if (t.getName().equals(this.getName()) && t.getTeacher().equals(this.getTeacher()) &&
                    t.weekStart == this.weekStart && t.weekLength == this.weekLength && t.getPlace().equals(this.getPlace()) &&
                    t.dayOfWeek == this.dayOfWeek && t.timeStart == this.timeStart && t.timeLength == this.timeLength) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
}
