package com.commisso.francesco.courses;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.util.Log;
import android.view.Menu;
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
    FloatingActionButton fab;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_activity);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        Intent intent = getIntent();

        dbHelper = DBHelper.getInstance(getApplicationContext());
        if( dbHelper.getCourse(dbHelper.getReadableDatabase(),intent.getLongExtra("id",1)) != null){
            course = dbHelper.getCourse(dbHelper.getReadableDatabase(),intent.getLongExtra("id",1));
        }


        title = (TextView) findViewById(R.id.textViewCourseTitle);
        courseCode = (TextView) findViewById(R.id.textViewCourseCode);
        time = (TextView) findViewById(R.id.textViewTime);
        remainingDays = (TextView) findViewById(R.id.textViewNumberOfDays);
        teacher = (TextView) findViewById(R.id.textViewTeacher);
        fab = (FloatingActionButton) findViewById(R.id.floatingActionButtonCourseActivity);

        recyclerView = (RecyclerView) findViewById(R.id.tasksRecyclerView);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new TaskAdapter(dbHelper.getTasks(dbHelper.getReadableDatabase(),course)));


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

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getApplicationContext(),String.valueOf(course.getId()),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(),AddTaskActivity.class);
                intent.putExtra("id",course.getId());
                startActivity(intent);

                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
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
