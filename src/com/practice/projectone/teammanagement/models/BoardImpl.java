package com.practice.projectone.teammanagement.models;

import com.practice.projectone.teammanagement.models.contracts.Board;
import com.practice.projectone.teammanagement.models.contracts.EventLog;
import com.practice.projectone.teammanagement.models.contracts.Task;
import com.practice.projectone.teammanagement.utils.ValidationHelpers;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

public class BoardImpl implements Board {
    public static final int NAME_LEN_MIN = 5;
    public static final int NAME_LEN_MAX = 10;
    private static final String NAME_LEN_ERR = format(
            "Board name must be between %s and %s characters long!",
            NAME_LEN_MIN,
            NAME_LEN_MAX);
    private String boardName;
    private final List<Task> tasks;
    private final List<EventLog> eventLogs;

    public BoardImpl(String boardName) {
        setBoardName(boardName);
        tasks = new ArrayList<>();
        eventLogs = new ArrayList<>();

        addEventToHistory(new EventLogImpl(String.format("Employee %s joined the company", boardName)));
    }

    private void setBoardName(String boardName) {
        ValidationHelpers.validateStringLength(boardName,NAME_LEN_MIN,NAME_LEN_MAX,NAME_LEN_ERR);
        this.boardName = boardName;
    }

    @Override
    public String getBoardName() {
        return boardName;
    }

    @Override
    public List<Task> getTasks() {
        return new ArrayList<>(tasks);
    }

    @Override
    public List<EventLog> getActivityHistory() {
        return new ArrayList<>(eventLogs);
    }

    @Override
    public void addTask(Task task) {
        tasks.add(task);

        addEventToHistory(new EventLogImpl(String.format("New item %s added to %s board", task, boardName)));
    }

    private void addEventToHistory(EventLog eventLog) {
        eventLogs.add(eventLog);
    }

    @Override
    public String toString() {
        return boardName;
    }
}
