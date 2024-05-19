package com.practice.projectone.teammanagement.models.contracts;

import com.practice.projectone.teammanagement.models.tasks.contracts.Task;

import java.util.List;

public interface TaskAssignable {
    List<Task> getTasks();

    String viewTasks();
}
