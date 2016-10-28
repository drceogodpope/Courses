package com.commisso.francesco.courses;

import org.joda.time.DateTime;

public abstract class Project extends Task {
    public Project(double percentage, DateTime date) {
        super(percentage, date);
    }
}
