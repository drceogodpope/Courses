package com.commisso.francesco.courses;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Date;

public class AddTaskActivity extends AppCompatActivity {

    Course course;
    DBHelper dbHelper;

    Toolbar toolbar;

    FloatingActionButton fab;

    LinearLayout topLayout;
    ScrollView scrollView;
    LinearLayout inputArgumentLayout;
    RelativeLayout scrollViewRelativeLayout;

    TextView testButton;
    TextView midtermButton;
    TextView finalButton;
    TextView projectButton;
    TextView assignmentButton;
    TextView presentationButton;

    ArrayList<TextView> buttons;

    EditText taskPercent;
    EditText testLength;
    DatePicker taskDate;
    TimePicker taskTime;

    int testType = -1;
    int projectType =-1;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_task_activity);
        final Intent intent = getIntent();
        dbHelper =  DBHelper.getInstance(getApplicationContext());
        course = dbHelper.getCourse(dbHelper.getReadableDatabase(),intent.getLongExtra("id",-1));
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        topLayout = (LinearLayout) findViewById(R.id.addTaskTopLayout);
        scrollView = (ScrollView) findViewById(R.id.addTaskScrollView);
        inputArgumentLayout = (LinearLayout) findViewById(R.id.taskArgumentsLayout);
        scrollViewRelativeLayout = (RelativeLayout) findViewById(R.id.scrollViewRelativeLayout);

        fab = (FloatingActionButton) findViewById(R.id.addTaskFloatingActionButton);

        testButton = (TextView) findViewById(R.id.addTaskTestButton);
        midtermButton = (TextView) findViewById(R.id.addTaskMidtermButton);
        finalButton = (TextView) findViewById(R.id.addTaskFinalButton);
        projectButton = (TextView) findViewById(R.id.addTaskProjectButton);
        assignmentButton = (TextView) findViewById(R.id.addTaskAssignmentButton);
        presentationButton = (TextView) findViewById(R.id.addTaskPresentationButton);

        buttons = new ArrayList<>();
        buttons.add(testButton);
        buttons.add(midtermButton);
        buttons.add(finalButton);
        buttons.add(projectButton);
        buttons.add(assignmentButton);
        buttons.add(presentationButton);

        taskPercent = (EditText) findViewById(R.id.addTaskPercentage);
        testLength = (EditText) findViewById(R.id.addTestLength);
        taskDate = (DatePicker) findViewById(R.id.addTestDatePicker);
        taskTime = (TimePicker) findViewById(R.id.addTestTimePicker);

        View.OnClickListener buttonListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taskChosen(view);
            }
        };
        for(TextView t:buttons) {
            t.setOnClickListener(buttonListener);
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(testType!=-1){

                    if(checkFields()) {
                        if (testLength.getVisibility() == View.VISIBLE) {
                            course.addTask(makeTest());
                            dbHelper.insertTasks(course);
                            Intent intent = new Intent(getApplicationContext(), CourseActivity.class);
                            intent.putExtra("id", course.getId());
                            startActivity(intent);
                            overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                            onBackPressed();
                        }
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Check Percent and Length Fields!",Toast.LENGTH_SHORT).show();
                    }

                }

                if(projectType!= -1) {
                    if(checkField()){
                        course.addTask(makeProject());
                        dbHelper.insertProjects(course);
                        onBackPressed();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Check Percent Field!", Toast.LENGTH_SHORT).show();
                    }


                }




            }
        });
    }

    public void taskChosen(View v){

        switch (v.getId()){
            case R.id.addTaskTestButton:
                testType = Test.TEST;
                break;
            case R.id.addTaskMidtermButton:
                testType = Test.MIDTERM;
                break;
            case R.id.addTaskFinalButton:
                testType = Test.FINAL;
                break;
            case R.id.addTaskProjectButton:
                testLength.setVisibility(View.GONE);
                projectType = Project.PROJECT_TYPE_PROJECT;
                break;
            case R.id.addTaskAssignmentButton:
                testLength.setVisibility(View.GONE);
                projectType = Project.PROJECT_TYPE_ASSIGNMENT;
                break;
            case R.id.addTaskPresentationButton:
                testLength.setVisibility(View.GONE);
                projectType = Project.PROJECT_TYPE_PRESENTATION;
                break;
        }
       RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params.addRule(RelativeLayout.BELOW,R.id.toolbar);
        topLayout.setLayoutParams(params);

        scrollView.postDelayed(new Runnable() {
            @Override
            public void run() {
                inputArgumentLayout.setVisibility(View.VISIBLE);
                scrollViewRelativeLayout.removeView(inputArgumentLayout);
                scrollViewRelativeLayout.addView(inputArgumentLayout);
            }
        }, 200);
        for(TextView t:buttons){
            if(t!=v){
                topLayout.removeView(t);
            }
        }
    }

    public Test makeTest(){
        double percentage = Double.valueOf(taskPercent.getText().toString());
        DateTime date = DateTimeUtils.dateTimeFromPickers(taskDate,taskTime);
        double length = Double.valueOf(taskPercent.getText().toString());
        return new Test(percentage,date,testType,length);
    }

    public  Project makeProject(){
        double percentage = Double.valueOf(taskPercent.getText().toString());
        DateTime date = DateTimeUtils.dateTimeFromPickers(taskDate,taskTime);

        return new Project(percentage,date,projectType);
    }

    private boolean checkFields(){
        return DateTimeUtils.checkEditTextEmpty(taskPercent) && DateTimeUtils.checkEditTextEmpty(testLength)
                && taskPercent.getText().toString().length()<=2;
    }

    private boolean checkField(){
        return DateTimeUtils.checkEditTextEmpty(taskPercent) && taskPercent.getText().toString().length()<=2;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),CourseActivity.class);
        intent.putExtra("id",course.getId());
        startActivity(intent);
        overridePendingTransition(R.anim.pull_in_left,R.anim.push_out_right);
    }


}
