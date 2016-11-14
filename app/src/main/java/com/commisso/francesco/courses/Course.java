package com.commisso.francesco.courses;

import android.support.v4.content.ContextCompat;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.Serializable;
import java.util.ArrayList;

// A CLASS TO HOLD TO HOLD COURSE INFORMATION

public class Course{

    private long id;
    private String title;
    private String courseCode;
    private DateTime startDate;
    private DateTime endDate;
    private LocalTime time;
    private String teacher;
    private String textbook;
    private ArrayList<Task> tasks = new ArrayList<>();

    //FIRST CONSTRUCTOR
    //COURSE OBJECT GETS CREATED AND THEN ID GETS SET WHEN ADDED TO DB
    public Course(String title, String courseCode, DateTime startDate, DateTime endDate, LocalTime time){
        this.title = title;
        this.courseCode = courseCode;
        this.startDate = startDate;
        this.endDate = endDate;
        this.time = time;
    }

    //USED TO RECREATE COURSES FROM DB
    public Course(String title, String courseCode, DateTime startDate, DateTime endDate, LocalTime time,long id){
        this.title = title;
        this.courseCode = courseCode;
        this.startDate = startDate;
        this.endDate = endDate;
        this.time = time;
        this.id = id;
    }

    //
    public Course(String teacher, String title, String courseCode,DateTime startDate, DateTime endDate,LocalTime time){
        this.startDate = startDate;
        this.endDate = endDate;
        this.time = time;
        this.teacher = teacher;
        this.title = title;
        this.courseCode = courseCode;
    }


    //  SORTS ARRAYLIST OF COURSES IN ORDER OF SOONEST TASK

//    public static ArrayList<Course> sortCourses(ArrayList<Course> courses){
//
//    }



    //GETTERS
    public long getId(){return this.id;}
    public String getTeacher(){
        return teacher;
    }
    public String getTitle(){
        return title;
    }
    public String getCourseCode(){
        return courseCode;
    }
    public String getTextbook(){
        return this.textbook;
    }
    public String getTimeHHMMSS(){
        DateTimeFormatter fmt = DateTimeFormat.forPattern("HH:mm:ss");
        return time.toString(fmt) ;
    }
    public String getDay(){
        DateTimeFormatter fmt = DateTimeFormat.forPattern("EEE");
        return startDate.toString(fmt);
    }
    public String getTime(){
        DateTimeFormatter fmt = DateTimeFormat.forPattern("h:mm");
        return time.toString(fmt);
    }
    public long getEndDateMillis() {
        return endDate.getMillis();
    }
    public DateTime getEndDate(){
        return endDate;
    }
    public long getStartDateMillis() {
        return startDate.getMillis();
    }
    public DateTime getStartDate(){return startDate;}


    public static LocalTime makeLocalTime(String time){
        DateTimeFormatter fmt = DateTimeFormat.forPattern("HH:mm:ss");
        return fmt.parseLocalTime(time);
    }

    public Task getTask(int position){
        return tasks.get(position);
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }


    //SETTERS
    public void setTextbook(String textbook){this.textbook = textbook;}
    public void setTeacher(String teacher){
            this.teacher  = teacher;
    }
    public void setId(long id){
        this.id = id;
    }
    public void setTasks(ArrayList<Task> tasks){this.tasks = tasks;}


    public void addTask(Task t){
        this.tasks.add(t);
    }


    //STATIC

    public static int decideTextColour(Course c){

        int redThreshold = 7;

        if(DateTimeUtils.daysRemaining(c.getEndDate())<=redThreshold){
            return ContextCompat.getColor(Courses.getAppContext(),R.color.colorLavender);
        }
            return ContextCompat.getColor(Courses.getAppContext(),R.color.colorLightSteelBlue);

    }


    @Override
    public String toString() {
        return (
                "\n"+
                "Teacher: " + teacher +"\n" +
                "Title: " + title +"\n" +
                "Course Code: " + courseCode +"\n" +
                "Start Date: " + startDate +"\n" +
                "End Date: " + endDate +"\n" +
                "Number of Days: " + DateTimeUtils.daysRemaining(endDate) +"\n" +
                "Time: " + getTimeHHMMSS() +"\n" +
                "Course ID: " + getId());
    }



}
