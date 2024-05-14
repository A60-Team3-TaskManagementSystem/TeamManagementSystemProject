package com.practice.projectone.teammanagement.models.contracts;

import java.util.List;

public interface Person extends ActivityAble{
    String getMemberName();
    List<Task> getTasks();
    void addTask(Task task);
}
