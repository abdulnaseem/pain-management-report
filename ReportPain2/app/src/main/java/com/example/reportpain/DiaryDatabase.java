package com.example.reportpain;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DiaryDatabase extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "diary.db";
    public static final String TABLE_NAME = "diary_table";
    public static final String ID = "ID";
    public static final String DATE = "Date";
    public static final String TIME = "Time";
    public static final String PAIN_LOCATION = "pain_location";
    public static final String PAIN_FEELING = "pain_feeling";
    public static final String PAIN_LEVEL = "pain_level";
    public static final String COMMENT = "comment";

    private Context context;
    public DiaryDatabase(Context context) {
        super(context, DATABASE_NAME, null, 3);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " DATE TEXT, TIME TEXT, PAIN_LOCATION TEXT, PAIN_FEELING TEXT, PAIN_LEVEL INTEGER, COMMENT TEXT)" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String date, String time, String location, String feeling, int level, String comment){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv  = new ContentValues();

        cv.put(DATE, date);
        cv.put(TIME, time);
        cv.put(PAIN_LOCATION, location);
        cv.put(PAIN_FEELING, feeling);
        cv.put(PAIN_LEVEL, level);
        cv.put(COMMENT, comment);

        long valid = db.insert(TABLE_NAME, null, cv);
        if(valid == -1){
            return false;
            //if there is a problem inserting data, return false
        }
        else {
            return true;
            //otherwise return true
        }

    }
    public Cursor readData(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db!=null){
            cursor = db.rawQuery("select * from " + TABLE_NAME, null);
        }
        return cursor;
    }

    public void updateData(String id, String date, String time, String location,
                           String feeling, int level, String comment){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(DATE, date);
        cv.put(TIME, time);
        cv.put(PAIN_LOCATION, location);
        cv.put(PAIN_FEELING, feeling);
        cv.put(PAIN_LEVEL, level);
        cv.put(COMMENT, comment);

        // updating row
        db.update(TABLE_NAME , cv, ID + " = ?",
                new String[]{(id)});
        db.close();
    }

    public void deleteRow(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, ID + " = ?", new String[]{(id)});
        if(result == -1){
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
            //if there is a problem inserting data, return false
        }
        else {
            Toast.makeText(context, "Deleted.", Toast.LENGTH_SHORT).show();
            //otherwise return true
        }
    }


}
