package com.example.common.data;

public interface JeekDBConfig {

    int DATABASE_VERSION = 1;

    String DATABASE_NAME = "SheduleManagement";
    //暂时无法删除的内容，不需要这些东西
    String EVENT_SET_ID = "id";
    String EVENT_SET_NAME = "name";
    String EVENT_SET_COLOR = "color";
    String EVENT_SET_ICON = "icon";

    String EVENT_SET_TABLE_NAME = "EventSet";

    String CREATE_EVENT_SET_TABLE_SQL = "CREATE TABLE " + EVENT_SET_TABLE_NAME + "("
            + EVENT_SET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + EVENT_SET_NAME + " VARCHAR(32), "
            + EVENT_SET_COLOR + " INTEGER, "
            + EVENT_SET_ICON + " INTEGER" + ")";

    String DROP_EVENT_SET_TABLE_SQL = "DROP TABLE " + EVENT_SET_TABLE_NAME;
//以上
String TABLE_NAME = "SheduleManagement";
    String COLUMN_ID = "_ID";
    String COLUMN_NAME = "name";
    String COLUMN_DDL_YEAR = "YEAR";
    String COLUMN_DDL_MONTH = "MONTH";
    String COLUMN_DDL_DAY= "DAY";
    String COLUMN_FINISH = "ISFINISH";
    String COLUMN_LEVEL = "DIFLEVEL";
    String COLUMN_DURATIONU = "FINISHDURATIONUP";
    String COLUMN_DURATIOND = "FINISHDURATIONDOWN";

    String CREATE_SCHEDULE_TABLE_SQL = "CREATE TABLE " + TABLE_NAME + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_NAME + " TEXT,"
            + COLUMN_DDL_YEAR + " INTEGER, "
            + COLUMN_DDL_MONTH + " INTEGER, "
            + COLUMN_DDL_DAY + " INTEGER, "
            + COLUMN_FINISH + " NUMERIC,"
            + COLUMN_LEVEL + " INTEGER,"
            + COLUMN_DURATIONU+" INTEGER,"
            + COLUMN_DURATIOND+" INTEGER"
            + ")";

    String DROP_SCHEDULE_TABLE_SQL = "DROP TABLE " + TABLE_NAME;

}
