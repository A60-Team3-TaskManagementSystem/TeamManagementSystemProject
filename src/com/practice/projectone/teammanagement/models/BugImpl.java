package com.practice.projectone.teammanagement.models;

import com.practice.projectone.teammanagement.models.contracts.Bug;
import com.practice.projectone.teammanagement.models.contracts.Member;
import com.practice.projectone.teammanagement.models.enums.Priority;
import com.practice.projectone.teammanagement.models.enums.Severity;
import com.practice.projectone.teammanagement.models.enums.StatusType;

import java.util.ArrayList;
import java.util.List;

public class BugImpl extends Content implements Bug {

    private final List<String> steps;
    private Severity severity;

    public BugImpl (int id, String title, String description, StatusType status, Priority priority, Severity severity, Member assignee){
        super(id, title, description, status, priority, assignee);
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
    protected void revertStatus() {

    }

    @Override
    protected void advanceStatus() {

    }
}
