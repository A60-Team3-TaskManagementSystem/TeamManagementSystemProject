package com.practice.projectone.teammanagement.models;

import com.practice.projectone.teammanagement.exceptions.DuplicateEntityException;
import com.practice.projectone.teammanagement.exceptions.ElementNotFoundException;
import com.practice.projectone.teammanagement.models.contracts.Board;
import com.practice.projectone.teammanagement.models.contracts.EventLog;
import com.practice.projectone.teammanagement.models.tasks.contracts.Task;
import com.practice.projectone.teammanagement.utils.ValidationHelpers;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.lang.String.format;

public class BoardImpl implements Board {
    public static final int NAME_LEN_MIN = 5;
    public static final int NAME_LEN_MAX = 10;
    private static final String NAME_LEN_ERR = format(
            "Board name must be between %s and %s characters long!",
            NAME_LEN_MIN,
            NAME_LEN_MAX);
    public static final String DUPLICATE_ENTRY = "Task already exists";
    public static final String TASK_NOT_FOUND = "Task ID%d not found in board %s";
    private String boardName;
    private final List<Task> tasks;
    private final List<EventLog> eventLogs;

    public BoardImpl(String boardName) {
        setBoardName(boardName);
        tasks = new ArrayList<>();
        eventLogs = new ArrayList<>();

        addEventToHistory(new EventLogImpl(String.format("Board %s created", boardName)));
    }

    private void setBoardName(String boardName) {
        ValidationHelpers.validateStringLength(boardName, NAME_LEN_MIN, NAME_LEN_MAX, NAME_LEN_ERR);
        this.boardName = boardName;
    }

    @Override
    public String getName() {
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
        if (tasks.contains(task)) {
            throw new DuplicateEntityException(DUPLICATE_ENTRY);
        }

        tasks.add(task);

        addEventToHistory(new EventLogImpl(String.format("New task %s added to board %s", task.getName(), boardName)));
    }

    @Override
    public void removeTask(Task task) {
        if (!tasks.remove(task)) {
            throw new ElementNotFoundException(String.format(TASK_NOT_FOUND, task.getId(), boardName));
        }

        addEventToHistory(new EventLogImpl((String.format("Task ID%d removed from board %s.", task.getId(), boardName))));
    }

    @Override
    public String toString() {
        return String.format("BOARD: %s, TOTAL TASKS: %d", boardName, getTasks().size());
    }

    private void addEventToHistory(EventLog eventLog) {
        eventLogs.add(eventLog);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoardImpl board = (BoardImpl) o;
        return boardName.equals(board.boardName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(boardName, tasks, eventLogs);
    }
}
