package com.practice.projectone.teammanagement.commands.listing;

import com.practice.projectone.teammanagement.commands.contracts.Command;
import com.practice.projectone.teammanagement.core.TaskManagementSystemImpl;
import com.practice.projectone.teammanagement.core.contracts.TaskManagementSystemRepository;
import com.practice.projectone.teammanagement.exceptions.ElementNotFoundException;
import com.practice.projectone.teammanagement.models.tasks.BugImpl;
import com.practice.projectone.teammanagement.models.tasks.StoryImpl;
import com.practice.projectone.teammanagement.models.tasks.contracts.Bug;
import com.practice.projectone.teammanagement.models.tasks.contracts.SpecificTask;
import com.practice.projectone.teammanagement.models.tasks.contracts.Story;
import com.practice.projectone.teammanagement.models.tasks.contracts.Task;
import com.practice.projectone.teammanagement.models.tasks.enums.Status;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.practice.projectone.teammanagement.utils.Constants.*;

public class ListOnlyAssignedTasksCommandTests {
    private TaskManagementSystemRepository repository;
    private Command listAssignedTasks;

    private Story story;
    private Bug bug;

    @BeforeEach
    void setup() {
        repository = new TaskManagementSystemImpl();
        listAssignedTasks = new ListOnlyAssignedTasksCommand(repository);
        bug = new BugImpl(VALID_TITLE, VALID_DESCRIPTION,
                VALID_SPECIFIC_TASK_PRIORITY, VALID_SEVERITY, VALID_STEPS_LIST);
        story = new StoryImpl("asd" + VALID_TITLE, VALID_DESCRIPTION,
                VALID_SPECIFIC_TASK_PRIORITY, VALID_STORY_SIZE);
    }

    @Test
    public void execute_Should_ThrowException_When_NoAnyTaskFound() {
        Assertions.assertThrows(ElementNotFoundException.class,
                () -> listAssignedTasks.execute(List.of()));
    }

    @Test
    public void execute_Should_ThrowException_When_SortConditionIsInvalid() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> listAssignedTasks.execute(List.of("fake")));
    }

    @Test
    public void execute_Should_PrintAllAssignedTasks_When_NoParameters() {
        bug.changeAssignee(VALID_NAME);

        repository.addBug(bug);
        repository.addStory(story);

        String test = repository.getSpecificTasks()
                .stream()
                .filter(task -> !task.getAssignee().equals("Not assigned yet"))
                .map(SpecificTask::toString)
                .collect(Collectors.joining(System.lineSeparator()));

        Assertions.assertEquals(test, listAssignedTasks.execute(List.of()));
    }

    @Test
    public void execute_Should_ReturnSortOrOneFilterTasks_When_TwoParameter() {
        repository.addBug(bug);
        bug.changeAssignee(VALID_NAME);

        repository.addStory(story);
        story.changeAssignee("asd" + VALID_NAME);

        String filterStatus = Status.ACTIVE.toString();

        String testFilterStatus = repository.getSpecificTasks().stream()
                .filter(task1 -> task1.getStatus().toString().equals(filterStatus))
                .map(Task::toString).collect(Collectors.joining(System.lineSeparator()));

        String testFilterAssignee = repository.getSpecificTasks().stream()
                .filter(task1 -> task1.getAssignee().contains(VALID_NAME))
                .map(Task::toString).collect(Collectors.joining(System.lineSeparator()));

        String testSort = repository.getSpecificTasks().stream()
                .sorted(Comparator.comparing(SpecificTask::getName))
                .map(SpecificTask::toString).collect(Collectors.joining(System.lineSeparator()));

        Assertions.assertAll(
                () -> Assertions.assertEquals(testFilterStatus,
                        listAssignedTasks.execute(List.of("true", filterStatus))),
                () -> Assertions.assertEquals(testFilterAssignee,
                        listAssignedTasks.execute(List.of("false", VALID_NAME))),
                () -> Assertions.assertEquals(testSort,
                        listAssignedTasks.execute(List.of("true", VALID_NAME)))
        );
    }

    @Test
    public void execute_Should_ReturnSortOrTwoFilterTasks_When_ThreeParameter() {
        Story newStory = new StoryImpl(VALID_TITLE + "asd", VALID_DESCRIPTION,
                VALID_SPECIFIC_TASK_PRIORITY, VALID_STORY_SIZE);
        newStory.changeAssignee("asd");
        repository.addStory(newStory);

        repository.addBug(bug);
        bug.changeAssignee(VALID_NAME);

        repository.addStory(story);
        story.changeAssignee("asd" + VALID_NAME);

        String filterStatus = Status.NOT_DONE.toString();
        String filterAssignee = "asd";

        String testFilterStatusAndAssignee = repository.getSpecificTasks()
                .stream()
                .filter(task -> task.getStatus().toString().equals(filterStatus))
                .filter(task -> task.getAssignee().contains(filterAssignee))
                .map(SpecificTask::toString)
                .collect(Collectors.joining(System.lineSeparator()));

        String testSort = repository.getSpecificTasks().stream()
                .filter(task -> task.getStatus().toString().equals(filterStatus))
                .filter(task -> task.getAssignee().contains(filterAssignee))
                .sorted(Comparator.comparing(Task::getName))
                .map(SpecificTask::toString)
                .collect(Collectors.joining(System.lineSeparator()));

        Assertions.assertAll(
                () -> Assertions.assertEquals(testFilterStatusAndAssignee,
                        listAssignedTasks.execute(List.of("false", filterStatus, filterAssignee))),
                () -> Assertions.assertThrows(IllegalArgumentException.class,
                        () -> listAssignedTasks.execute(List.of("false", VALID_NAME, filterAssignee))),
                () -> Assertions.assertEquals(testSort,
                        listAssignedTasks.execute((List.of("true", filterStatus, filterAssignee))))
        );
    }
}
