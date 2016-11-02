package com.commisso.francesco.courses;

import org.joda.time.DateTime;

public  class Project extends Task {

    private String groupMembers = "";

    public Project(double percentage, DateTime date,String title,String groupMembers) {

        super(percentage, date);
        this.groupMembers = groupMembers;
        this.title = title;
    }




    public String getGroupMembers() {
        return groupMembers;
    }

    public void setGroupMembers(String groupMembers) {
        this.groupMembers = groupMembers;
    }
}
