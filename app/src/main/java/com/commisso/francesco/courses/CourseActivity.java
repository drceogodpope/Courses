package com.commisso.francesco.courses;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;

/**
 * ACTIVITY TO VIEW THE INFO FOR A SPECIFIC COURSE
 */

public class CourseActivity extends AppCompatActivity {

    Course course;
    SeekBar sb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_activity);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        Intent i = getIntent();
        course = MainActivity.COURSES.get(i.getIntExtra("position",0));

        Toast.makeText(getApplicationContext(),course.getTitle(),Toast.LENGTH_SHORT).show();

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
}
