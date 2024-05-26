package com.practice.projectone.teammanagement.models.tasks;

import com.practice.projectone.teammanagement.exceptions.InvalidUserInputException;
import com.practice.projectone.teammanagement.models.tasks.contracts.Story;
import com.practice.projectone.teammanagement.models.tasks.enums.Priority;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.practice.projectone.teammanagement.utils.Constants.*;

public class SpecificTaskImplTests {

    public static final String NEW_ASSIGNEE_NAME = "Pesho";
    public static final String NOT_ASSIGNED_YET = "Not assigned yet";
    public static final String ALDEADY_ASSIGNED = "Gosho";
    private Story story;

    @BeforeEach
    void setup() {
        story = new StoryImpl(VALID_TITLE,
                VALID_DESCRIPTION,
                VALID_SPECIFIC_TASK_PRIORITY,
                VALID_STORY_SIZE);
    }

    @Test
    public void getAssignee_Should_ReturnNone_IfTaskIsNotYetAssigned() {
        Assertions.assertEquals(NOT_ASSIGNED_YET, story.getAssignee());
    }

    @Test
    public void changeAssignee_Should_ThrowException_When_UnAssigningNotYetAssignedTask() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> story.changeAssignee(null));
    }

    @Test
    public void changeAssignee_Should_ThrowException_When_AssigningSamePerson() {
        story.changeAssignee(NEW_ASSIGNEE_NAME);

        Assertions.assertThrows(IllegalArgumentException.class, () -> story.changeAssignee(NEW_ASSIGNEE_NAME));
    }

    @Test
    public void changeAssignee_Should_AssignNewPerson_When_AssigningNotYetAssignedTask() {
        story.changeAssignee(NEW_ASSIGNEE_NAME);

        Assertions.assertEquals(NEW_ASSIGNEE_NAME, story.getAssignee());
    }

    @Test
    public void changeAssignee_Should_UnAssignPerson_When_PassedNullValue() {
        story.changeAssignee(NEW_ASSIGNEE_NAME);
        story.changeAssignee(null);

        Assertions.assertEquals(NOT_ASSIGNED_YET, story.getAssignee());
    }

    @Test
    public void changeAssignee_Should_LogEvent_When_UnAssigningPerson() {
        story.changeAssignee(NEW_ASSIGNEE_NAME);
        story.changeAssignee(null);

        Assertions.assertEquals(3, story.getActivityHistory().size());
    }

    @Test
    public void changeAssignee_Should_LogEvent_When_AssigningNewPerson() {
        story.changeAssignee(NEW_ASSIGNEE_NAME);

        Assertions.assertEquals(2, story.getActivityHistory().size());
    }

    @Test
    public void changeAssignee_Should_LogEvent_When_ChangingAssignee() {
        story.changeAssignee(ALDEADY_ASSIGNED);
        story.changeAssignee(NEW_ASSIGNEE_NAME);

        Assertions.assertEquals(3, story.getActivityHistory().size());
    }

    @Test
    public void changePriority_Should_ThrowException_When_SamePriority() {
        Assertions.assertThrows(InvalidUserInputException.class, () -> story.changePriority(VALID_SPECIFIC_TASK_PRIORITY));
    }

    @Test
    public void changePriority_Should_SetPriority_When_NewPriorityIsValid() {
        story.changePriority(Priority.LOW);

        Assertions.assertEquals(Priority.LOW, story.getPriority());
    }

    @Test
    public void changePriority_Should_LogEvent_When_PriorityIsChanged() {
        story.changePriority(Priority.LOW);

        Assertions.assertEquals(2, story.getActivityHistory().size());
    }
}
