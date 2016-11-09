package com.commisso.francesco.courses;

import org.joda.time.DateTime;

public class Test extends Task{

    public static final int TEST = 0;
    public static final int MIDTERM =1;
    public static final int FINAL = 2;

    private static String TEST_TITLE= "Test";
    private static String MIDTERM_TITLE= "Midterm";
    private static String FINAL_TITLE= "Final";

    private double length;
    private String topic = "";
    private int testType;

    public Test(double percentage, DateTime date,int testType,double length) {
        super(percentage, date);
        this.testType = testType;
        this.length = length;
        this.topic = topic;
        this.testType = testType;
        setTestTitle(testType);
    }

    public Test(double percentage, DateTime date,int testType,double length, String topic) {
        super(percentage, date);
        this.testType = testType;
        this.length = length;
        this.topic = topic;
        this.testType = testType;
        setTestTitle(testType);
    }

    private void setTestTitle(int testType){
        switch (testType){
            case TEST: this.setTitle(TEST_TITLE);
                break;
            case MIDTERM: this.setTitle(MIDTERM_TITLE);
                break;
            case FINAL: this.setTitle(FINAL_TITLE);
        }
    }

    //GETTERS
    public double getLength() {
        return length;
    }
    public String getTopic() {
        return topic;
    }
    public int getTestType() {
        return testType;
    }

    //SETTERS
    public void setLength(double length) {
        this.length = length;
    }
    public void setTopic(String topic) {
        this.topic = topic;
    }


}
