package com.commisso.francesco.courses;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Francesco on 2016-10-28.
 */

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskCardViewHolder> {

    private ArrayList<Task> tasks;

    public TaskAdapter(ArrayList<Task> tasks) {
        super();
        this.tasks = tasks;
    }

    @Override
    public TaskCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_card,parent,false);
        return new TaskCardViewHolder(itemView);    }

    @Override
    public int getItemCount() {return tasks.size();}

    public static class TaskCardViewHolder extends RecyclerView.ViewHolder{

        protected TextView title;
        protected TextView daysLeft;
        protected TextView percentage;
        protected CardView cardView;
        protected  View v;

        public TaskCardViewHolder(View v) {
            super(v);
            title =  (TextView) v.findViewById(R.id.taskCardTitle);
            daysLeft = (TextView)  v.findViewById(R.id.taskCardNumberOfDays);
            percentage = (TextView)  v.findViewById(R.id.taskCardPercentage);
            cardView = (CardView) v.findViewById(R.id.taskCard);
        }
    }

    @Override
    public void onBindViewHolder(TaskCardViewHolder holder, int position) {
        Task t = tasks.get(position);
        holder.title.setText(t.getTitle());
        holder.daysLeft.setText(String.valueOf(t.getDays()));
        holder.percentage.setText(String.valueOf((int)t.getPercentage()) + "%");
    }
}
