package com.practice.projectone.teammanagement.models;

import com.practice.projectone.teammanagement.models.contracts.Comment;
import com.practice.projectone.teammanagement.models.contracts.Task;
import com.practice.projectone.teammanagement.models.enums.StatusType;
import com.practice.projectone.teammanagement.utils.ValidationHelpers;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

public abstract class TaskImpl implements Task {

    public static final int TITLE_LEN_MIN = 10;
    public static final int TITLE_LEN_MAX = 100;
    private static final String TITLE_LEN_ERR = format(
            "Title must be between %s and %s characters long!",
            TITLE_LEN_MIN,
            TITLE_LEN_MAX);
    public static final int DESCRIPTION_LEN_MIN = 10;
    public static final int DESCRIPTION_LEN_MAX = 500;
    private static final String DESCRIPTION_LEN_ERR = format(
            "Description must be between %s and %s characters long!",
            DESCRIPTION_LEN_MIN,
            DESCRIPTION_LEN_MAX);

    private final long id;
    private String title;
    private String description;
    private final List<Comment> comments;
    private final List<EventLog> activityHistory;

    public TaskImpl(long id, String title, String description) {
        this.id = id;
        setTitle(title);
        setDescription(description);
        comments = new ArrayList<>();
        activityHistory = new ArrayList<>();
    }

    private void setTitle(String title) {
        ValidationHelpers.validateStringLength(title, TITLE_LEN_MIN, TITLE_LEN_MAX, TITLE_LEN_ERR);
        this.title = title;
    }

    @Override
    public String getTitle() {
        return title;
    }

    private void setDescription(String description) {
        ValidationHelpers.validateStringLength(description, DESCRIPTION_LEN_MIN, DESCRIPTION_LEN_MAX, DESCRIPTION_LEN_ERR);
        this.description = description;
    }


    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public StatusType getStatus() {
        return getStatus();
    }

    @Override
    public List<Comment> getComments() {
        return new ArrayList<>(comments);
    }

    @Override
    public List<EventLog> getActivityHistory() {
        return new ArrayList<>(activityHistory);
    }

    @Override
    public void addComment(Comment comment) {
        comments.add(comment);
    }

    @Override
    public void removeComment(Comment comment) {
        comments.remove(comment);
    }

    protected abstract void revertStatus();
    protected abstract void advanceStatus();
}
