package com.practice.projectone.teammanagement.models;

import com.practice.projectone.teammanagement.models.contracts.Feedback;
import com.practice.projectone.teammanagement.models.enums.Status;
import com.practice.projectone.teammanagement.utils.ValidationHelpers;

public class FeedbackImpl extends TaskImpl implements Feedback {
    private static final Status INITIAL_STATUS = Status.NEW;
    public static final int RATING_MIN_VALUE = 0;
    public static final int RATING_MAX_VALUE = 10;
    public static final String INVALID_RATING_ERR = String.format("Story rating must be between %d and %d",
                                                                    RATING_MIN_VALUE,
                                                                    RATING_MAX_VALUE);
    private double rating;

    public FeedbackImpl(String title, String description, double rating) {
        super(title, description, INITIAL_STATUS);
        setRating(rating);
    }

    @Override
    public double getRating() {
        return rating;
    }

    @Override
    public void changeRating(int rating) {
        setRating(rating);
    }

    @Override
    protected void validateStatus(Status status) {
        if (!status.getTaskType().equals("Feedback") && !status.getTaskType().equals("All") ) {
            throw new IllegalArgumentException("Please provide valid story status");
        }
    }

    private void setRating(double rating) {
        ValidationHelpers.validateDecimalRange(rating, RATING_MIN_VALUE, RATING_MAX_VALUE, INVALID_RATING_ERR);

        this.rating = rating;
    }

}
