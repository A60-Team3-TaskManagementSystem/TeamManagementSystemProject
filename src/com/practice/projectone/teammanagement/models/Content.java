package com.practice.projectone.teammanagement.models;

import com.practice.projectone.teammanagement.models.contracts.Assignable;
import com.practice.projectone.teammanagement.models.contracts.Member;
import com.practice.projectone.teammanagement.models.contracts.Prioritizable;
import com.practice.projectone.teammanagement.models.enums.Priority;
import com.practice.projectone.teammanagement.models.enums.StatusType;

public abstract class Content extends TaskImpl implements Assignable, Prioritizable {

    private Priority priority;
    private Member assignee;

    protected Content(long id, String title, String description, StatusType statusType, Priority priority, Member assignee) {
        super(id, title, description, statusType);
        this.priority = priority;
        this.assignee = assignee;
    }


    @Override
    public Member getAssignee() {
        return assignee;
    }

    @Override
    public Priority getPriority() {
        return priority;
    }
}
