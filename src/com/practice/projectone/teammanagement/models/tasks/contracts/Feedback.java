package com.practice.projectone.teammanagement.models.tasks.contracts;

public interface Feedback extends Task{
    int getRating();
    public void changeRating(int rating);
}
