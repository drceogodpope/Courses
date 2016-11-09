package com.commisso.francesco.courses;

import org.joda.time.DateTime;

public  class Project extends Task {


    public static final int PROJECT_TYPE_PROJECT = 0;
    public static final int PROJECT_TYPE_ASSIGNMENT = 1;
    public static final int PROJECT_TYPE_PRESENTATION = 2;


    private String groupMembers = "";
    private int projectType = PROJECT_TYPE_PROJECT;

    public Project(double percentage, DateTime date,int projectType) {
        super(percentage, date);
        this.projectType = projectType;
    }

    public Project(double percentage, DateTime date,String title,String groupMembers,int projectType) {
        super(percentage, date);
        this.groupMembers = groupMembers;
        this.title = title;
        this.projectType = projectType;
    }


    public String getGroupMembers() {
        return groupMembers;
    }

    public String getTitle() {
        switch (projectType){
            case PROJECT_TYPE_ASSIGNMENT: return "Assignment";
            case PROJECT_TYPE_PRESENTATION: return "Presentation";
        }
        return "Project";
    }

    public int getProjectType(){
        return projectType;
    }


    public void setGroupMembers(String groupMembers) {
        this.groupMembers = groupMembers;
    }

    @Override
    public void setTitle(String title) {
        super.setTitle(title);
    }
}
