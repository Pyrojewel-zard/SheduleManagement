package com.example.common.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.common.bean.Schedule;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Pyrojewel
 */
public class ScheduleDao {

    private static final String DB_NAME = "ScheduleManagement";
    private final JeekSQLiteHelper mHelper;

    public ScheduleDao(Context context) {
        mHelper = new JeekSQLiteHelper(context);
    }

    public static ScheduleDao getInstance(Context context) {
        return new ScheduleDao(context);
    }
    public int addSchedule(Schedule schedule) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(JeekDBConfig.COLUMN_NAME, schedule.getName());
        values.put(JeekDBConfig.COLUMN_FINISH, schedule.getFinished());
        values.put(JeekDBConfig.COLUMN_LEVEL, schedule.getDiffLevel());
        values.put(JeekDBConfig.COLUMN_DURATIONU, schedule.getFinishDurationUp());
        values.put(JeekDBConfig.COLUMN_DURATIOND, schedule.getFinishDurationDown());
        values.put(JeekDBConfig.COLUMN_DDL_YEAR, schedule.getYear());
        values.put(JeekDBConfig.COLUMN_DDL_MONTH, schedule.getMonth());
        values.put(JeekDBConfig.COLUMN_DDL_DAY, schedule.getDay());
        long row = db.insert(JeekDBConfig.TABLE_NAME, null, values);
        db.close();
        return row > 0 ? getLastScheduleId() : 0;
    }
    private int getLastScheduleId() {
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.query(JeekDBConfig.TABLE_NAME, null, null, null, null, null, null, null);
        int id = 0;
        if (cursor.moveToLast()) {
            id = cursor.getInt(choose(cursor.getColumnIndex(JeekDBConfig.COLUMN_ID)));
        }
        cursor.close();
        db.close();
        mHelper.close();
        return id;
    }
    public List<Schedule> getScheduleByDate(int year, int month, int day) {
        List<Schedule> schedules = new ArrayList<>();
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.query(JeekDBConfig.TABLE_NAME, null,
                String.format("%s=? and %s=? and %s=?", JeekDBConfig.COLUMN_DDL_YEAR,
                        JeekDBConfig.COLUMN_DDL_MONTH, JeekDBConfig.COLUMN_DDL_DAY), new String[]{String.valueOf(year), String.valueOf(month), String.valueOf(day)}, null, null, null);
        Schedule schedule;
        while (cursor.moveToNext()) {
            schedule = new Schedule();
            schedule.setId(cursor.getInt(choose(cursor.getColumnIndex(JeekDBConfig.COLUMN_ID))));
            schedule.setName(cursor.getString(choose(cursor.getColumnIndex(JeekDBConfig.COLUMN_NAME))));
            schedule.setYear(cursor.getInt(choose(cursor.getColumnIndex(JeekDBConfig.COLUMN_DDL_YEAR))));
            schedule.setMonth(cursor.getInt(choose(cursor.getColumnIndex(JeekDBConfig.COLUMN_DDL_MONTH))));
            schedule.setDay(cursor.getInt(choose(cursor.getColumnIndex(JeekDBConfig.COLUMN_DDL_DAY))));
            schedule.setFinish(cursor.getInt(choose(cursor.getColumnIndex(JeekDBConfig.COLUMN_FINISH))));
            schedule.setDiffLevel(cursor.getInt(choose(cursor.getColumnIndex(JeekDBConfig.COLUMN_LEVEL))));
            schedule.setFinishDurationUp(cursor.getInt(choose(cursor.getColumnIndex(JeekDBConfig.COLUMN_DURATIONU))));
            schedule.setFinishDurationDown(cursor.getInt(choose(cursor.getColumnIndex(JeekDBConfig.COLUMN_DURATIOND))));
            schedules.add(schedule);
        }
        cursor.close();
        db.close();
        mHelper.close();
        return schedules;
    }

    public List<Integer> getTaskHintByMonth(int year, int month) {
        List<Integer> taskHint = new ArrayList<>();
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.query(JeekDBConfig.TABLE_NAME, new String[]{JeekDBConfig.COLUMN_DDL_DAY},
                String.format("%s=? and %s=?", JeekDBConfig.COLUMN_DDL_YEAR,
                        JeekDBConfig.COLUMN_DDL_MONTH), new String[]{String.valueOf(year), String.valueOf(month)}, null, null, null);
        while (cursor.moveToNext()) {
            taskHint.add(cursor.getInt(0));
        }
        cursor.close();
        db.close();
        mHelper.close();
        return taskHint;
    }

    public List<Integer> getTaskHintByWeek(int firstYear, int firstMonth, int firstDay, int endYear, int endMonth, int endDay) {
        List<Integer> taskHint = new ArrayList<>();
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor1 = db.query(JeekDBConfig.TABLE_NAME, new String[]{JeekDBConfig.COLUMN_DDL_DAY},
                String.format("%s=? and %s=? and %s>=?", JeekDBConfig.COLUMN_DDL_YEAR, JeekDBConfig.COLUMN_DDL_MONTH, JeekDBConfig.COLUMN_DDL_DAY),
                new String[]{String.valueOf(firstYear), String.valueOf(firstMonth), String.valueOf(firstDay)}, null, null, null);
        while (cursor1.moveToNext()) {
            taskHint.add(cursor1.getInt(0));
        }
        cursor1.close();
        Cursor cursor2 = db.query(JeekDBConfig.TABLE_NAME, new String[]{JeekDBConfig.COLUMN_DDL_DAY},
                String.format("%s=? and %s=? and %s<=?", JeekDBConfig.COLUMN_DDL_YEAR, JeekDBConfig.COLUMN_DDL_MONTH, JeekDBConfig.COLUMN_DDL_DAY),
                new String[]{String.valueOf(endYear), String.valueOf(endMonth), String.valueOf(endDay)}, null, null, null);
        while (cursor2.moveToNext()) {
            taskHint.add(cursor2.getInt(0));
        }
        cursor2.close();
        db.close();
        mHelper.close();
        return taskHint;
    }

    public boolean removeSchedule(long id) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        int row = db.delete(JeekDBConfig.TABLE_NAME, String.format("%s=?", JeekDBConfig.COLUMN_ID), new String[]{String.valueOf(id)});
        db.close();
        mHelper.close();
        return row != 0;
    }

    public boolean updateSchedule(Schedule schedule) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(JeekDBConfig.COLUMN_NAME, schedule.getName());
        values.put(JeekDBConfig.COLUMN_FINISH, schedule.getFinished());
        values.put(JeekDBConfig.COLUMN_LEVEL, schedule.getDiffLevel());
        values.put(JeekDBConfig.COLUMN_DURATIONU, schedule.getFinishDurationUp());
        values.put(JeekDBConfig.COLUMN_DURATIOND, schedule.getFinishDurationDown());
        values.put(JeekDBConfig.COLUMN_DDL_YEAR, schedule.getYear());
        values.put(JeekDBConfig.COLUMN_DDL_MONTH, schedule.getMonth());
        values.put(JeekDBConfig.COLUMN_DDL_DAY, schedule.getDay());
        int row = db.update(JeekDBConfig.TABLE_NAME, values, String.format("%s=?", JeekDBConfig.COLUMN_ID), new String[]{String.valueOf(schedule.getId())});
        db.close();
        mHelper.close();
        return row > 0;
    }

    public int choose(int a){//处理value must be >=0真的笑死我了
        if(a>=0) {
            return a;
        } else {
            return 0;
        }
    }
}
