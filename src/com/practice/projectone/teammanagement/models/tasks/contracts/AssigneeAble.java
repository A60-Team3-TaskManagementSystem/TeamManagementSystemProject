package com.practice.projectone.teammanagement.models.tasks.contracts;

public interface AssigneeAble extends Task {
    String getAssignee();
    public void changeAssignee(String assigneeName);
}
