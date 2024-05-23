package com.practice.projectone.teammanagement.models.tasks;

import com.practice.projectone.teammanagement.exceptions.InvalidUserInputException;
import com.practice.projectone.teammanagement.models.EventLogImpl;
import com.practice.projectone.teammanagement.models.tasks.contracts.Feedback;
import com.practice.projectone.teammanagement.models.tasks.enums.Status;
import com.practice.projectone.teammanagement.utils.ValidationHelpers;

public class FeedbackImpl extends TaskImpl implements Feedback {
    private static final Status INITIAL_STATUS = Status.NEW;
    public static final int RATING_MIN_VALUE = 0;
    public static final int RATING_MAX_VALUE = 10;
    public static final String INVALID_RATING_ERR = String.format("Story rating must be between %d and %d",
            RATING_MIN_VALUE,
            RATING_MAX_VALUE);
    private static final String RATING_CHANGED = "The rating of item with ID %d switched from %d to %d";
    private static final String RATING_SAME_ARR = "Can't change, rating already is %d";

    private int rating;

    public FeedbackImpl(String title, String description, int rating) {
        super(title, description, INITIAL_STATUS);
        setRating(rating);

        addEventToHistory(new EventLogImpl(String.format("Task %s with ID%d created", title, getId())));
    }

    @Override
    public int getRating() {
        return rating;
    }

    @Override
    public void changeRating(int rating) {
        int oldRating = this.rating;
        if (rating == oldRating) {
            throw new InvalidUserInputException(String.format(RATING_SAME_ARR, this.rating));
        }

        setRating(rating);
        addEventToHistory(new EventLogImpl(String.format(RATING_CHANGED, getId(), oldRating, rating)));
    }

    @Override
    public String toString() {
        return String.format("%s  #Rating: %d%n", super.toString(), rating);
    }

    @Override
    protected void validateStatus(Status status) {
        if (!status.getTaskType().equals("Feedback") && !status.getTaskType().equals("All")) {
            throw new IllegalArgumentException("Please provide valid story status");
        }
    }

    @Override
    protected String getTaskType() {
        return getClass().getSimpleName().substring(0, getClass().getSimpleName().length() - 4);
    }

    private void setRating(int rating) {
        ValidationHelpers.validateDecimalRange(rating, RATING_MIN_VALUE, RATING_MAX_VALUE, INVALID_RATING_ERR);

        this.rating = rating;
    }
}
