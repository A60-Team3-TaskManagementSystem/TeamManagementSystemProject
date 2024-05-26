package com.practice.projectone.teammanagement.commands.listing;

import com.practice.projectone.teammanagement.commands.contracts.Command;
import com.practice.projectone.teammanagement.core.TaskManagementSystemImpl;
import com.practice.projectone.teammanagement.core.contracts.TaskManagementSystemRepository;
import com.practice.projectone.teammanagement.exceptions.ElementNotFoundException;
import com.practice.projectone.teammanagement.exceptions.InvalidUserInputException;
import com.practice.projectone.teammanagement.models.tasks.FeedbackImpl;
import com.practice.projectone.teammanagement.models.tasks.StoryImpl;
import com.practice.projectone.teammanagement.models.tasks.contracts.Feedback;
import com.practice.projectone.teammanagement.models.tasks.contracts.Story;
import com.practice.projectone.teammanagement.models.tasks.contracts.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.practice.projectone.teammanagement.utils.Constants.*;

public class ListTasksCommandTests {
    private TaskManagementSystemRepository repository;
    private Command listTaskCommand;

    private Story otherTask;
    private Feedback task;

    @BeforeEach
    void setup() {
        repository = new TaskManagementSystemImpl();
        listTaskCommand = new ListTasksCommand(repository);
        task = new FeedbackImpl("asd" + VALID_TITLE, VALID_DESCRIPTION,
                VALID_RATING);
        otherTask = new StoryImpl(VALID_TITLE, VALID_DESCRIPTION,
                VALID_SPECIFIC_TASK_PRIORITY, VALID_STORY_SIZE);
    }

    @Test
    public void execute_Should_ThrowException_When_NoAnyTaskFound() {
        Assertions.assertThrows(ElementNotFoundException.class,
                () -> listTaskCommand.execute(List.of()));
    }

    @Test
    public void execute_Should_ThrowException_When_ConditionInvalid() {
        Assertions.assertThrows(InvalidUserInputException.class,
                () -> listTaskCommand.execute(List.of(VALID_NAME, "test")));
    }

    @Test
    public void execute_Should_ReturnAllTasks_When_NoParameters() {
        repository.addFeedback(task);
        repository.addStory(otherTask);

        String test = repository.getTasks().stream()
                .map(Task::toString).collect(Collectors.joining(System.lineSeparator()));

        Assertions.assertEquals(test, listTaskCommand.execute(List.of()));
    }

    @Test
    public void execute_Should_ReturnSortedAndFilteredTasks_When_OneParameter() {
        repository.addFeedback(task);
        repository.addStory(otherTask);
        String filter = VALID_TITLE;

        String test = repository.getTasks().stream()
                .filter(task1 -> task1.getName().contains(filter))
                .sorted(Comparator.comparing(Task::getName))
                .map(Task::toString).collect(Collectors.joining(System.lineSeparator()));

        Assertions.assertEquals(test, listTaskCommand.execute(List.of(filter)));
    }

    @Test
    public void execute_Should_ReturnSortOrFilterTasks_When_TwoParameter() {
        repository.addFeedback(task);
        repository.addStory(otherTask);
        String filter = "asd";

        String testFilter = repository.getTasks().stream()
                .filter(task1 -> task1.getName().contains(filter))
                .map(Task::toString).collect(Collectors.joining(System.lineSeparator()));

        String testSort = repository.getTasks().stream()
                .sorted(Comparator.comparing(Task::getName))
                .map(Task::toString).collect(Collectors.joining(System.lineSeparator()));

        Assertions.assertAll(
                () -> Assertions.assertEquals(testFilter, listTaskCommand.execute(List.of(filter, "filter"))),
                () -> Assertions.assertEquals(testSort, listTaskCommand.execute(List.of(filter, "sort")))
        );
    }
}
