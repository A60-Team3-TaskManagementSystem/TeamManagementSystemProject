package com.practice.projectone.teammanagement.commands;

import com.practice.projectone.teammanagement.core.contracts.TeamRepository;
import com.practice.projectone.teammanagement.exceptions.InvalidUserInputException;
import com.practice.projectone.teammanagement.models.contracts.Person;
import com.practice.projectone.teammanagement.models.tasks.contracts.*;
import com.practice.projectone.teammanagement.models.tasks.enums.Priority;
import com.practice.projectone.teammanagement.models.tasks.enums.Severity;
import com.practice.projectone.teammanagement.models.tasks.enums.Size;
import com.practice.projectone.teammanagement.models.tasks.enums.Status;
import com.practice.projectone.teammanagement.utils.ParsingHelpers;
import com.practice.projectone.teammanagement.utils.ValidationHelpers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AssignTaskToMemberCommand extends BaseCommand {
    private static final int EXPECTED_PARAMETERS_COUNT = 2;
    private static final String INVALID_TASK_ID = "Invalid value for taskID. Should be a number.";
    public static final String TASK_ASSIGNED = "Task with ID%d assigned to %s";
    public static final String TASK_NOT_ASSIGNABLE = "Task can not be assigned";
    public static final String INVALID_CONDITIONS = "No task satisfy given condition";
    public static final String INVALID_TASK = "Feedback";

    public AssignTaskToMemberCommand(TeamRepository teamRepository) {
        super(teamRepository);
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_PARAMETERS_COUNT);

        String memberName = parameters.get(0);
        int taskID = ParsingHelpers.tryParseInt(parameters.get(1), INVALID_TASK_ID);

        if (parameters.size() == 4) {
            String taskType = parameters.get(1);

            if (taskType.equals(INVALID_TASK)) {
                throw new InvalidUserInputException(TASK_NOT_ASSIGNABLE);
            }

            String taskAttribute = parameters.get(2);
            String attributeCondition = parameters.get(3);

            return assignSpecificTasks(memberName, taskType, taskAttribute, attributeCondition);
        }

        return assignTask(taskID, memberName);
    }

    private String assignTask(int taskID, String memberName) {
        SpecificTask task = getTeamRepository().findSpecificTask(taskID);
        Person person = getTeamRepository().findPersonByName(memberName);

        return assign(task, person);
    }

    private String assignSpecificTasks(String memberName, String taskType, String taskAttribute, String attributeCondition) {
        Person person = getTeamRepository().findPersonByName(memberName);
        String result;

        switch (taskAttribute) {
            case "Status":
                List<SpecificTask> tasks = getTeamRepository().getSpecificTasks();
                Status status = ParsingHelpers.tryParseEnum(attributeCondition, Status.class);

                result = assignStatusTasks(tasks, taskType, status, person);
                break;
            case "Priority":
                List<SpecificTask> specificTasks = getTeamRepository().getSpecificTasks();
                Priority priority = ParsingHelpers.tryParseEnum(attributeCondition, Priority.class);

                result = assignPriorityTasks(specificTasks, taskType, priority, person);
                break;
            case "Size":
                List<Story> stories = getTeamRepository().getStories();
                Size size = ParsingHelpers.tryParseEnum(attributeCondition, Size.class);

                result = assignSizeTasks(stories, taskType, size, person);
                break;
            case "Severity":
                List<Bug> bugs = getTeamRepository().getBugs();
                Severity severity = ParsingHelpers.tryParseEnum(attributeCondition, Severity.class);

                result = assignSeverityTasks(bugs, taskType, severity, person);
                break;
            default:
                throw new IllegalArgumentException(INVALID_CONDITIONS);
        }

        return result;
    }

    private String assignStatusTasks(List<SpecificTask> tasks, String taskType, Status status, Person person) {
        List<SpecificTask> filteredTasks = tasks.stream()
                .filter(task -> task.getStatus().getTaskType().equals(taskType))
                .filter(task -> task.getStatus().equals(status))
                .collect(Collectors.toList());

        return getResult(filteredTasks, person);
    }

    private String assignPriorityTasks(List<SpecificTask> specificTasks, String taskType, Priority priority, Person person) {
        List<SpecificTask> filteredTasks = specificTasks.stream()
                .filter(task -> task.getStatus().getTaskType().equals(taskType))
                .filter(task -> task.getPriority().equals(priority))
                .collect(Collectors.toList());

        return getResult(filteredTasks, person);
    }

    private String assignSizeTasks(List<Story> stories, String taskType, Size size, Person person) {
        List<Story> filteredTasks = stories.stream()
                .filter(task -> task.getStatus().getTaskType().equals(taskType))
                .filter(task -> task.getSize().equals(size))
                .collect(Collectors.toList());

        return getResult(filteredTasks, person);
    }

    private String assignSeverityTasks(List<Bug> bugs, String taskType, Severity severity, Person person) {
        List<Bug> filteredTasks = bugs.stream()
                .filter(task -> task.getStatus().getTaskType().equals(taskType))
                .filter(task -> task.getSeverity().equals(severity))
                .collect(Collectors.toList());

        return getResult(filteredTasks, person);
    }

    private <T extends SpecificTask> String getResult(List<T> filteredTasks, Person person) {
        if (filteredTasks.isEmpty()) {
            return INVALID_CONDITIONS;
        }

        List<String> result = new ArrayList<>();
        filteredTasks.forEach(task -> {

            Person taskAssignee = getTeamRepository().findPersonByName(task.getAssignee());

            taskAssignee.unassignTask(task);
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
