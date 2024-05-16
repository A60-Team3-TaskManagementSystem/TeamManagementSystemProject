package com.practice.projectone.teammanagement.models.contracts;

import com.practice.projectone.teammanagement.models.tasks.contracts.Task;

import java.util.List;

public interface Person extends ActivityAble, Nameable{
    List<Task> getTasks();
    void assignTask(Task task);
    void unassignTask(Task task);
}
