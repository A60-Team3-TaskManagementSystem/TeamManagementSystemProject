package com.practice.projectone.teammanagement.models.contracts;

import com.practice.projectone.teammanagement.models.enums.Status;

public interface Task extends Identifiable, Commentable, ActivityAble, Nameable{
    String getDescription();
    Status getStatus();
    void addComment(Comment comment);
    void removeComment(Comment comment);
    void changeStatus(Status status);

}
