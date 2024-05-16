package com.practice.projectone.teammanagement.models.tasks.contracts;

public interface Feedback extends Task{
    double getRating();
    public void changeRating(int rating);
}
