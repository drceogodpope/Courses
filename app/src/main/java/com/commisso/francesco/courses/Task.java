package com.commisso.francesco.courses;

import org.joda.time.DateTime;
import org.joda.time.Days;

public abstract class Task {

    private String title;
    private double percentage;
    private DateTime date;

    public Task(double percentage,DateTime date){
        this.percentage = percentage;
        this.date = date;
    }

    //GETTERS
    public String getTitle() {
        return title;
    }
    public double getPercentage() {
        return percentage;
    }
    public DateTime getDate() {
        return date;
    }
    public int getDays(){
        return Days.daysBetween(new DateTime().toLocalDate(),date.toLocalDate()).getDays();

    }

    //SETTERS
    public void setDate(DateTime date) {
        this.date = date;
    }
    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }
    public void setTitle(String title) {
        this.title = title;
    }
}
