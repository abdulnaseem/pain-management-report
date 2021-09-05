package com.example.reportpain;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class UserDatabase extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "user.db";
    public static final String TABLE_NAME = "user_table";
    public static final String ID = "ID";
    public static final String FULLNAME = "FULLNAME";
    public static final String DOB = "DOB";
    public static final String WEIGHT = "WEIGHT";
    public static final String HEIGHT = "HEIGHT";
    public static final String BMI = "BMI";
    public static final String USERNAME = "USERNAME";
    public static final String PASSWORD = "PASSWORD";

    public static final String TABLE_NAME2 = "diary_table";
    public static final String PAIN_LOCATION = "Pain_location";
    public static final String PAIN_FEELING = "Pain_feeling";
    public static final String PAIN_LEVEL = "Pain_level";
    public static final String COMMENT = "Comment";



    public UserDatabase(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, FULLNAME TEXT," +
                " DOB TEXT, WEIGHT REAL, HEIGHT REAL, BMI REAL, USERNAME TEXT, PASSWORD TEXT )" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    public boolean insertData(User user){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(FULLNAME, user.getFullname());
        cv.put(DOB, user.getDateOfBirth());
        cv.put(WEIGHT, user.getWeight());
        cv.put(HEIGHT, user.getHeight());
        cv.put(BMI, user.getBmi());
        cv.put(USERNAME, user.getUsername());
        cv.put(PASSWORD, user.getPassword());

        long valid = db.insert(TABLE_NAME, null, cv);
        if(valid == -1){
            return false;
            //if there is a problem inserting data, return false
        }
        else {
            return true;
            //otherwise return true
        }
        /*
         * First create the database, then using content values,
         * insert the data within the table of the database.
         * Validate whether the data has been inserted by
         * utilizing 'insert' internal method where 'valid' is tested
         * if it equals to -1; failed to insert data.
         */
    }
    public boolean validateUsername(String username) {

        String[] selectColumns = {
                ID         //choose the column to return
        };
        SQLiteDatabase db = this.getReadableDatabase();

        String columnName = USERNAME + " = ?";// choose the column to compare

        String[] takeInArgs = {username};//choose the value to compare

        Cursor cursor = db.query(TABLE_NAME, //Table to query
                selectColumns, columnName, takeInArgs,
                null, null, null);

        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }
        return false;
        /*
         * If the cursorCount identifies username already exist; count is
         * greater than zero, then return true.
         * Otherwise return false.
         */
    }
    public boolean validateUser(String username, String password) {
        User user = new User();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME +
                " where USERNAME = ? AND PASSWORD = ? ", new String[]{username, password});
        Log.d("Verified", "Yes");

        if (cursor.moveToFirst()) {
            do {
                user.setID(cursor.getInt(cursor.getColumnIndex(ID)));
                String id = String.valueOf(user.getID());
                Log.d("ID", id);

                user.setFullname(cursor.getString(cursor.getColumnIndex(FULLNAME)));
                Log.d("ID", user.getFullname());

                user.setDateOfBirth(cursor.getString(cursor.getColumnIndex(DOB)));
                Log.d("ID", user.getDateOfBirth());

                user.setWeight(cursor.getString(cursor.getColumnIndex(WEIGHT)));
                Log.d("ID", user.getWeight());

                user.setHeight(cursor.getString(cursor.getColumnIndex(HEIGHT)));
                Log.d("ID", user.getHeight());

                user.setBmi(cursor.getString(cursor.getColumnIndex(BMI)));
                Log.d("ID", user.getBmi());

                user.setUsername(cursor.getString(cursor.getColumnIndex(USERNAME)));
                Log.d("ID", user.getUsername());

                user.setPassword(cursor.getString(cursor.getColumnIndex(PASSWORD)));
                Log.d("ID", user.getPassword());

            }
            while(cursor.moveToNext());
        }
            Log.d("Closed", "Yes");
            cursor.close();
            db.close();

            int cursorCount = cursor.getCount();
            if (cursorCount > 0) {
                return true;
            }

        //Log.d("Out of Loop", user.getUsername());

        return false;
        /*
         * If username and password exists; count is
         * greater than zero, then return true.
         * Otherwise return false.
         */
    }

    public void updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(FULLNAME, user.getFullname());
        cv.put(WEIGHT, user.getWeight());
        cv.put(HEIGHT, user.getHeight());
        cv.put(BMI, user.getBmi());
        // updating row
        db.update(TABLE_NAME , cv, USERNAME + " = ?",
                new String[]{String.valueOf(user.getUsername())});
        db.close();
    }

}
