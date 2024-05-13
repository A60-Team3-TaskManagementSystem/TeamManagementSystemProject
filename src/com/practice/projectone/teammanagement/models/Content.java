package com.practice.projectone.teammanagement.models;

import com.practice.projectone.teammanagement.models.contracts.Assignable;
import com.practice.projectone.teammanagement.models.contracts.Prioritizable;
import com.practice.projectone.teammanagement.models.enums.Priority;

public abstract class Content extends TaskImpl implements Assignable, Prioritizable {

    private Priority priority;
    private Member assignee;

    protected Content(long id, String title, String description) {
        super(id, title, description);
    }


}
