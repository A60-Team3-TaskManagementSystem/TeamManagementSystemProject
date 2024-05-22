package com.practice.projectone.teammanagement.models.tasks;

import com.practice.projectone.teammanagement.exceptions.InvalidUserInputException;
import com.practice.projectone.teammanagement.models.EventLogImpl;
import com.practice.projectone.teammanagement.models.tasks.contracts.SpecificTask;
import com.practice.projectone.teammanagement.models.tasks.enums.Priority;
import com.practice.projectone.teammanagement.models.tasks.enums.Status;

public abstract class SpecificTaskImpl extends TaskImpl implements SpecificTask {

    private static final String PRIORITY_CHANGED = "The priority of item with ID %d switched from %s to %s";
    private static final String PRIORITY_SAME_ERR = "Can't change, priority already at %s";
    public static final String ASSIGNEE_TRANSFERRED = "Task assignee was transferred";
    public static final String NEW_ASSIGNEE_ARRIVED = "Task given to %s";
    public static final String ASSIGNEE_REPLACED = "Task transferred from %s to %s";
    public static final String TASK_NOT_YET_ASSIGNED = "Task not yet assigned";
    public static final String SAME_ASSIGNE_TASK = "Task %s already assigned to %s";
    private Priority priority;
    private String assigneeName;

    protected SpecificTaskImpl(String title, String description, Status status, Priority priority) {
        super(title, description, status);
        this.priority = priority;
    }


    @Override
    public String getAssignee() {
        return assigneeName == null ? "Not assigned yet" : assigneeName;
    }

    @Override
    public void changeAssignee(String assigneeName) {
        if (assigneeName == null) {
            unassign();
        } else {
            assign(assigneeName);
        }
    }

    @Override
    public Priority getPriority() {
        return priority;
    }

    @Override
    public void changePriority(Priority newPriority) {
        Priority oldPriority = getPriority();

        if (newPriority.equals(oldPriority)) {
            throw new InvalidUserInputException(String.format(PRIORITY_SAME_ERR, newPriority));
        }

        setPriority(newPriority);
        addEventToHistory(new EventLogImpl(String.format(PRIORITY_CHANGED, getId(), oldPriority, newPriority)));
    }

    @Override
    public String toString() {
        return String.format("%s  #Priority: %s%n", super.toString(), priority);
    }

    private void unassign() {
        if (!assigneeIsValid()) {
            throw new IllegalArgumentException(TASK_NOT_YET_ASSIGNED);
        }

        setAssignee(null);
        addEventToHistory(new EventLogImpl(ASSIGNEE_TRANSFERRED));
    }

    private void assign(String assigneeName) {
        if (assigneeIsValid() && this.assigneeName.equals(assigneeName)) {
            throw new IllegalArgumentException(String.format(SAME_ASSIGNE_TASK, getName(), assigneeName));
        }

        String eventTitle = assigneeIsValid() ? String.format(ASSIGNEE_REPLACED, getAssignee(), assigneeName)
                : String.format(NEW_ASSIGNEE_ARRIVED, assigneeName);

        setAssignee(assigneeName);
        addEventToHistory(new EventLogImpl(eventTitle));
    }

    private void setAssignee(String assigneeName) {
        this.assigneeName = assigneeName;
    }

    private void setPriority(Priority priority) {
        this.priority = priority;
    }

    private boolean assigneeIsValid() {
        return this.assigneeName != null;
    }
}
