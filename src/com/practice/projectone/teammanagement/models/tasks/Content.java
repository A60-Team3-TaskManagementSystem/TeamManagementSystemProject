package com.practice.projectone.teammanagement.models.tasks;

import com.practice.projectone.teammanagement.exceptions.InvalidUserInputException;
import com.practice.projectone.teammanagement.models.EventLogImpl;
import com.practice.projectone.teammanagement.models.tasks.contracts.AssigneeAble;
import com.practice.projectone.teammanagement.models.tasks.contracts.PrioritizeAble;
import com.practice.projectone.teammanagement.models.tasks.enums.Priority;
import com.practice.projectone.teammanagement.models.tasks.enums.Status;

public abstract class Content extends TaskImpl implements AssigneeAble, PrioritizeAble {

    private static final String PRIORITY_CHANGED = "The priority of item with ID %d switched from %s to %s";
    private static final String PRIORITY_SAME_ERR = "Can't change, priority already at %s";
    public static final String TASK_ALREADY_ASSIGNED = "Task is already assigned to %s";
    public static final String ASSIGNEE_TRANSFERRED = "Task assignee was transferred";
    public static final String NEW_ASSIGNEE_ARRIVED = "Task given to %s";
    private Priority priority;
    private String assigneeName;

    protected Content(String title, String description, Status status, Priority priority) {
        super(title, description, status);
        this.priority = priority;
    }


    @Override
    public String getAssignee() {
        if (!assigneeIsValid()) {
            throw new IllegalArgumentException("Task not yet assigned");
        }
        return assigneeName;
    }

    @Override
    public void changeAssignee(String assigneeName) {
        setAssignee(assigneeName);
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

    private void setAssignee(String assigneeName) {
        if (assigneeName != null) {
            if (assigneeIsValid()) {
                throw new IllegalArgumentException(String.format(TASK_ALREADY_ASSIGNED, this.assigneeName));
            }
        }

        String eventTitle;
        if (assigneeName == null) {
            eventTitle = ASSIGNEE_TRANSFERRED;
        } else {
            eventTitle = String.format(NEW_ASSIGNEE_ARRIVED, assigneeName);
        }

        this.assigneeName = assigneeName;

        addEventToHistory(new EventLogImpl(eventTitle));
    }

    private void setPriority(Priority priority) {
        this.priority = priority;
    }

    private boolean assigneeIsValid() {
        return this.assigneeName != null;
    }
}
