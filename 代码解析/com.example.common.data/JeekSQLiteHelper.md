```java
package com.example.common.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class exampleSQLiteHelper extends SQLiteOpenHelper {
    public exampleSQLiteHelper(Context context){
        super(context, exampleDBConfig.DATABASE_NAME, null, exampleDBConfig.DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(exampleDBConfig.CREATE_EVENT_SET_TABLE_SQL);
        db.execSQL(exampleDBConfig.CREATE_SCHEDULE_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL(exampleDBConfig.DROP_EVENT_SET_TABLE_SQL);
            db.execSQL(exampleDBConfig.DROP_SCHEDULE_TABLE_SQL);
            onCreate(db);
        }
    }
}
```