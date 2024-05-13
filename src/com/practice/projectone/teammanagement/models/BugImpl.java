package com.practice.projectone.teammanagement.models;

import com.practice.projectone.teammanagement.exceptions.InvalidUserInputException;
import com.practice.projectone.teammanagement.models.contracts.Bug;
import com.practice.projectone.teammanagement.models.contracts.Member;
import com.practice.projectone.teammanagement.models.enums.Priority;
import com.practice.projectone.teammanagement.models.enums.Severity;
import com.practice.projectone.teammanagement.models.enums.StatusType;

import java.util.ArrayList;
import java.util.List;

public class BugImpl extends Content implements Bug {

    private static final StatusType INITIAL_STATUS = StatusType.ACTIVE;
    private final List<String> steps;
    private Severity severity;

    public BugImpl (int id, String title, String description, Priority priority, Severity severity, Member assignee){
        super(id, title, description, INITIAL_STATUS, priority, assignee);
        this.severity = severity;
        this.steps = new ArrayList<>();
    }

    @Override
    public List<String> getSteps() {
        return new ArrayList<>(steps);
    }

    @Override
    public Severity getSeverity() {
        return severity;
    }

    @Override
    public void addStep(String step) {
        steps.add(step);
    }

    @Override
    public void changeSeverity(Severity severity) {
        if (severity.equals(getSeverity())) {
            throw new InvalidUserInputException(String.format("Can't change, severity already at %s", severity));
        }

        this.severity = severity;
        addEventToHistory(new EventLogImpl(String.format("Bug severity changed to %s", severity)));
    }


    @Override
    public void changePriority(Priority priority) {
        if (priority.equals(getPriority())) {
            throw new InvalidUserInputException(String.format("Can't change, priority already at %s", priority));
        }

        setPriority(priority);
        addEventToHistory(new EventLogImpl(String.format("Task priority changed to %s", priority)));
    }
}
