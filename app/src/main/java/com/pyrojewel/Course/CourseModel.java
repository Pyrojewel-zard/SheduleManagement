package com.pyrojewel.Course;//设计课程的格式

import com.zhuangfei.timetable.model.Schedule;
import com.zhuangfei.timetable.model.ScheduleEnable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Pyrojewel
 */
public class CourseModel implements ScheduleEnable {

    public static final String EXTRAS_ID = "extras_id";

    private int id;
    private String name;
    private String teacher;
    /**
     * 当前年月日
     */
    private String time;
    /**
     * 周次信息
     */
    private String weekString;
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
     * 一个随机数，用于对应课程的颜色
     */
    private int colorRandom = 0;
    private int diff = 5;

    public String getWeekString() {
        return weekString;
    }

    public void setWeekString(String weekString) {
        this.weekString = weekString;
    }

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

    public List<Integer> getWeekList(){
        String weeksString=getWeekString();
        List<Integer> weekList=new ArrayList<>();
        if(weeksString==null||weeksString.length()==0) {
            return weekList;
        }

        weeksString=weeksString.replaceAll("[^\\d\\-\\,]", "");
        if(weeksString.indexOf(",")!=-1){
            String[] arr=weeksString.split(",");
            for(int i=0;i<arr.length;i++){
                weekList.addAll(getWeekList2(arr[i]));
            }
        }else{
            weekList.addAll(getWeekList2(weeksString));
        }
        return weekList;
    }

    public static List<Integer> getWeekList2(String weeksString){
        List<Integer> weekList=new ArrayList<>();
        int first=-1,end=-1,index=-1;
        if((index=weeksString.indexOf("-"))!=-1){
            first=Integer.parseInt(weeksString.substring(0,index));
            end=Integer.parseInt(weeksString.substring(index+1));
        }else{
            first=Integer.parseInt(weeksString);
            end=first;
        }
        for(int i=first;i<=end;i++) {
            weekList.add(i);
        }
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
        System.out.println(name + "+" + teacher + "+" + weekString + "+" +  place + "+" + dayOfWeek + "+" + timeStart + "+" + timeLength + "+" + diff);
    }

    public CourseModel(String term, String name, String place, String teacher, List<Integer> weekList, int timeStart, int timeLength, int dayOfWeek, int colorRandom, String time, int diff) {
        super();
        this.term = term;
        this.name = name;
        this.place = place;
        this.teacher = teacher;
        this.weekList = weekList;
        this.timeStart = timeStart;
        this.timeLength = timeLength;
        this.dayOfWeek = dayOfWeek;
        this.colorRandom = colorRandom;
        this.time = time;
        this.diff = diff;
    }

    public CourseModel() {
        super();
    }

    @Override
    public Schedule getSchedule() {
        Schedule schedule = new Schedule();
        schedule.setDay(getDayOfWeek());
        schedule.setName(getName());
        schedule.setRoom(getPlace());
        schedule.setStart(getTimeStart());
        schedule.setStep(getTimeLength());
        schedule.setTeacher(getTeacher());
        schedule.setWeekList(getWeekList());
        schedule.setColorRandom(2);
        schedule.putExtras(EXTRAS_ID, getId());
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
                    t.weekString == this.weekString&& t.getPlace().equals(this.getPlace()) &&
                    t.dayOfWeek == this.dayOfWeek && t.timeStart == this.timeStart && t.timeLength == this.timeLength) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
}
