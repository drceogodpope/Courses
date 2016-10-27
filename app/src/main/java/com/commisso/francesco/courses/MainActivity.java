package com.commisso.francesco.courses;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton fab;
    RecyclerView coursesRecyclerView;
    LinearLayoutManager mLayoutManager;
    DBHelper dbHelper;
    public static ArrayList<Course> COURSES;

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
        COURSES = new ArrayList<>();
        if(dbHelper.getCourses(dbHelper.getReadableDatabase(),COURSES)!= null){
            COURSES = dbHelper.getCourses(dbHelper.getReadableDatabase(),COURSES);
        }

        coursesRecyclerView = (RecyclerView) findViewById(R.id.coursesRecyclerView);
        mLayoutManager = new LinearLayoutManager(this);
        coursesRecyclerView.setLayoutManager(mLayoutManager);
        coursesRecyclerView.setAdapter(new CourseAdapter(COURSES));


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AddCourseActivity.class);
                startActivity(intent);
//                overridePendingTransition(android.R.anim.,android.R.anim.slide_out_right);    !ADD TRANSITIONS
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;    }
}