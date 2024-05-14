package com.practice.projectone.teammanagement.models;

import com.practice.projectone.teammanagement.models.contracts.Assignable;
import com.practice.projectone.teammanagement.models.contracts.Prioritizable;
import com.practice.projectone.teammanagement.models.enums.Priority;
import com.practice.projectone.teammanagement.models.enums.Status;

public abstract class Content extends TaskImpl implements Assignable, Prioritizable {

    private Priority priority;
    private String assigneeName;

    protected Content(String title, String description, Status status, Priority priority, String assigneeName) {
        super(title, description, status);
        this.priority = priority;
        this.assigneeName = assigneeName;
    }


    @Override
    public String getAssignee() {
        return assigneeName;
    }

    @Override
    public Priority getPriority() {
        return priority;
    }

    protected void setPriority(Priority priority){
        this.priority = priority;
    }
}
