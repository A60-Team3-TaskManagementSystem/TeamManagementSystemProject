package com.practice.projectone.teammanagement.commands;

import com.practice.projectone.teammanagement.commands.contracts.Command;
import com.practice.projectone.teammanagement.core.TaskManagementSystemImpl;
import com.practice.projectone.teammanagement.core.contracts.TaskManagementSystemRepository;
import com.practice.projectone.teammanagement.exceptions.ElementNotFoundException;
import com.practice.projectone.teammanagement.exceptions.InvalidUserInputException;
import com.practice.projectone.teammanagement.models.contracts.Person;
import com.practice.projectone.teammanagement.models.tasks.contracts.Bug;
import com.practice.projectone.teammanagement.models.tasks.contracts.Story;
import com.practice.projectone.teammanagement.utils.TestUtilities;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.practice.projectone.teammanagement.utils.Constants.*;

public class AssignTaskToMemberCommandTests {
    private static final int EXPECTED_PARAMETERS_COUNT = 2;
    private TaskManagementSystemRepository repository;
    private Command assignTaskToMemberCommand;

    @BeforeEach
    void beforeEach() {
        repository = new TaskManagementSystemImpl();
        assignTaskToMemberCommand = new AssignTaskToMemberCommand(repository);
    }

    @Test
    public void execute_Should_ThrowException_When_ArgumentCountDifferentThanExpected() {
        List<String> params = TestUtilities.getList(EXPECTED_PARAMETERS_COUNT - 1);
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> assignTaskToMemberCommand.execute(params)
        );
    }

    @Test
    public void execute_Should_ThroughException_When_IDNotNumber() {
        List<String> notValidParams = List.of(VALID_NAME, "NotValidID");
        Person person = repository.createPerson(VALID_NAME);
        repository.addPerson(person);

        Assertions.assertThrows(IllegalArgumentException.class, () -> assignTaskToMemberCommand.execute(notValidParams));
    }

    @Test
    public void execute_Should_ThrowException_When_TaskIsNotAssignable() {
        List<String> notValidParams = List.of(VALID_NAME, "Feedback",
                "Priority", String.valueOf(VALID_SPECIFIC_TASK_PRIORITY));
        Person person = repository.createPerson(VALID_NAME);
        repository.addPerson(person);
        Assertions.assertThrows(InvalidUserInputException.class, () -> assignTaskToMemberCommand.execute(notValidParams));
    }

    @Test
    public void execute_Should_ThrowException_When_PersonDoesNotExists() {
        List<String> notValidParams = List.of(VALID_NAME, "Bug",
                "Priority", String.valueOf(VALID_SPECIFIC_TASK_PRIORITY));

        Assertions.assertThrows(ElementNotFoundException.class, () -> assignTaskToMemberCommand.execute(notValidParams));
    }

    @Test
    public void execute_Should_ThrowException_When_TaskAttributeIsInvalid() {
        List<String> notValidParams = List.of(VALID_NAME, "Bug",
                "InvalidAttribute", String.valueOf(VALID_SPECIFIC_TASK_PRIORITY));

        Person person = repository.createPerson(VALID_NAME);
        repository.addPerson(person);

        Assertions.assertThrows(IllegalArgumentException.class, () -> assignTaskToMemberCommand.execute(notValidParams));
    }

    @Test
    public void execute_Should_ThrowException_When_AttributeConditionIsInvalid() {
        List<String> notValidBug = List.of(VALID_NAME, "Bug",
                "Status", String.valueOf(VALID_SPECIFIC_TASK_PRIORITY));
        List<String> notValidStory = List.of(VALID_NAME, "Story",
                "Size", String.valueOf(VALID_SPECIFIC_TASK_PRIORITY));


        Person person = repository.createPerson(VALID_NAME);
        repository.addPerson(person);

        Assertions.assertAll(
                () -> Assertions.assertThrows(InvalidUserInputException.class,
                        () -> assignTaskToMemberCommand.execute(notValidBug)),
                () -> Assertions.assertThrows(InvalidUserInputException.class,
                        () -> assignTaskToMemberCommand.execute(notValidStory))
        );

    }

    @Test
    public void execute_Should_AssignSingleTask_When_InputIsValid() {
        Bug bug = repository.createBug(VALID_TITLE, VALID_DESCRIPTION,
                VALID_SPECIFIC_TASK_PRIORITY, VALID_SEVERITY, VALID_STEPS_LIST);
        repository.addBug(bug);

        Person person = repository.createPerson(VALID_NAME);
        repository.addPerson(person);

        List<String> validParams = List.of(VALID_NAME, String.valueOf(bug.getId()));

        String execute = assignTaskToMemberCommand.execute(validParams);

        Assertions.assertAll(
                () -> Assertions.assertEquals(bug.getAssignee(), person.getName()),
                () -> Assertions.assertEquals(1, person.getTasks().size()),
                () -> Assertions.assertEquals(1, execute.split(System.lineSeparator()).length)
        );

    }

    @Test
    public void execute_Should_AssignAllTasks_When_InputIsValid() {
        List<String> validParams = List.of(VALID_NAME, "Bug",
                "Severity", String.valueOf(VALID_SEVERITY));

        Person person = repository.createPerson(VALID_NAME);
        Person otherPerson = repository.createPerson("Pesho");
        repository.addPerson(person);
        repository.addPerson(otherPerson);

        Bug bug = repository.createBug(VALID_TITLE, VALID_DESCRIPTION,
                VALID_SPECIFIC_TASK_PRIORITY, VALID_SEVERITY, VALID_STEPS_LIST);
        Story story = repository.createStory(VALID_TITLE, VALID_DESCRIPTION,
                VALID_SPECIFIC_TASK_PRIORITY, VALID_STORY_SIZE);
        repository.addBug(bug);
        repository.addStory(story);

        otherPerson.assignTask(bug);
        otherPerson.assignTask(story);
        bug.changeAssignee(otherPerson.getName());
        story.changeAssignee(otherPerson.getName());

        Assertions.assertAll(
                () -> Assertions.assertEquals(2, otherPerson.getTasks().size()),
                () -> Assertions.assertEquals(0, person.getTasks().size()),
                () -> Assertions.assertEquals(bug.getAssignee(), otherPerson.getName()),
                () -> Assertions.assertEquals(story.getAssignee(), otherPerson.getName())
        );

        String execute = assignTaskToMemberCommand.execute(validParams);

        Assertions.assertAll(
                () -> Assertions.assertEquals(1, otherPerson.getTasks().size()),
                () -> Assertions.assertEquals(1, person.getTasks().size()),
                () -> Assertions.assertEquals(bug.getAssignee(), person.getName()),
                () -> Assertions.assertEquals(story.getAssignee(), otherPerson.getName()),
                () -> Assertions.assertEquals(1, execute.split(System.lineSeparator()).length)
        );
    }
}
