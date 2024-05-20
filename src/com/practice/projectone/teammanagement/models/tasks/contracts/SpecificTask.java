package com.practice.projectone.teammanagement.models.tasks.contracts;

import com.practice.projectone.teammanagement.models.tasks.enums.Priority;

public interface SpecificTask extends Task {
    Priority getPriority();

    public void changePriority(Priority newPriority);

    String getAssignee();

    public void changeAssignee(String assigneeName);
}
