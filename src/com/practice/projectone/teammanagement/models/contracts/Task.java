package com.practice.projectone.teammanagement.models.contracts;

import com.practice.projectone.teammanagement.models.enums.StatusType;

import java.util.List;

public interface Task extends Identifiable{
    String getTitle();
    String getDescription();
    StatusType getStatus();
    List<Comment> getComments();
    List<EventLog> getActivityHistory();
    void addComment(Comment comment);
    void removeComment(Comment comment);

}
