package com.practice.projectone.teammanagement.models;

import com.practice.projectone.teammanagement.models.contracts.Assignable;
import com.practice.projectone.teammanagement.models.contracts.Person;
import com.practice.projectone.teammanagement.models.contracts.Prioritizable;
import com.practice.projectone.teammanagement.models.enums.Priority;
import com.practice.projectone.teammanagement.models.enums.StatusType;

public abstract class Content extends TaskImpl implements Assignable, Prioritizable {

    private Priority priority;
    private Person assignee;

    protected Content(long id, String title, String description, StatusType statusType, Priority priority, Person assignee) {
        super(id, title, description, statusType);
        this.priority = priority;
        this.assignee = assignee;
    }


    @Override
    public Person getAssignee() {
        return assignee;
    }

    @Override
    public Priority getPriority() {
        return priority;
    }

    protected void setPriority(Priority priority){
        this.priority = priority;
    }
}
