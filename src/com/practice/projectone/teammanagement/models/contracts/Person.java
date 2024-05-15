package com.practice.projectone.teammanagement.models.contracts;

import java.util.List;

public interface Person extends ActivityAble, Nameable{
    List<Task> getTasks();
    void assignTask(Task task);
    void removeTask(Task task);
}
