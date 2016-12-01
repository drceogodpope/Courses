package com.commisso.francesco.courses;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Collections;

/**
 * ACTIVITY TO VIEW THE INFO FOR A SPECIFIC COURSE
 */

public class CourseActivity extends AppCompatActivity {

    private static final String tag = "CourseActivity";

    private Course course;
    private SeekBar sb;
    private TextView title;
    private TextView courseCode;
    private TextView time;
    private TextView remainingDays;
    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private DBHelper dbHelper;
    private int daysLeft;
    private TaskAdapter ta;

    private NotificationManager notifManager;
    private boolean notifActive = false;
    private int notifId = 33;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_activity);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        Intent intent = getIntent();

        dbHelper = DBHelper.getInstance(getApplicationContext());
        if( dbHelper.getCourse(intent.getLongExtra("id",1)) != null){
            course = dbHelper.getCourse(intent.getLongExtra("id",1));
        }


        title = (TextView) findViewById(R.id.textViewCourseTitle);
        courseCode = (TextView) findViewById(R.id.textViewCourseCode);
        time = (TextView) findViewById(R.id.textViewTime);
        remainingDays = (TextView) findViewById(R.id.textViewNumberOfDays);
        fab = (FloatingActionButton) findViewById(R.id.floatingActionButtonCourseActivity);
        fab.setImageResource(R.drawable.ic_action_add);

        recyclerView = (RecyclerView) findViewById(R.id.tasksRecyclerView);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        ta = new TaskAdapter(dbHelper.getTasks(course),course.getId());
        recyclerView.setAdapter(ta);


        title.setText(course.getTitle());
        courseCode.setText(course.getCourseCode());
        time.setText(course.getDay() +" "+ course.getTime());
        setRemainingDays();
        setRemainingDaysColour();



        sb = (SeekBar) findViewById(R.id.progressBar);
        initializeSeekBar();




        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),AddTaskActivity.class);
                intent.putExtra("id",course.getId());
                startActivity(intent);

                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
            }
        });

    }

    public void setRemainingDays(){
        daysLeft = DateTimeUtils.daysRemaining(course.getEndDate());
        remainingDays.setText(String.valueOf(daysLeft));
    }

    public void setRemainingDaysColour(){
        remainingDays.setTextColor(Course.decideTextColour(course));
    }

    public void initializeSeekBar(){
        final int maxDays = DateTimeUtils.daysBetween(course.getStartDate(),course.getEndDate());
        int daysElapsed = DateTimeUtils.daysBetween(course.getStartDate(),new DateTime());

        sb.setMax(maxDays);
        sb.getThumb().mutate().setAlpha(0);
        sb.setProgress(daysElapsed);
        sb.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.course_activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_delete_course:
                deleteCourse();
                return true;
            case R.id.notification_toggle:
//                toggleCourseNotifications();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void toggleCourseNotifications() {
        if(!notifActive){
            NotificationCompat.Builder notifBuilder =
                    new NotificationCompat.Builder(this)
                            .setContentTitle(course.getTitle())
                            .setContentText(ta.getTask(0).getTitle() + " in " +String.valueOf(daysLeft) + " Days")
                            .setTicker("Alert New Message")
                            .setSmallIcon(R.drawable.cast_ic_notification_small_icon); // change me to app icon
            notifManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notifManager.notify(notifId,notifBuilder.build());
            Toast.makeText(this,"Notifications Enabled for Course",Toast.LENGTH_SHORT).show();
            notifActive = true;
        }
        else{
            notifManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notifManager.cancel(notifId);
            Toast.makeText(this,"Notifications Disabled for Course",Toast.LENGTH_SHORT).show();
            notifActive = false;
        }

    }


    public void deleteCourse(){
        dbHelper.deleteCourse(course);
        onBackPressed();
    }

}
