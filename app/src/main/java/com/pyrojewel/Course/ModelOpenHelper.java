package com.pyrojewel.Course;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * @author Pyrojewel
 */
public class ModelOpenHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "CsvDemo";
    private static final int DB_VERSION = 1;
    private Context mContext;

    public ModelOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        mContext=context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CourseConfig.CREATE_TABLE);
        //之后可以在这里新建那个课程表的数据集
        Toast.makeText(mContext,"Create succeeded", Toast.LENGTH_SHORT).show();

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("生活小助手","--版本更新"+oldVersion+"-->"+newVersion);

    }
    /**
     *
     * @param courseModel
     * @return 返回新插入的行的ID，发生错误，插入不成功，则返回-1
     * 我思考是不是插入新的避免重复是在这里加一个判断？！
     * 对将每个新的都作为查询条件抖进去查询
     */
    public long insertCourseModel(CourseModel courseModel) {

        if(!searchExist(courseModel.getName(),courseModel.getTeacher(),courseModel.getDayOfWeek())){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(CourseConfig.COLUMN_NAME, courseModel.getName());
            values.put(CourseConfig.COLUMN_TEACHER, courseModel.getTeacher());
            values.put(CourseConfig.COLUMN_WEEKS, courseModel.getWeekString());
            values.put(CourseConfig.COLUMN_PLACE, courseModel.getPlace());
            values.put(CourseConfig.COLUMN_DAYS, courseModel.getDayOfWeek());
            values.put(CourseConfig.COLUMN_TIMES, courseModel.getTimeStart());
            values.put(CourseConfig.COLUMN_TIMELength, courseModel.getTimeLength());

            long id = db.insert(CourseConfig.TABLE_NAME, null, values);
            db.close();
            return id;
        }
        return -1;
    }
    public boolean searchExist(String searchName,String searchTeacher,int searchDayOfWeek){
        CourseModel courseModel = new CourseModel();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columnArray = new String[]{
                CourseConfig.COLUMN_ID,
                CourseConfig.COLUMN_NAME,
                CourseConfig.COLUMN_TEACHER,
                CourseConfig.COLUMN_WEEKS,
                CourseConfig.COLUMN_PLACE,
                CourseConfig.COLUMN_DAYS,
                CourseConfig.COLUMN_TIMES,
                CourseConfig.COLUMN_TIMELength
        };
        Cursor cursor = db.query(CourseConfig.TABLE_NAME,
                columnArray,
                CourseConfig.COLUMN_NAME + "=?"+" AND "+CourseConfig.COLUMN_TEACHER+"=?"+" AND "+CourseConfig.COLUMN_DAYS+"=? ",
                new String[]{searchName,searchTeacher,Integer.toString(searchDayOfWeek)},
                null, null, null);
        if (cursor != null && cursor.moveToNext()) {
            return true;
            //表示存在
        }
        return false;
    }

    /**
     *
     * @param searchName query database by name
     * @return CourseModel
     */
    public CourseModel getCourseModelQueryByName(String searchName) {
        CourseModel courseModel = new CourseModel();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columnArray = new String[]{
                CourseConfig.COLUMN_ID,
                CourseConfig.COLUMN_NAME,
                CourseConfig.COLUMN_TEACHER,
                CourseConfig.COLUMN_WEEKS,
                CourseConfig.COLUMN_PLACE,
                CourseConfig.COLUMN_DAYS,
                CourseConfig.COLUMN_TIMES,
                CourseConfig.COLUMN_TIMELength
        };
        Cursor cursor = db.query(CourseConfig.TABLE_NAME,
                columnArray,
                CourseConfig.COLUMN_NAME + "=? ",
                new String[]{searchName},
                null, null, null);
        if (cursor != null && cursor.moveToNext()) {
            int id = cursor.getInt(choose(cursor.getColumnIndex(CourseConfig.COLUMN_ID)));
            String name = cursor.getString(choose(cursor.getColumnIndex(CourseConfig.COLUMN_NAME)));
            String teacher=cursor.getString(choose(cursor.getColumnIndex(CourseConfig.COLUMN_TEACHER)));
            String weekString=cursor.getString(choose(cursor.getColumnIndex(CourseConfig.COLUMN_WEEKS)));
            String place=cursor.getString(choose(cursor.getColumnIndex(CourseConfig.COLUMN_PLACE)));
            int dayOfWeek=cursor.getInt(choose(cursor.getColumnIndex(CourseConfig.COLUMN_DAYS)));
            int timeStart=cursor.getInt(choose(cursor.getColumnIndex(CourseConfig.COLUMN_TIMES)));
            int timeLength=cursor.getInt(choose(cursor.getColumnIndex(CourseConfig.COLUMN_TIMELength)));

            courseModel.setId(id);
            courseModel.setName(name);
            courseModel.setTeacher(teacher);
            courseModel.setWeekString(weekString);
            courseModel.setPlace(place);
            courseModel.setDayOfWeek(dayOfWeek);
            courseModel.setTimeStart(timeStart);
            courseModel.setTimeLength(timeLength);

            cursor.close();
            return courseModel;
        }
        return null;
    }
    public int choose(int a){//处理value must be >=0真的emmmm
        if(a>=0) {
            return a;
        } else {
            return 0;
        }
    }


    /**
     *
     * @return 读取数据库，返回一个 CourseConfig 类型的 ArrayList
     */
    public ArrayList<CourseModel> getAllCourseConModels() {
        ArrayList<CourseModel> courseModelsList = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + CourseConfig.TABLE_NAME
                + " ORDER BY " + CourseConfig.COLUMN_ID + " ASC";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                CourseModel courseModel = new CourseModel();
                int id = cursor.getInt(choose(cursor.getColumnIndex(CourseConfig.COLUMN_ID)));
                String name = cursor.getString(choose(cursor.getColumnIndex(CourseConfig.COLUMN_NAME)));
                String teacher=cursor.getString(choose(cursor.getColumnIndex(CourseConfig.COLUMN_TEACHER)));
                String weekString=cursor.getString(choose(cursor.getColumnIndex(CourseConfig.COLUMN_WEEKS)));
                String place=cursor.getString(choose(cursor.getColumnIndex(CourseConfig.COLUMN_PLACE)));
                int dayOfWeek=cursor.getInt(choose(cursor.getColumnIndex(CourseConfig.COLUMN_DAYS)));
                int timeStart=cursor.getInt(choose(cursor.getColumnIndex(CourseConfig.COLUMN_TIMES)));
                int timeLength=cursor.getInt(choose(cursor.getColumnIndex(CourseConfig.COLUMN_TIMELength)));

                courseModel.setId(id);
                courseModel.setName(name);
                courseModel.setTeacher(teacher);
                courseModel.setWeekString(weekString);
                courseModel.setPlace(place);
                courseModel.setDayOfWeek(dayOfWeek);
                courseModel.setTimeStart(timeStart);
                courseModel.setTimeLength(timeLength);

                courseModelsList.add(courseModel);
            }
        }

        db.close();
        return courseModelsList;
    }

    /**
     *
     * @return 返回数据库行数
     */
    public int getCourseModelCount() {
        String countQuery = "SELECT * FROM " + CourseConfig.TABLE_NAME;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }
    /**
     *
     * @param id update row id （需要更新的ID）
     * @param courseModel update value （去更新数据库的内容）
     * @return the number of rows affected (影响到的行数，如果没更新成功，返回0。所以当return 0时，需要告诉用户更新不成功)
     */
    public int updateCourseModel(int id, CourseModel courseModel) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CourseConfig.COLUMN_NAME, courseModel.getName());
        values.put(CourseConfig.COLUMN_TEACHER, courseModel.getTeacher());
        values.put(CourseConfig.COLUMN_WEEKS, courseModel.getWeekString());
        values.put(CourseConfig.COLUMN_PLACE, courseModel.getPlace());
        values.put(CourseConfig.COLUMN_DAYS, courseModel.getDayOfWeek());
        values.put(CourseConfig.COLUMN_TIMES, courseModel.getTimeStart());
        values.put(CourseConfig.COLUMN_TIMELength, courseModel.getTimeLength());

        int idReturnByUpdate = db.update(CourseConfig.TABLE_NAME, values, CourseConfig.COLUMN_ID + " =? ", new String[]{String.valueOf(id)});
        db.close();
        return idReturnByUpdate;
    }


    public int deleteCourseModel(int id) {
        SQLiteDatabase db = getWritableDatabase();
        int idReturnByDelete = db.delete(CourseConfig.TABLE_NAME, CourseConfig.COLUMN_ID + "=? ", new String[]{String.valueOf(id)});
        db.close();
        return idReturnByDelete;
    }

    /**
     * 删除所有行，whereClause 传入 String "1"
     * @return 返回删除掉的行数总数（比如：删除了四行就返回4）
     */
    public int deleteAllCourseModel() {
        SQLiteDatabase db = getWritableDatabase();
        int idReturnByDelete = db.delete(CourseConfig.TABLE_NAME, String.valueOf(1), null);
        db.close();
        return idReturnByDelete;
    }
}

