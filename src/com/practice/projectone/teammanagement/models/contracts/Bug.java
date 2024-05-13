package com.practice.projectone.teammanagement.models.contracts;

import com.practice.projectone.teammanagement.models.enums.Severity;
import com.practice.projectone.teammanagement.models.enums.StatusType;

import java.util.List;

public interface Bug extends Task{
    List<String> getSteps();
    void addStep(String step);

    Severity getSeverity();
    void changeSeverity(Severity severity);
}
