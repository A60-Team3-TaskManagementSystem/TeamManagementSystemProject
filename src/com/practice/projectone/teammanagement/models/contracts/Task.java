package com.practice.projectone.teammanagement.models.contracts;

import com.practice.projectone.teammanagement.models.enums.StatusType;

import java.util.List;

public interface Task extends Identifiable, Commentable, ActivityAble, Nameable{
    String getDescription();
    StatusType getStatus();
    void addComment(Comment comment);
    void removeComment(Comment comment);
    void changeStatus(StatusType statusType);

}
