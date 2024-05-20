package com.practice.projectone.teammanagement.models.contracts;

import com.practice.projectone.teammanagement.models.tasks.contracts.Task;

import java.util.List;

public interface TaskAssignable extends Nameable{
    List<Task> getTasks();

    default String viewTasks() {
        StringBuilder sb = new StringBuilder();

        String className = this.getClass()
                .getSimpleName()
                .substring(0, this.getClass().getSimpleName().length() - 4)
                .toUpperCase();

        sb.append(String.format("****%s %s****", className, getName())).append(System.lineSeparator());
        sb.append("--ASSIGNED TASKS--").append(System.lineSeparator());

        List<Task> tasks = getTasks();

        if (tasks.isEmpty()) {
            sb.append("--NO ASSIGNED TASKS--").append(System.lineSeparator());
        } else {
            tasks.forEach(task -> sb.append(task).append(System.lineSeparator()));
            sb.append("--ASSIGNED TASKS--").append(System.lineSeparator());
        }

        return sb.toString().trim();
    }
}
