package com.practice.projectone.teammanagement.models.tasks.contracts;

import com.practice.projectone.teammanagement.models.tasks.enums.Severity;

import java.util.List;

public interface Bug extends Task, AssigneeAble, PrioritizeAble {
    List<String> getSteps();

    Severity getSeverity();

    public void changeSeverity(Severity newSeverity);
}
