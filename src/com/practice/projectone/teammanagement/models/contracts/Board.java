package com.practice.projectone.teammanagement.models.contracts;

import java.util.List;

public interface Board extends ActivityAble, Nameable{
    List<Task> getTasks();
    void addTask(Task task);

    void removeTask(Task task);
}
