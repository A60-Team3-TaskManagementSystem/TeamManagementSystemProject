package com.practice.projectone.teammanagement.models.tasks.contracts;

import com.practice.projectone.teammanagement.models.contracts.ActivityAble;
import com.practice.projectone.teammanagement.models.contracts.Comment;
import com.practice.projectone.teammanagement.models.contracts.Identifiable;
import com.practice.projectone.teammanagement.models.contracts.Nameable;
import com.practice.projectone.teammanagement.models.tasks.enums.Status;

public interface Task extends Identifiable, Commentable, ActivityAble, Nameable {
    String getDescription();

    Status getStatus();

    void changeStatus(Status status);

    void addComment(Comment comment);
}
