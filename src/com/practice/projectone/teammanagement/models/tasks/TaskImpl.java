package com.practice.projectone.teammanagement.models.tasks;

import com.practice.projectone.teammanagement.exceptions.InvalidUserInputException;
import com.practice.projectone.teammanagement.models.EventLogImpl;
import com.practice.projectone.teammanagement.models.contracts.Comment;
import com.practice.projectone.teammanagement.models.contracts.EventLog;
import com.practice.projectone.teammanagement.models.tasks.contracts.Task;
import com.practice.projectone.teammanagement.models.tasks.enums.Status;
import com.practice.projectone.teammanagement.utils.ValidationHelpers;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.lang.String.format;

public abstract class TaskImpl implements Task {

    private static final int TITLE_LEN_MIN = 10;
    private static final int TITLE_LEN_MAX = 100;
    private static final String TITLE_LEN_ERR = format(
            "Title must be between %s and %s characters long!",
            TITLE_LEN_MIN,
            TITLE_LEN_MAX);
    private static final int DESCRIPTION_LEN_MIN = 10;
    private static final int DESCRIPTION_LEN_MAX = 500;
    private static final String DESCRIPTION_LEN_ERR = format(
            "Description must be between %s and %s characters long!",
            DESCRIPTION_LEN_MIN,
            DESCRIPTION_LEN_MAX);
    private static final String STATUS_CHANGED = "The status of item with ID %d switched from %s to %s";
    private static final String STATUS_SAME_ERR = "Can't change, task status already at %s";
    public static final String COMMENT_ADDED = "New comment %s added by %s";
    private static long idCounter = 0;
    private final long id;
    private String title;
    private String description;
    private Status status;
    private final List<Comment> comments;
    private final List<EventLog> activityHistory;

    protected TaskImpl(String title, String description, Status status) {
        setTitle(title);
        setDescription(description);
        setStatus(status);
        comments = new ArrayList<>();
        activityHistory = new ArrayList<>();
        this.id = ++idCounter;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public String getName() {
        return title;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public Status getStatus() {
        return status;
    }

    @Override
    public void changeStatus(Status newStatus) {
        Status oldStatus = getStatus();

        if (newStatus.equals(oldStatus)) {
            throw new InvalidUserInputException(String.format(STATUS_SAME_ERR, newStatus));
        }

        setStatus(newStatus);
        addEventToHistory(new EventLogImpl(String.format(STATUS_CHANGED, getId(), oldStatus, newStatus)));
    }

    @Override
    public List<EventLog> getActivityHistory() {
        return new ArrayList<>(activityHistory);
    }

    @Override
    public List<Comment> getComments() {
        return new ArrayList<>(comments);
    }

    @Override
    public void addComment(Comment comment) {
        comments.add(comment);

        addEventToHistory(new EventLogImpl(String.format(COMMENT_ADDED, comment.getDescription(), comment.getAuthor())));
    }

    @Override
    public String toString() {
        String taskType = getTaskType();
        return String.format("Task ID%d%n  #Type: %s%n  #Title: %s%n  #Description: %s%n  #Status: %s%n",
                getId(), taskType, title, description, status);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskImpl task = (TaskImpl) o;
        return id == task.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    protected abstract String getTaskType();

    protected abstract void validateStatus(Status status);

    protected void addEventToHistory(EventLog eventLog) {
        activityHistory.add(eventLog);
    }

    private void setTitle(String title) {
        ValidationHelpers.validateStringLength(title, TITLE_LEN_MIN, TITLE_LEN_MAX, TITLE_LEN_ERR);
        this.title = title;
    }

    private void setDescription(String description) {
        ValidationHelpers.validateStringLength(description, DESCRIPTION_LEN_MIN, DESCRIPTION_LEN_MAX, DESCRIPTION_LEN_ERR);
        this.description = description;
    }

    private void setStatus(Status status) {
        validateStatus(status);
        this.status = status;
    }
}
