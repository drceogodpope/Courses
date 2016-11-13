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



public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskCardViewHolder> {

    private ArrayList<Task> tasks;
    private long id; // REMOVE ME AFTER TASK COURSE IS MADE AND YOU CAN DELETE FROM THERE !!

    // REMOVE long courseID AFTER TASK COURSE IS MADE AND DELETE FROM THERE
    public TaskAdapter(ArrayList<Task> tasks,long courseID) {
        super();
        this.tasks = tasks;
        this.id = courseID;
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
    public void onBindViewHolder(final TaskCardViewHolder holder, int position) {
        final Task t = tasks.get(position);
        holder.title.setText(t.getTitle());
        holder.daysLeft.setText(String.valueOf(t.getDays()));
        holder.percentage.setText(String.valueOf((int)t.getPercentage()) + "%");

        //REMOVE ALL THIS STUFF AFTER TASK COURSE IS MADE AND DELETE FROM THERE !!

        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                DBHelper dbHelper = DBHelper.getInstance(Courses.getAppContext());
                if(t instanceof Test){
                    System.out.println("TEST");
                    System.out.println("T.ID: "+String.valueOf(t.getId()));
                    dbHelper.deleteTest(t.getId());
                }
                else {
                    System.out.println("PROJECT");
                    dbHelper.deleteProject(t.getId());
                }

                tasks = dbHelper.getTasks(dbHelper.getCourse(id));
                notifyItemRangeChanged(holder.getAdapterPosition(), tasks.size());
                notifyItemRemoved(holder.getAdapterPosition());
                return false;
            }
        });
    }
}
