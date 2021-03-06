package com.fexli.portable.todosqlite.models;

import android.content.ContentValues;

import java.util.Random;

public class ToDoTask {
    public int id;
    public String name, description, category;// 名称，描述，分类
    public boolean isEmergency, isFinished; // 是否紧急任务，是否已完成
    public int doneTs, difficulty;// 任务完成的时间戳，任务难度分数
    public int increaseTs, taskDay; // 需要的时间(秒) 任务进行日期
    public long taskAt;// 开启任务的时间和
    public static String CREATE_TABLE_INFO = "(" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT," + // 原始index，自增主键
            "name VARCHAR," +//任务名称
            "cts TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +//创建时间，自动生成
            "dts TIMESTAMP," +//程序给出的完成时间，可供快速调用参考
            "emerg BOOLEAN," +//是否为紧急事件
            "describ VARCHAR," +//任务描述
            "category VARCHAR," +//任务分类，暂时使用string分类，由程序区分
            "diff INTEGER," +//手动难度
            "finished BOOLEAN)";//是否已完成

    public static final String ID = "_id";
    public static final String NAME = "name";
    public static final String CREATE_TS = "cts";
    public static final String DONE_TS = "dts";
    public static final String IS_EMERGENCY = "emerg";
    public static final String DESCRIPTION = "describ";
    public static final String CATEGORY = "category";
    public static final String DIFFICULTY = "diff";
    public static final String IS_FINISHED = "finished";

    public ToDoTask(int id, String name, String description, int doneTs, String category, boolean isEmergency, boolean isFinished, int diff) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.doneTs = doneTs;
        this.isEmergency = isEmergency;
        this.isFinished = isFinished;
        this.difficulty = diff;
    }

    public ToDoTask(String name, String description, int doneTs, String category, boolean isEmergency, boolean isFinished, int diff) {
        new ToDoTask(0, name, description, doneTs, category, isEmergency, isFinished, diff);
    }

    public int Arrange() {//TODO:timecost
        this.increaseTs = 5000;//测试时默认5000s，正式模型需要进行Arrange函数构建对于当前项目的用时模型
        return this.increaseTs;
    }

    public String getName() {
        return this.name;
    }

    public void fillContent(ContentValues ctx) {
        ctx.put("name", this.name);
        ctx.put("describ", this.description);
        ctx.put("dts", this.doneTs);
        ctx.put("emerg", this.isEmergency);
        ctx.put("category", this.category);
        ctx.put("finished", this.isFinished);
    }

}
