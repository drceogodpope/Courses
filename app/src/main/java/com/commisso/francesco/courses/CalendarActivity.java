package com.commisso.francesco.courses;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CalendarView;

import com.roomorama.caldroid.CaldroidFragment;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Francesco on 2016-12-02.
 */

public class CalendarActivity extends AppCompatActivity {

    private final static String tag = "CalendarActivity";

    DBHelper mDbHelper;
    CaldroidFragment caldroidFragment;
    ArrayList<Task> tasks;
    CalendarView cv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        caldroidFragment = new CaldroidFragment();

        Bundle args = new Bundle();
        Calendar cal = Calendar.getInstance();
        args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
        args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
        caldroidFragment.setArguments(args);

        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.calendarView, caldroidFragment);
        t.commit();

        mDbHelper = DBHelper.getInstance(getApplicationContext());
        tasks = mDbHelper.getAllTasks();
        drawDates();

        cv = (CalendarView) findViewById(R.id.calendarView);
    }



    private void drawDates(){
        Log.d(tag,"drawDates()");
        for(Task t:tasks){
            caldroidFragment.setSelectedDate(t.getDate().toDate());
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
