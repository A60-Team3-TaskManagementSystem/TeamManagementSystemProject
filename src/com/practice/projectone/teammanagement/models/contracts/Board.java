package com.practice.projectone.teammanagement.models.contracts;

import com.practice.projectone.teammanagement.models.tasks.contracts.Task;

public interface Board extends ActivityAble, TaskAssignable, Nameable {

    void addTask(Task task);

    void removeTask(Task task);
}
