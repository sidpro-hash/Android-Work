package com.example.madpractical;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class StudentDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "student_db";
    public static final int DATABASE_VERSION = 1;


    // create table query
    public static final String CREATE_TABLE = "create table " + Studentinfo.TABLE_NAME + "(" +
            Studentinfo.CONTACT + " text," +
            Studentinfo.NAME + " text," +
            Studentinfo.EMAIL + " text," +
            Studentinfo.CITY + " text)";

    public static final String DROP_TABLE = "drop table if exists " + Studentinfo.TABLE_NAME;

    public StudentDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d("Database Operation", "Database Created...");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
        Log.d("Database Operation", "Table Created...");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    // insert data into table
    public void addInfo(String id, String name, String email, String city, SQLiteDatabase db) {
        // to store key value pair
        ContentValues contentValues = new ContentValues();
        // column name => value
        contentValues.put(Studentinfo.CONTACT, id);
        contentValues.put(Studentinfo.NAME, name);
        contentValues.put(Studentinfo.EMAIL, email);
        contentValues.put(Studentinfo.CITY, city);

        db.insert(Studentinfo.TABLE_NAME, null, contentValues);
        Log.d("Database Operation", "row(s) inserted...");
    }

    // read from table
    public Cursor readInfo(SQLiteDatabase db) {
        String[] projections = {Studentinfo.CONTACT, Studentinfo.NAME, Studentinfo.EMAIL, Studentinfo.CITY};
        Cursor cursor = db.query(Studentinfo.TABLE_NAME, projections, null, null, null, null, null);
        return cursor;
    }

    // delete
    public void deleteInfo(String id, SQLiteDatabase db) {
        String selection = Studentinfo.CONTACT + " = " + id;
        db.delete(Studentinfo.TABLE_NAME, selection, null);
        Log.d("Database Operation", "row(s) deleted...");
    }

}
