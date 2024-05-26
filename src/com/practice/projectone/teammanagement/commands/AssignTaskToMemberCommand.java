package com.practice.projectone.teammanagement.commands;

import com.practice.projectone.teammanagement.core.contracts.TaskManagementSystemRepository;
import com.practice.projectone.teammanagement.exceptions.InvalidUserInputException;
import com.practice.projectone.teammanagement.models.contracts.Person;
import com.practice.projectone.teammanagement.models.tasks.contracts.Bug;
import com.practice.projectone.teammanagement.models.tasks.contracts.SpecificTask;
import com.practice.projectone.teammanagement.models.tasks.contracts.Story;
import com.practice.projectone.teammanagement.utils.ParsingHelpers;
import com.practice.projectone.teammanagement.utils.ValidationHelpers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AssignTaskToMemberCommand extends BaseCommand {
    private static final int EXPECTED_PARAMETERS_COUNT = 2;
    private static final String INVALID_TASK_ID = "Invalid value for taskID. Should be a number.";
    private static final String TASK_ASSIGNED = "Task with ID%d assigned to %s";
    private static final String TASK_NOT_ASSIGNABLE = "Task can not be assigned";
    private static final String INVALID_CONDITIONS = "No task satisfy given condition";
    private static final String INVALID_TASK = "Feedback";

    public AssignTaskToMemberCommand(TaskManagementSystemRepository taskManagementSystemRepository) {
        super(taskManagementSystemRepository);
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_PARAMETERS_COUNT);

        String memberName = parameters.get(0);
        Person person = getTMSRepository().findPersonByName(memberName);

        if (parameters.size() == 4) {
            String taskType = parameters.get(1);

            if (taskType.equals(INVALID_TASK)) {
                throw new InvalidUserInputException(TASK_NOT_ASSIGNABLE);
            }

            String taskAttribute = parameters.get(2);
            String attributeCondition = parameters.get(3);

            return assignSpecificTasks(person, taskType, taskAttribute, attributeCondition);
        }

        int taskID = ParsingHelpers.tryParseInt(parameters.get(1), INVALID_TASK_ID);

        return assignTask(taskID, person);
    }

    private String assignTask(int taskID, Person person) {
        SpecificTask task = getTMSRepository().findSpecificTask(taskID);
        return assign(task, person);
    }

    private String assignSpecificTasks(Person person, String taskType, String taskAttribute, String attributeCondition) {
        switch (taskAttribute) {
            case "Status":
            case "Priority":
                List<SpecificTask> specificTasks = getTMSRepository().getSpecificTasks();
                return assignStatusOrPriorityTasks(specificTasks, taskType, attributeCondition, person);
            case "Size":
                List<Story> stories = getTMSRepository().getStories();
                return assignSizeTasks(stories, taskType, attributeCondition, person);
            case "Severity":
                List<Bug> bugs = getTMSRepository().getBugs();
                return assignSeverityTasks(bugs, taskType, attributeCondition, person);
            default:
                throw new IllegalArgumentException(INVALID_CONDITIONS);
        }
    }

    private String assignStatusOrPriorityTasks(List<SpecificTask> tasks, String taskType, String condition, Person person) {
        List<SpecificTask> filteredTasks = tasks.stream()
                .filter(task -> task.getStatus().getTaskType().equals(taskType))
                .filter(task -> task.getStatus().toString().equals(condition) || task.getPriority().toString().equals(condition))
                .collect(Collectors.toList());

        return getResult(filteredTasks, person);
    }

    private String assignSizeTasks(List<Story> stories, String taskType, String size, Person person) {
        List<Story> filteredTasks = stories.stream()
                .filter(task -> task.getStatus().getTaskType().equals(taskType))
                .filter(task -> task.getSize().toString().equals(size))
                .collect(Collectors.toList());

        return getResult(filteredTasks, person);
    }

    private String assignSeverityTasks(List<Bug> bugs, String taskType, String severity, Person person) {
        List<Bug> filteredTasks = bugs.stream()
                .filter(task -> task.getStatus().getTaskType().equals(taskType))
                .filter(task -> task.getSeverity().toString().equals(severity))
                .collect(Collectors.toList());

        return getResult(filteredTasks, person);
    }

    private <T extends SpecificTask> String getResult(List<T> filteredTasks, Person person) {
        if (filteredTasks.isEmpty()) {
            throw new InvalidUserInputException(INVALID_CONDITIONS);
        }

        List<String> result = new ArrayList<>();

        filteredTasks.forEach(task -> {
            if (task.getAssignee() != null) {
                Person taskAssignee = getTMSRepository().findPersonByName(task.getAssignee());
                taskAssignee.unassignTask(task);
            }

            result.add(assign(task, person));
        });

        return String.join(System.lineSeparator(), result).trim();
    }

    private String assign(SpecificTask task, Person person) {
        task.changeAssignee(person.getName());
        person.assignTask(task);

        return String.format(TASK_ASSIGNED, task.getId(), person.getName());
    }
}
