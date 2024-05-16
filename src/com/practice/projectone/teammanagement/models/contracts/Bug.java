package com.practice.projectone.teammanagement.models.contracts;

import com.practice.projectone.teammanagement.models.enums.Severity;

import java.util.List;

public interface Bug extends Task, Assignable, Prioritizable{
    List<String> getSteps();
    Severity getSeverity();
}
