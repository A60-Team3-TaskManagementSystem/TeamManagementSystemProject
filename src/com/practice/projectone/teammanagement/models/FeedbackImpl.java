package com.practice.projectone.teammanagement.models;

import com.practice.projectone.teammanagement.models.contracts.Feedback;
import com.practice.projectone.teammanagement.models.enums.StatusType;

public class FeedbackImpl extends TaskImpl implements Feedback {

    private int rating;

    public FeedbackImpl (int id, String title, String description, StatusType status, int rating) {
        super (id, title, description, status);
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
