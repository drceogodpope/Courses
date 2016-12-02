package com.commisso.francesco.courses;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import org.joda.time.DateTime;
import org.joda.time.DateTimeComparator;
import org.joda.time.LocalTime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    private static final String tag = "MainActivity";

    private FloatingActionButton fab;
    private RecyclerView coursesRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        dbHelper = DBHelper.getInstance(getApplicationContext());

        coursesRecyclerView = (RecyclerView) findViewById(R.id.coursesRecyclerView);
        mLayoutManager = new LinearLayoutManager(this);
        coursesRecyclerView.setLayoutManager(mLayoutManager);
        coursesRecyclerView.setAdapter(new CourseAdapter(dbHelper.getCourses()));

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageResource(R.drawable.ic_action_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),AddCourseActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.pull_in_left,R.anim.push_out_right);
            }
        });
        fab.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View view) {
                dbHelper.onUpgrade(dbHelper.getReadableDatabase(),0,1);
                Intent intent = new Intent(MainActivity.this,MainActivity.class);
                startActivity(intent);
                return false;
            }
        });

    }

    @Override
    public void onBackPressed() {
        Log.d(tag,  "*** BEFORE \n" + dbHelper.getTableAsString(DBHelper.COURSES_TABLE_NAME) +
                    dbHelper.getTableAsString(DBHelper.TESTS_TABLE_NAME) +
                    dbHelper.getTableAsString(DBHelper.PROJECT_TABLE_NAME) +
                    "*** \n");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
}