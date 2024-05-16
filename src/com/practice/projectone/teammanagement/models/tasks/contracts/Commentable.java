package com.practice.projectone.teammanagement.models.tasks.contracts;

import com.practice.projectone.teammanagement.models.contracts.Comment;

import java.util.List;

public interface Commentable {
    List<Comment> getComments();
}
