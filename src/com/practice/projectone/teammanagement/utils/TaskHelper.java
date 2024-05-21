package com.practice.projectone.teammanagement.utils;

import com.practice.projectone.teammanagement.core.contracts.TaskManagementSystemRepository;
import com.practice.projectone.teammanagement.models.tasks.enums.Priority;
import com.practice.projectone.teammanagement.models.tasks.enums.Severity;
import com.practice.projectone.teammanagement.models.tasks.enums.Size;
import com.practice.projectone.teammanagement.models.tasks.enums.Status;

import java.util.List;

public class TaskHelper {
    private static final String INVALID_CONDITIONS = "No task satisfy given condition";


    public static List<?> getTasksByAttribute(String taskAttribute, TaskManagementSystemRepository taskManagementSystemRepository) {
        return switch (taskAttribute) {
            case "Status" -> taskManagementSystemRepository.getTasks();
            case "Assignee", "Priority" -> taskManagementSystemRepository.getSpecificTasks();
            case "Size" -> taskManagementSystemRepository.getStories();
            case "Severity" -> taskManagementSystemRepository.getBugs();
            case "Rating" -> taskManagementSystemRepository.getFeedbacks();
            default -> throw new IllegalArgumentException(INVALID_CONDITIONS);
        };
    }

    public static <E extends Enum<E>> E parseEnum(String attributeCondition, String taskAttribute) {
        return switch (taskAttribute) {
            case "Status" -> (E) ParsingHelpers.tryParseEnum(attributeCondition, Status.class);
            case "Priority" -> (E) ParsingHelpers.tryParseEnum(attributeCondition, Priority.class);
            case "Size" -> (E) ParsingHelpers.tryParseEnum(attributeCondition, Size.class);
            case "Severity" -> (E) ParsingHelpers.tryParseEnum(attributeCondition, Severity.class);
            default -> throw new IllegalArgumentException(INVALID_CONDITIONS);
        };
    }
}
