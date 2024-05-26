package com.practice.projectone.teammanagement.commands;

import com.practice.projectone.teammanagement.commands.contracts.Command;
import com.practice.projectone.teammanagement.core.TaskManagementSystemImpl;
import com.practice.projectone.teammanagement.core.contracts.TaskManagementSystemRepository;
import com.practice.projectone.teammanagement.exceptions.ElementNotFoundException;
import com.practice.projectone.teammanagement.models.contracts.Person;
import com.practice.projectone.teammanagement.models.tasks.contracts.Story;
import com.practice.projectone.teammanagement.utils.TestUtilities;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.practice.projectone.teammanagement.utils.Constants.*;

public class UnassignTaskToMemberCommandTests {

    private static final int EXPECTED_PARAMETERS_COUNT = 2;
    private TaskManagementSystemRepository repository;
    private Command unassignTaskToMember;

    @BeforeEach
    void beforeEach() {
        repository = new TaskManagementSystemImpl();
        unassignTaskToMember = new UnassignTaskToMemberCommand(repository);
    }

    @Test
    public void execute_Should_ThrowException_When_ArgumentCountDifferentThanExpected() {
        List<String> params = TestUtilities.getList(EXPECTED_PARAMETERS_COUNT - 1);
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> unassignTaskToMember.execute(params)
        );
    }

    @Test
    public void execute_Should_ThroughException_When_IDNotNumber() {
        List<String> notValidParams = List.of("NotValidID", VALID_NAME);

        Assertions.assertThrows(IllegalArgumentException.class, () -> unassignTaskToMember.execute(notValidParams));
    }

    @Test
    public void execute_Should_ThrowException_When_TaskDoesNotExists() {
        List<String> validParams = List.of(String.valueOf(1), VALID_NAME);

        Assertions.assertThrows(ElementNotFoundException.class, () -> unassignTaskToMember.execute(validParams));
    }

    @Test
    public void execute_Should_ThrowException_When_PersonDoesNotExists() {
        Story story = repository.createStory(VALID_TITLE, VALID_DESCRIPTION, VALID_SPECIFIC_TASK_PRIORITY, VALID_STORY_SIZE);
        repository.addStory(story);

        List<String> validParams = List.of(String.valueOf(story.getId()), VALID_NAME);

        Assertions.assertThrows(ElementNotFoundException.class, () -> unassignTaskToMember.execute(validParams));
    }

    @Test
    public void execute_Should_UnAssignTask_When_InputIsValid() {
        Story story = repository.createStory(VALID_TITLE, VALID_DESCRIPTION, VALID_SPECIFIC_TASK_PRIORITY, VALID_STORY_SIZE);
        repository.addStory(story);
        story.changeAssignee(VALID_NAME);

        Person person = repository.createPerson(VALID_NAME);
        repository.addPerson(person);
        person.assignTask(story);

        List<String> validParams = List.of(String.valueOf(story.getId()), VALID_NAME);
        unassignTaskToMember.execute(validParams);

        Assertions.assertAll(
                () -> Assertions.assertEquals("Not assigned yet",story.getAssignee()),
                () -> Assertions.assertEquals(0, person.getTasks().size())
        );
    }
}
