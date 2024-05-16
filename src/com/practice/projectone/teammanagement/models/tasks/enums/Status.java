package com.practice.projectone.teammanagement.models.tasks.enums;

public enum Status {

    ACTIVE("Bug", "Active"),
    NOT_DONE("Story", "Not Done"),
    IN_PROGRESS("Story", "In Progress"),
    NEW("Feedback", "New"),
    UNSCHEDULED("Feedback", "Unscheduled"),
    SCHEDULED("Feedback", "Scheduled"),
    DONE("All", "Done");

    private final String taskType;
    private final String value;

    Status(String taskType, String value) {
        this.taskType = taskType;
        this.value = value;
    }

    public String getTaskType() {
        return taskType;
    }

    @Override
    public String toString() {
        return value;
    }
}
