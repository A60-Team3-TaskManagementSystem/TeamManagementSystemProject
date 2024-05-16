package com.practice.projectone.teammanagement.models.contracts;

import com.practice.projectone.teammanagement.models.enums.Priority;
import com.practice.projectone.teammanagement.models.enums.Severity;
import com.practice.projectone.teammanagement.models.enums.Size;
import com.practice.projectone.teammanagement.models.enums.Status;

public interface Task extends Identifiable, Commentable, ActivityAble, Nameable{
    String getDescription();
    Status getStatus();
    void addComment(Comment comment);
    void removeComment(Comment comment);
    void changeStatus(Status status);
    void changePriority(Priority priority);
    void changeSize(Size size);
    void changeRating(int rating);
    void changeSeverity(Severity severity);
    void changeAssignee(String assigneeName);
}
