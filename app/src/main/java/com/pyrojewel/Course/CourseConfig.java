package com.pyrojewel.Course;

/**
 * @author Pyrojewel
 */
public class CourseConfig {

    public static final String TABLE_NAME = "Csvdemo";
    public static final String COLUMN_ID = "_ID";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_TEACHER = "teacher";
    public static final String COLUMN_WEEKS = "weekStart";
    public static final String WEEK_LENGTH = "weekLength";
    public static final String COLUMN_PLACE = "place";
    public static final String COLUMN_DAYS = "dayOfWeek";
    public static final String COLUMN_TIMES = "timeStart";
    public static final String TIME_LENGTH = "timeLength";
    public static final String COURSE_DIFFICULTY = "courseDifficulty";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_NAME + " TEXT,"
            + COLUMN_TEACHER + " TEXT,"
            + COLUMN_WEEKS + " INTEGER,"
            + WEEK_LENGTH + " INTEGER,"
            + COLUMN_PLACE + " TEXT,"
            + COLUMN_DAYS + " INTEGER,"
            + COLUMN_TIMES + " INTEGER,"
            + COURSE_DIFFICULTY + "INTEGER,"
            + TIME_LENGTH + " INTEGER"
            + ")";
}
