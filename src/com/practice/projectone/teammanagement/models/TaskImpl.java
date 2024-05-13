package com.practice.projectone.teammanagement.models;

import com.practice.projectone.teammanagement.exceptions.InvalidUserInputException;
import com.practice.projectone.teammanagement.models.contracts.Comment;
import com.practice.projectone.teammanagement.models.contracts.EventLog;
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
    private StatusType statusType;
    private final List<Comment> comments;
    private final List<EventLog> activityHistory;

    protected TaskImpl(long id, String title, String description, StatusType statusType) {
        this.id = id;
        setTitle(title);
        setDescription(description);
        setStatusType(statusType);
        comments = new ArrayList<>();
        activityHistory = new ArrayList<>();

        addEventToHistory(new EventLogImpl(String.format("Task created %s",this)));
    }

    @Override
    public long getId() {
        return id;
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
    public StatusType getStatus() {
        return getStatus();
    }

    protected void setStatusType(StatusType statusType){
        this.statusType = statusType;
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

    @Override
    public void changeStatus(StatusType statusType) {
        if (statusType.equals(getStatus())) {
            throw new InvalidUserInputException(String.format("Can't change, task status already at %s", statusType));
        }

        setStatusType(statusType);
        addEventToHistory(new EventLogImpl(String.format("Task status changed to %s", statusType)));
    }

    protected void addEventToHistory(EventLog eventLog) {
        activityHistory.add(eventLog);
    }

    @Override
    public String toString() {
        return """
                ID%d
                Title: "%s"
                Status:%s""".formatted(getId(), title, statusType);
    }
}
