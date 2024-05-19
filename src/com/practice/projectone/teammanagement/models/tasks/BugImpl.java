package com.practice.projectone.teammanagement.models.tasks;

import com.practice.projectone.teammanagement.exceptions.InvalidUserInputException;
import com.practice.projectone.teammanagement.models.EventLogImpl;
import com.practice.projectone.teammanagement.models.tasks.contracts.Bug;
import com.practice.projectone.teammanagement.models.tasks.enums.Priority;
import com.practice.projectone.teammanagement.models.tasks.enums.Severity;
import com.practice.projectone.teammanagement.models.tasks.enums.Status;

import java.util.ArrayList;
import java.util.List;

public class BugImpl extends SpecificTaskImpl implements Bug {
    private static final Status INITIAL_STATUS = Status.ACTIVE;
    private static final String SEVERITY_CHANGED = "The severity of item with ID %d switched from %s to %s";
    private static final String SEVERITY_SAME_ERR = "Can't change, severity already at %s";
    private static final String STEPS_EMPTY_ERR = "Please provide steps to reproduce the bug";
    private List<String> steps;
    private Severity severity;

    public BugImpl(String title, String description, Priority priority,
                   Severity severity, List<String> steps) {

        super(title, description, INITIAL_STATUS, priority);
        this.severity = severity;
        setSteps(steps);
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
    public void changeSeverity(Severity newSeverity) {
        Severity oldSeverity = getSeverity();

        if (newSeverity.equals(oldSeverity)) {
            throw new InvalidUserInputException(String.format(SEVERITY_SAME_ERR, newSeverity));
        }

        this.severity = newSeverity;
        addEventToHistory(new EventLogImpl(String.format(SEVERITY_CHANGED, getId(), oldSeverity, newSeverity)));
    }

    @Override
    public String toString() {
        return String.format("%s  #Severity: %s%n  #StepsToReproduce:%n    %s%n",
                super.toString(),
                severity,
                String.join(System.lineSeparator(), getSteps()));
    }

    @Override
    protected void validateStatus(Status status) {
        if (!status.getTaskType().equals("Bug") && !status.getTaskType().equals("All")) {
            throw new IllegalArgumentException("Please provide valid story status");
        }
    }

    @Override
    protected String getTaskType() {
        return getClass().getTypeName().substring(0, getClass().getSimpleName().length() - 4);
    }

    private void setSteps(List<String> steps) {
        if (steps.isEmpty()) {
            throw new IllegalArgumentException(STEPS_EMPTY_ERR);
        }

        this.steps = steps;
    }
}
