package com.commisso.francesco.courses;

import android.content.res.TypedArray;
import android.util.TypedValue;

/**
 * Created by Francesco on 2016-11-13.
 */

public class ColourUtils {

    public static int getColorAccent() {
        TypedValue typedValue = new TypedValue();

        TypedArray a = Courses.getAppContext().obtainStyledAttributes(typedValue.data, new int[] { R.attr.colorAccent });
        int color = a.getColor(0, 0);

        a.recycle();

        return color;
    }

}
