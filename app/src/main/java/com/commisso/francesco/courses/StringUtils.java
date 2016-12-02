package com.commisso.francesco.courses;

/**
 * Created by Francesco on 2016-11-10.
 */

public class StringUtils {

    public String addSpaces(String word){
        String result = "";
        for(int i=0;i<word.length();i++){
            result+=word.substring(i,i+1);
            result+=" ";
        }
        return result;
    }

    // added something

}
