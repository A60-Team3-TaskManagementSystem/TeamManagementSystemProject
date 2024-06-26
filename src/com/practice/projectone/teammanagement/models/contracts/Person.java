package com.practice.projectone.teammanagement.models.contracts;

import com.practice.projectone.teammanagement.models.tasks.contracts.Task;

public interface Person extends ActivityAble, TaskAssignable, Nameable {
    void assignTask(Task task);

    void unassignTask(Task task);
}
