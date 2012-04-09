package com.androidapps.user;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class RecordsDBOpenHelper extends SQLiteOpenHelper{
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME="user";
    private static final String TABLE_CREATE =
                "CREATE TABLE exprecords (amount FLOAT, " +
                                         "date DATE, " +
                                         "category TEXT, " +
                                         "currency TEXT);";

    RecordsDBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db,int a,int b) {
        
    }
    
}
