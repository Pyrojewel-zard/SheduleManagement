package com.example.common.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author Pyrojewel
 */
public class JeekSQLiteHelper extends SQLiteOpenHelper {
    public JeekSQLiteHelper(Context context){
        super(context, JeekDBConfig.DATABASE_NAME, null, JeekDBConfig.DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(JeekDBConfig.CREATE_SCHEDULE_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL(JeekDBConfig.DROP_SCHEDULE_TABLE_SQL);
            onCreate(db);
        }
    }
}
