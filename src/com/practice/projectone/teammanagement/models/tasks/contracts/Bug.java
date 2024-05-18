package com.practice.projectone.teammanagement.models.tasks.contracts;

import com.practice.projectone.teammanagement.models.tasks.enums.Severity;

import java.util.List;

public interface Bug extends Task, SpecificTask {
    List<String> getSteps();

    Severity getSeverity();

    public void changeSeverity(Severity newSeverity);
}
