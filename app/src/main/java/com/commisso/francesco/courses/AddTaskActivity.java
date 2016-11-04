package com.commisso.francesco.courses;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TimePicker;
import android.widget.Toast;

public class AddTaskActivity extends AppCompatActivity {

    LinearLayout topLayout;
    ScrollView scrollView;
    LinearLayout testLinearLayout;
    LinearLayout projectLinearLayout;

    //Buttons
    CardView testButton;
    CardView midtermButton;
    CardView finalButton;
    CardView projectButton;
    CardView presentationButton;
    CardView assignmentButton;

    //Test
    EditText enterTestPercentage;
    EditText enterTestLength;
    DatePicker enterTestDate;
    TimePicker enterTestTime;

    //Project
    EditText enterProjectPercentage;

    FloatingActionButton fab;
    DateTimeUtils dateUtils;

    private boolean testChosen;
    private int testType;
    Course course;

    DBHelper dbHelper;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_task_activity);

        dbHelper =  DBHelper.getInstance(getApplicationContext());
        dateUtils = new DateTimeUtils();


        int id = getIntent().getIntExtra("id",-1);

        course = dbHelper.getCourse(dbHelper.getReadableDatabase(),id);

        topLayout = (LinearLayout) findViewById(R.id.addTaskLinearLayout);
        scrollView = (ScrollView) findViewById(R.id.addTaskScrollView);
        testLinearLayout = (LinearLayout) findViewById(R.id.addTestLinearLayout);
        projectLinearLayout = (LinearLayout) findViewById(R.id.addProjectLinearLayout);
        topLayout.setGravity(Gravity.CENTER);

        testButton = (CardView) findViewById(R.id.addTaskTestButton);
        midtermButton = (CardView) findViewById(R.id.addTaskMidtermButton);
        finalButton = (CardView) findViewById(R.id.addTaskFinalButton);
        projectButton = (CardView) findViewById(R.id.addTaskProjectButton);
        presentationButton = (CardView) findViewById(R.id.addTaskPresentationButton);
        assignmentButton = (CardView) findViewById(R.id.addTaskAssignmentButton);

        enterTestPercentage = (EditText) findViewById(R.id.addTestPercentage);
        enterTestLength = (EditText) findViewById(R.id.addTestLength);
        enterTestDate = (DatePicker) findViewById(R.id.addTestDatePicker);
        enterTestTime = (TimePicker) findViewById(R.id.addTestTimePicker);

        enterProjectPercentage = (EditText) findViewById(R.id.addProjectPercentage);

        fab = (FloatingActionButton) findViewById(R.id.addTaskFloatingActionButton);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taskChosen(view);
            }
        };

        testButton.setOnClickListener(listener);
        midtermButton.setOnClickListener(listener);
        finalButton.setOnClickListener(listener);
        projectButton.setOnClickListener(listener);
        assignmentButton.setOnClickListener(listener);
        presentationButton.setOnClickListener(listener);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkFields()){
                    course.addTask(makeTask());
                    dbHelper.insertTasks(course);
                    Intent intent = new Intent(AddTaskActivity.this,CourseActivity.class);

                    Toast.makeText(getApplicationContext(),"Course id: " + String.valueOf(getIntent().getIntExtra("id",-1)),Toast.LENGTH_SHORT).show();

                    intent.putExtra("position",course.getId());
                    startActivity(intent);
                    overridePendingTransition(R.anim.pull_in_left,R.anim.push_out_right);
                }
            }
            sdf
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this,CourseActivity.class);
        Toast.makeText(getApplicationContext(),"Course id: " + String.valueOf(course.getId()),Toast.LENGTH_SHORT).show();

        intent.putExtra("position",course.getId());
        startActivity(intent);
        overridePendingTransition(R.anim.pull_in_left,R.anim.push_out_right);
    }

    public void taskChosen(View v){

        switch (v.getId()){
            case R.id.addTaskTestButton:
                testChosen = true;
                testType = Test.TEST;
                break;
            case R.id.addTaskMidtermButton:
                testChosen = true;
                testType = Test.MIDTERM;
                break;
            case R.id.addTaskFinalButton:
                testChosen = true;
                testType = Test.FINAL;
                break;
            case R.id.addTaskProjectButton:  testChosen = false;
                break;
            case R.id.addTaskAssignmentButton: testChosen = false;
                break;
            case R.id.addTaskPresentationButton: testChosen = false;
                break;
        }

        int i = 0;
        while (topLayout.getChildCount()>1){
            int j = i%topLayout.getChildCount();
            if(topLayout.getChildAt(j)!= v){
                if(topLayout.getChildAt(j)!=null)
                topLayout.removeView(topLayout.getChildAt(j));
            }
            i++;
        }
        topLayout.setGravity(Gravity.TOP);
        if(testChosen) {
            testLinearLayout.setVisibility(View.VISIBLE);
        }
        else projectLinearLayout.setVisibility(View.VISIBLE);
    }

    public Task makeTask(){
            return new Test(Double.parseDouble(enterTestPercentage.getText().toString()),
                    dateUtils.dateTimeFromPickers(enterTestDate,enterTestTime),
                    testType,
                    Double.parseDouble(enterTestLength.getText().toString()));
    }


    public boolean checkFields(){
        return(DateTimeUtils.checkEditTextEmpty(enterTestPercentage)&&DateTimeUtils.checkEditTextEmpty(enterTestLength));
    }

}
