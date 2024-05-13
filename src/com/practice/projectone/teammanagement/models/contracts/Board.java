package com.practice.projectone.teammanagement.models.contracts;

import java.util.List;

public interface Board extends ActivityAble{
    String getBoardName();
    List<Task> getTasks();
    void addTask(Task task);
}
