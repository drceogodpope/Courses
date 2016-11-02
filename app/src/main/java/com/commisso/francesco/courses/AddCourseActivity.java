package com.commisso.francesco.courses;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;


public class AddCourseActivity extends AppCompatActivity {

    RelativeLayout mainLayout;

    ScrollView scrollView;
    LinearLayout finalLinearLayout;

    LinearLayout addTitleLayout;
    LinearLayout addCodeLayout;
    LinearLayout addStartDateLayout;
    LinearLayout addEndDateLayout;
    LinearLayout addTimeLayout;
    FloatingActionButton fab;
    ArrayList<LinearLayout> layouts;

    EditText enterCourseTitle;
    EditText enterCourseCode;
    DatePicker enterStartDate;
    DatePicker enterEndDate;
    TimePicker enterTime;

    int currentLayout = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_course_activity);
        final DBHelper dbHelper = DBHelper.getInstance(getApplicationContext());

        //Input manager to manage keyboard shown
       final InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow((null == getCurrentFocus()) ? null : getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);


        mainLayout = (RelativeLayout) findViewById(R.id.addCourseRelativeLayout);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        finalLinearLayout = (LinearLayout) findViewById(R.id.finalLinearLayout);

        addTitleLayout = (LinearLayout) findViewById(R.id.layoutEnterCourseName);
        addCodeLayout = (LinearLayout) findViewById(R.id.layoutEnterCourseCode);
        addStartDateLayout = (LinearLayout) findViewById(R.id.layoutEnterCourseStartDate);
        addEndDateLayout = (LinearLayout) findViewById(R.id.layoutEnterCourseEndDate);
        addTimeLayout = (LinearLayout) findViewById(R.id.layoutEnterCourseTime);
        fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);

        //array of layouts to show and hide
        layouts = new ArrayList<>();
        layouts.add(addTitleLayout);
        layouts.add(addCodeLayout);
        layouts.add(addStartDateLayout);
        layouts.add(addEndDateLayout);
        layouts.add(addTimeLayout);

        enterCourseTitle = (EditText) findViewById(R.id.editTextCourseTitle);
        enterCourseCode = (EditText) findViewById(R.id.editTextCourseCode);
        enterStartDate = (DatePicker) findViewById(R.id.datePickerStartDate);
        enterEndDate = (DatePicker) findViewById(R.id.datePickerEndDate);
        enterTime = (TimePicker) findViewById(R.id.timePickercourseTime);

        enterEndDate.setMinDate(Calendar.getInstance().getTimeInMillis());

        for(int i = 1;i<layouts.size();i++){
            layouts.get(i).setVisibility(View.GONE);
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputManager.hideSoftInputFromWindow((null == getCurrentFocus()) ? null : getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                if(currentLayout>=0) {
                    showNextLayout();
                }
                else{
                    if(checkArguments()){
                        createCourse();
                        Intent intent = new Intent(AddCourseActivity.this,MainActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Check Fields!",Toast.LENGTH_SHORT).show();}
                }
            }
        });
    }


    public void showNextLayout(){
        layouts.get(currentLayout).setVisibility(View.GONE);
            if (currentLayout < layouts.size() - 1) {
                currentLayout++;
                layouts.get(currentLayout).setVisibility(View.VISIBLE);
            } else {
                showAllLayouts();
            }
    }


    public void showAllLayouts(){
        if(currentLayout>0){
            for(LinearLayout l : layouts){
                l.setVisibility(View.GONE);
            }

            scrollView.setVisibility(View.VISIBLE);

            for(LinearLayout l : layouts){
              View view = l.getChildAt(1);
                l.removeView(view);
                finalLinearLayout.addView(view);
                TextView tv = new TextView(getApplicationContext());
                tv.setVisibility(View.INVISIBLE);
                finalLinearLayout.addView(tv);
            }

            currentLayout = -1;
        }
    }


    public boolean checkArguments(){
        if(!(enterCourseTitle.getText().toString().trim().length()>0)) return false;
        if(!(enterCourseCode.getText().toString().trim().length()>0)) return false;
        return true;
    }


    public void createCourse(){
        String courseTitle = enterCourseTitle.getText().toString();
        String courseCode = enterCourseCode.getText().toString();
        DateTime startDate = getDateTimeFromInputs(enterStartDate,enterTime);
        DateTime endDate = getDateTimeFromInputs(enterEndDate,enterTime);
        LocalTime localTime = getTimeFromInputs(enterTime);
        DBHelper dbHelper = DBHelper.getInstance(getApplicationContext());
        dbHelper.insertCourse(new Course(courseTitle,courseCode,startDate,endDate,localTime));
    }

    public DateTime getDateTimeFromInputs(DatePicker datePicker,TimePicker timePicker){

        int year = datePicker.getYear();
        int month = datePicker.getMonth() + 1;
        int dayOfMonth = datePicker.getDayOfMonth();
        int hourOfDay = timePicker.getHour();
        int minuteOfHour = timePicker.getMinute();
        return new DateTime(year,month,dayOfMonth,hourOfDay,minuteOfHour);
    }

    public LocalTime getTimeFromInputs(TimePicker timePicker){
        return new LocalTime(timePicker.getHour(),timePicker.getMinute());
    }


    @Override
    public void onBackPressed() {
        if(currentLayout>0){
            layouts.get(currentLayout).setVisibility(View.GONE);
            if(currentLayout>0){
                currentLayout--;
                layouts.get(currentLayout).setVisibility(View.VISIBLE);
            }
        }
        else{
            super.onBackPressed();
            overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);

        }

    }



}