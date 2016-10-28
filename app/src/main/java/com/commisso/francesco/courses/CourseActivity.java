package com.commisso.francesco.courses;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.joda.time.DateTime;

import java.util.ArrayList;

/**
 * ACTIVITY TO VIEW THE INFO FOR A SPECIFIC COURSE
 */

public class CourseActivity extends AppCompatActivity {

    Course course;
    SeekBar sb;
    TextView title;
    TextView courseCode;
    TextView time;
    TextView remainingDays;
    TextView teacher;

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_activity);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        Intent i = getIntent();
        course = MainActivity.COURSES.get(i.getIntExtra("position",0));

        title = (TextView) findViewById(R.id.textViewCourseTitle);
        courseCode = (TextView) findViewById(R.id.textViewCourseCode);
        time = (TextView) findViewById(R.id.textViewTime);
        remainingDays = (TextView) findViewById(R.id.textViewNumberOfDays);
        teacher = (TextView) findViewById(R.id.textViewTeacher);


        ArrayList<Task> sampleTasks = new ArrayList<>();

        for(int j = 0; j<20;j++){
            Test t = new Test(33,new DateTime().plusDays(20),Test.TEST);
            sampleTasks.add(t);
            System.out.print(t.getTitle());
        }

        recyclerView = (RecyclerView) findViewById(R.id.tasksRecyclerView);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new TaskAdapter(sampleTasks));


        title.setText(addSpaces(course.getTitle()));
        courseCode.setText(course.getCourseCode());
        time.setText(course.getDay() +" "+ course.getTime());
        remainingDays.setText(String.valueOf(course.getNumberOfDays()));


        sb = (SeekBar) findViewById(R.id.progressBar);
        sb.setMax(10);
        sb.getThumb().mutate().setAlpha(0);
        sb.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return false;
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }


    public String addSpaces(String word){
        String result = "";
        for(int i=0;i<word.length();i++){
            result+=word.substring(i,i+1);
            result+=" ";
        }
        return result;

    }

}
