package com.pyrojewel.Model;

/*构造咱要储存的数据的结构，反正体现的大概是重构的思想*/
public class DatabaseModel {
        public static final String TABLE_NAME = "SheduleManagement";
        public static final String COLUMN_ID = "_ID";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_DDL = "DDLDATE";
        public static final String COLUMN_FINISH = "ISFINISH";
        public static final String COLUMN_LEVEL = "DIFLEVEL";
        public static final String COLUMN_DURATIONU = "FINISHDURATIONUP";
        public static final String COLUMN_DURATIOND = "FINISHDURATIONDOWN";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_DDL + " TEXT,"
                + COLUMN_FINISH + " NUMERIC,"
                + COLUMN_LEVEL + " INTEGER,"
                + COLUMN_DURATIONU+" INTEGER,"
                + COLUMN_DURATIOND+" INTEGER"
            + ")";

        private int id;
        private String name;
        private String ddlDate;
        //true:finished, false:unfinished
        private boolean isfinished;
        private int diffLevel;
        private int finishDurationUp;//上限完成预期时间
        private int finishDurationDown;//下限
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getDDLDate() {
            return ddlDate;
        }

        public void setDDLDate(String ddlDate) {
            this.ddlDate = ddlDate;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean getFinished() {
            return isfinished;
        }

        public void setFinish(boolean isfinished) {
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
        public int getFinishDurationDown(){
            return finishDurationDown;
        }

        public void setFinishDurationDown(int finishDurationDown) {
        this.finishDurationDown = finishDurationDown;
    }
    }


