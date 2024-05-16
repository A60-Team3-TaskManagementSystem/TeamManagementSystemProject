package com.practice.projectone.teammanagement.models.tasks.contracts;

import java.util.List;

public interface Bug extends Task, AssigneeAble, PrioritizeAble, SeverityAble {
    List<String> getSteps();
}
