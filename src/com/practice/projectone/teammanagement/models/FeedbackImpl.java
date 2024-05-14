package com.practice.projectone.teammanagement.models;

import com.practice.projectone.teammanagement.models.contracts.Feedback;
import com.practice.projectone.teammanagement.models.enums.Status;

public class FeedbackImpl extends TaskImpl implements Feedback {

    private int rating;

    public FeedbackImpl (String title, String description, Status status, int rating) {
        super (title, description, status);
        this.rating = rating;
    }
    @Override
    public int getRating() {
        return rating;
    }

    @Override
    protected void revertStatus() {

    }

    @Override
    protected void advanceStatus() {

    }
}
