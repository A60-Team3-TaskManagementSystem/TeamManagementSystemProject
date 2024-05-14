package com.practice.projectone.teammanagement.models;

import com.practice.projectone.teammanagement.models.contracts.EventLog;
import com.practice.projectone.teammanagement.models.contracts.Person;
import com.practice.projectone.teammanagement.models.contracts.Task;
import com.practice.projectone.teammanagement.utils.ValidationHelpers;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

public class PersonImpl implements Person {

    public static final int NAME_LEN_MIN = 5;
    public static final int NAME_LEN_MAX = 15;
    private static final String NAME_LEN_ERR = format(
            "Employee name must be between %s and %s characters long!",
            NAME_LEN_MIN,
            NAME_LEN_MAX);
    private String memberName;
    private final List<Task> tasks;
    private final List<EventLog> eventLogs;

    public PersonImpl(String memberName) {
        setMemberName(memberName);
        tasks = new ArrayList<>();
        eventLogs = new ArrayList<>();

        addEventToHistory(new EventLogImpl(String.format("Employee %s joined the company", memberName)));
    }

    private void setMemberName(String memberName) {
        ValidationHelpers.validateStringLength(memberName, NAME_LEN_MIN, NAME_LEN_MAX, NAME_LEN_ERR);
        this.memberName = memberName;
    }

    @Override
    public String getName() {
        return memberName;
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
        addEventToHistory(new EventLogImpl(String.format("New item %s added to %s list", task, memberName)));
    }

    private void addEventToHistory(EventLog eventLog) {
        eventLogs.add(eventLog);
    }

    @Override
    public String toString() {
        return memberName;
    }
}
