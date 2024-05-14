package com.practice.projectone.teammanagement.models.contracts;

public interface Feedback extends Task{
    double getRating();

    void changeRating(int rating);
}
