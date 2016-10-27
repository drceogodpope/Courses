package com.commisso.francesco.courses;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Francesco on 2016-10-14.
 */

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseCardViewHolder> {

    ArrayList<Course> courses;

    public CourseAdapter(ArrayList<Course> courses) {
        this.courses = courses;
    }

    public static class CourseCardViewHolder extends RecyclerView.ViewHolder {
        protected TextView courseTitle;
        protected TextView courseCode;
        protected TextView courseTime;
        protected CardView cardView;
        long courseId = -1;
        protected View v;

        public CourseCardViewHolder(View v) {
            super(v);
            courseTitle =  (TextView) v.findViewById(R.id.course_card_title);
            courseCode = (TextView)  v.findViewById(R.id.course_card_code);
            courseTime = (TextView)  v.findViewById(R.id.course_card_time);
            cardView = (CardView) v.findViewById(R.id.course_card_card);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent intent = new Intent(context,CourseActivity.class);
                    intent.putExtra("position",getAdapterPosition());
                    context.startActivity(intent);
                }
            });
            v.setBackgroundColor(ContextCompat.getColor(v.getContext(),R.color.white));
        }

    }

    @Override
    public void onBindViewHolder(CourseCardViewHolder holder, int position) {
        Course c = courses.get(position);
        holder.courseTitle.setText(c.getTitle());
        holder.courseCode.setText(c.getCourseCode());
        holder.courseTime.setText(c.getDay()+" " +c.getTime());
        holder.courseId = c.getId();
    }

    @Override
    public CourseCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_card,parent,false);
        return new CourseCardViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    public ArrayList<Course> getCourses(){
        return courses;
    }

}
