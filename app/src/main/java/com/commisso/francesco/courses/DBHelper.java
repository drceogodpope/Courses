package com.commisso.francesco.courses;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;

import java.util.ArrayList;


public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "DataBase.db";

    public static final String COURSES_TABLE_NAME = "courses";
    public static final String COURSES_COLUMN_ID = "_id";
    public static final String COURSES_COLUMN_NAME = "courseName";
    public static final String COURSES_COLUMN_COURSE_CODE = "courseCode";
    public static final String COURSES_COLUMN_START_DATE = "startDate";
    public static final String COURSES_COLUMN_END_DATE = "endDate";
    public static final String COURSES_COLUMN_TIME = "time";

    public static final String RESOURCES_TABLE_NAME = "resources";
    public static final String RESOURCES_COLUMN_ID = "_id";
    public static final String RESOURCES_COLUMN_TITLE = "title";

    public static final String TESTS_TABLE_NAME = "tests";
    public static final String TESTS_COLUMN_ID = "id";
    public static final String TESTS_COLUMN_PERCENTAGE = "percentage";
    public static final String TESTS_COLUMN_DATE = "date";
    public static final String TESTS_COLUMN_TITLE = "title";

    private static DBHelper sInstance;

    public static synchronized DBHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DBHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    private DBHelper(Context context){
        super(context, DATABASE_NAME , null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table " + COURSES_TABLE_NAME + "(" + COURSES_COLUMN_ID +" INTEGER PRIMARY KEY, " + COURSES_COLUMN_NAME + " TEXT, "+ COURSES_COLUMN_COURSE_CODE +" TEXT, "+ COURSES_COLUMN_START_DATE +" INTEGER, " + COURSES_COLUMN_END_DATE + " INTEGER, "+ COURSES_COLUMN_TIME+" TEXT)"
        );

        db.execSQL(
                "create table " + RESOURCES_TABLE_NAME + "(" + RESOURCES_COLUMN_ID +" INTEGER, " + RESOURCES_COLUMN_TITLE + " TEXT)"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + COURSES_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ RESOURCES_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ TESTS_TABLE_NAME);
        onCreate(db);
    }

    public boolean insertTextbook(Course course){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(RESOURCES_COLUMN_ID,course.getId());
        contentValues.put(RESOURCES_COLUMN_TITLE,course.getTextbook());
        db.insert(RESOURCES_TABLE_NAME,null,contentValues);
        return true;
    }

    public boolean insertCourse  (Course course){
        // inserts course object into Courses table and sets course id
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COURSES_COLUMN_NAME, course.getTitle());
        contentValues.put(COURSES_COLUMN_COURSE_CODE, course.getCourseCode());
        contentValues.put(COURSES_COLUMN_START_DATE, course.getStartDateMillis());
        contentValues.put(COURSES_COLUMN_END_DATE, course.getEndDateMillis());
        contentValues.put(COURSES_COLUMN_TIME, course.getTimeHHMMSS());
        //sets course id
        course.setId(db.insert(COURSES_TABLE_NAME, null, contentValues));
        return true;
    }


    public boolean updateValueString(SQLiteDatabase db,String tableName, long id,String column, String value){
        //p3 = ID OF ROW YOU WANT TO CHANGE, p4 = NAME OF COLUMN YOU WANT TO ENTER,
        ContentValues newValues = new ContentValues();
        newValues.put(column,value);
        db.update(tableName,newValues, DBHelper.COURSES_COLUMN_ID+"="+ id,null);
        return true;
    }

    public boolean updateValueInt(SQLiteDatabase db,String tableName, long id,String column, int value){
        ContentValues newValues = new ContentValues();
        newValues.put(column,value);
        db.update(tableName,newValues, DBHelper.COURSES_COLUMN_ID+"="+ id,null);
        return true;
    }

    public boolean updateValueLong(SQLiteDatabase db,String tableName, long id,String column, long value){
        ContentValues newValues = new ContentValues();
        newValues.put(column,value);
        db.update(tableName,newValues, DBHelper.COURSES_COLUMN_ID+"="+ id,null);
        return true;
    }


    public ArrayList<Course> getCourses(SQLiteDatabase db,ArrayList<Course> courses){

        Cursor c = db.query(COURSES_TABLE_NAME,null," 1",null,null,null,null);  // "SELECT * FROM " + COURSES_TABLE_NAME + "
                                                                                // WHERE 1"
        courses.clear();
        if (c.moveToFirst()) {
            do {
                String title = c.getString(c.getColumnIndex(COURSES_COLUMN_NAME));
                String courseCode = c.getString(c.getColumnIndex(COURSES_COLUMN_COURSE_CODE));
                DateTime startDate = new DateTime(c.getLong(c.getColumnIndex(COURSES_COLUMN_START_DATE)));
                DateTime endDate = new DateTime(c.getLong(c.getColumnIndex(COURSES_COLUMN_END_DATE)));
                LocalTime time = new LocalTime(c.getString(c.getColumnIndex(COURSES_COLUMN_TIME)));
                long id = c.getLong(c.getColumnIndex(COURSES_COLUMN_ID));
                courses.add(new Course(title,courseCode,startDate,endDate,time,id));

            } while (c.moveToNext());
        }
        c.close();
        return  courses;
    }

    public String getTableAsString(SQLiteDatabase db, String tableName) {
        String tableString = String.format("Table %s:\n", tableName);
        Cursor allRows  = db.rawQuery("SELECT * FROM " + tableName, null);
        if (allRows.moveToFirst() ){
            String[] columnNames = allRows.getColumnNames();
            do {
                for (String name: columnNames) {
                    tableString += String.format("%s: %s\n", name,
                            allRows.getString(allRows.getColumnIndex(name)));
                }
                tableString += "\n";

            } while (allRows.moveToNext());
        }
        allRows.close();
        return tableString;
    }


}



