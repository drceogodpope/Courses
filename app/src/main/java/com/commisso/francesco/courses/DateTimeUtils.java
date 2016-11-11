package com.commisso.francesco.courses;

import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import org.joda.time.DateTime;
import org.joda.time.Days;

/**
 * Created by Francesco on 2016-11-03.
 */

public class DateTimeUtils {

    public DateTimeUtils(){}

    public static DateTime dateTimeFromPickers(DatePicker datePicker, TimePicker timePicker){
        int year = datePicker.getYear();
        int month = datePicker.getMonth() + 1;
        int dayOfMonth = datePicker.getDayOfMonth();
        int hourOfDay = timePicker.getHour();
        int minuteOfHour = timePicker.getMinute();
        return new DateTime(year,month,dayOfMonth,hourOfDay,minuteOfHour);
    }

    public static boolean checkEditTextEmpty(EditText editText){
        return ((editText.getText().toString().trim().length()>0));
    }

    public static int daysRemaining(DateTime date){
        int numberOfDays = Days.daysBetween(new DateTime().toLocalDate(),date.toLocalDate()).getDays();
        if (numberOfDays>0){return numberOfDays;}
        return 0;
    }

}
