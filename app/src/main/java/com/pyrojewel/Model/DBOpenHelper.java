package com.pyrojewel.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class DBOpenHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "SheduleManagement";
    private static final int DB_VERSION = 1;
    private final Context mContext;

    public DBOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        mContext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DatabaseModel.CREATE_TABLE);
        //之后可以在这里新建那个课程表的数据集
        Toast.makeText(mContext,"Create succeeded", Toast.LENGTH_SHORT).show();

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("生活小助手","--版本更新"+oldVersion+"-->"+newVersion);
//    if (oldVersion < 2) {
//      upgradeVersion2(sqLiteDatabase);
// }
    }//暂时应该用不上这个函数
//    private void upgradeVersion2(SQLiteDatabase db) {
//        db.execSQL("ALTER TABLE " + DatabaseModel.TABLE_NAME + " ADD COLUMN " + DatabaseModel.COLUMN_COMMENT + " TEXT");
//    }
    /**
     *
     * @param detailModel
     * @return 返回新插入的行的ID，发生错误，插入不成功，则返回-1
     */
    public long insertDatabaseModel(DatabaseModel detailModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseModel.COLUMN_NAME, detailModel.getName());
        values.put(DatabaseModel.COLUMN_DDL, detailModel.getDDLDate());
        values.put(DatabaseModel.COLUMN_FINISH, detailModel.getFinished() ? 1 : 0);
        values.put(DatabaseModel.COLUMN_LEVEL, detailModel.getDiffLevel());
        values.put(DatabaseModel.COLUMN_DURATIONU, detailModel.getFinishDurationUp());
        values.put(DatabaseModel.COLUMN_DURATIOND, detailModel.getFinishDurationDown());
        long id = db.insert(DatabaseModel.TABLE_NAME, null, values);
        db.close();
        return id;
    }
    /**
     *
     * @param searchName query database by name
     * @return DatabaseModel
     */
    public DatabaseModel getDatabaseModelQueryByName(String searchName) {
        DatabaseModel detailModel = new DatabaseModel();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columnArray = new String[]{
                DatabaseModel.COLUMN_ID,
                DatabaseModel.COLUMN_NAME,
                DatabaseModel.COLUMN_DDL,
                DatabaseModel.COLUMN_FINISH,
                DatabaseModel.COLUMN_LEVEL,
                DatabaseModel.COLUMN_DURATIONU,
                DatabaseModel.COLUMN_DURATIOND};
        Cursor cursor = db.query(DatabaseModel.TABLE_NAME,
                columnArray,
                DatabaseModel.COLUMN_NAME + "=? ",
                new String[]{searchName},
                null, null, null);
        if (cursor != null && cursor.moveToNext()) {
            int id = cursor.getInt(choose(cursor.getColumnIndex(DatabaseModel.COLUMN_ID)));
            String name = cursor.getString(choose(cursor.getColumnIndex(DatabaseModel.COLUMN_NAME)));
            String ddlDate = cursor.getString(choose(cursor.getColumnIndex(DatabaseModel.COLUMN_DDL)));
            int isfinished = cursor.getInt(choose(cursor.getColumnIndex(DatabaseModel.COLUMN_FINISH)));
            int diffLevel = cursor.getInt(choose(cursor.getColumnIndex(DatabaseModel.COLUMN_LEVEL)));
            int finishDurationUp = cursor.getInt(choose(cursor.getColumnIndex(DatabaseModel.COLUMN_DURATIONU)));
            int finishDurationDown=cursor.getInt(choose(cursor.getColumnIndex(DatabaseModel.COLUMN_DURATIOND)));
            detailModel.setId(id);
            detailModel.setName(name);
            detailModel.setDDLDate(ddlDate);
            detailModel.setFinish(isfinished==1);
            detailModel.setDiffLevel(diffLevel);
            detailModel.setFinishDurationUp(finishDurationUp);
            detailModel.setFinishDurationDown(finishDurationDown);
            cursor.close();
            return detailModel;
        }
        return null;
    }
    public int choose(int a){//处理value must be >=0真的emmmm
        if(a>=0)return a;
        else return 0;
    }


    /**
     *
     * @return 读取数据库，返回一个 DatabaseModel 类型的 ArrayList
     */
    public ArrayList<DatabaseModel> getAllDatabaseModels() {
        ArrayList<DatabaseModel> detailModelsList = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + DatabaseModel.TABLE_NAME
                + " ORDER BY " + DatabaseModel.COLUMN_ID + " ASC";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                DatabaseModel detailModel = new DatabaseModel();
                int id = cursor.getInt(choose(cursor.getColumnIndex(DatabaseModel.COLUMN_ID)));
                String name = cursor.getString(choose(cursor.getColumnIndex(DatabaseModel.COLUMN_NAME)));
                String ddlDate = cursor.getString(choose(cursor.getColumnIndex(DatabaseModel.COLUMN_DDL)));
                int isfinished = cursor.getInt(choose(cursor.getColumnIndex(DatabaseModel.COLUMN_FINISH)));
                int diffLevel = cursor.getInt(choose(cursor.getColumnIndex(DatabaseModel.COLUMN_LEVEL)));
                int finishDurationUp = cursor.getInt(choose(cursor.getColumnIndex(DatabaseModel.COLUMN_DURATIONU)));
                int finishDurationDown=cursor.getInt(choose(cursor.getColumnIndex(DatabaseModel.COLUMN_DURATIOND)));
                detailModel.setId(id);
                detailModel.setName(name);
                detailModel.setDDLDate(ddlDate);
                detailModel.setFinish(isfinished==1);
                detailModel.setDiffLevel(diffLevel);
                detailModel.setFinishDurationUp(finishDurationUp);
                detailModel.setFinishDurationDown(finishDurationDown);

                detailModelsList.add(detailModel);
            }
        }

        db.close();
        return detailModelsList;
    }

    /**
     *
     * @return 返回数据库行数
     */
    public int getDatabaseModelCount() {
        String countQuery = "SELECT * FROM " + DatabaseModel.TABLE_NAME;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }
    /**
     *
     * @param id update row id （需要更新的ID）
     * @param detailModel update value （去更新数据库的内容）
     * @return the number of rows affected (影响到的行数，如果没更新成功，返回0。所以当return 0时，需要告诉用户更新不成功)
     */
    public int updateDatabaseModel(int id, DatabaseModel detailModel) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseModel.COLUMN_NAME, detailModel.getName());
        values.put(DatabaseModel.COLUMN_DDL, detailModel.getDDLDate());
        values.put(DatabaseModel.COLUMN_FINISH, detailModel.getFinished()?1:0);
        values.put(DatabaseModel.COLUMN_LEVEL, detailModel.getDiffLevel());
        values.put(DatabaseModel.COLUMN_DURATIONU, detailModel.getFinishDurationUp());
        values.put(DatabaseModel.COLUMN_DURATIOND, detailModel.getFinishDurationDown());

        int idReturnByUpdate = db.update(DatabaseModel.TABLE_NAME, values, DatabaseModel.COLUMN_ID + " =? ", new String[]{String.valueOf(id)});
        db.close();
        return idReturnByUpdate;
    }


    public int deleteDatabaseModel(int id) {
        SQLiteDatabase db = getWritableDatabase();
        int idReturnByDelete = db.delete(DatabaseModel.TABLE_NAME, DatabaseModel.COLUMN_ID + "=? ", new String[]{String.valueOf(id)});
        db.close();
        return idReturnByDelete;
    }

    /**
     * 删除所有行，whereClause 传入 String "1"
     * @return 返回删除掉的行数总数（比如：删除了四行就返回4）
     */
    public int deleteAllDatabaseModel() {
        SQLiteDatabase db = getWritableDatabase();
        int idReturnByDelete = db.delete(DatabaseModel.TABLE_NAME, String.valueOf(1), null);
        db.close();
        return idReturnByDelete;
    }
}
