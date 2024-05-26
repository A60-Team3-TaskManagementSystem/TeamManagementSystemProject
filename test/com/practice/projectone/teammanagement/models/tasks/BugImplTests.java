package com.practice.projectone.teammanagement.models.tasks;

import com.practice.projectone.teammanagement.exceptions.InvalidUserInputException;
import com.practice.projectone.teammanagement.models.tasks.enums.Severity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.practice.projectone.teammanagement.utils.Constants.*;

public class BugImplTests {

    private BugImpl bug;

    @BeforeEach
    void setup() {
        bug = new BugImpl(
                VALID_TITLE,
                VALID_DESCRIPTION,
                VALID_SPECIFIC_TASK_PRIORITY,
                VALID_SEVERITY,
                VALID_STEPS_LIST);
    }

    @Test
    public void constructor_Should_ThrowException_When_StepsListIsNullOrEmpty() {
        Assertions.assertAll(
                () -> Assertions.assertThrows(
                        IllegalArgumentException.class,
                        () -> new BugImpl(
                                VALID_TITLE,
                                VALID_DESCRIPTION,
                                VALID_SPECIFIC_TASK_PRIORITY,
                                VALID_SEVERITY,
                                INVALID_STEPS_LIST
                        )
                ),
                () -> Assertions.assertThrows(
                        IllegalArgumentException.class,
                        () -> new BugImpl(
                                VALID_TITLE,
                                VALID_DESCRIPTION,
                                VALID_SPECIFIC_TASK_PRIORITY,
                                VALID_SEVERITY,
                                INVALID_STEPS_LIST
                        )
                )
        );
    }

    @Test
    public void constructor_Should_InitializeBug_When_ArgumentsAreValid() {
        Assertions.assertAll(
                () -> Assertions.assertEquals(VALID_TITLE, bug.getName()),
                () -> Assertions.assertEquals(VALID_DESCRIPTION, bug.getDescription()),
                () -> Assertions.assertEquals(VALID_SPECIFIC_TASK_PRIORITY, bug.getPriority()),
                () -> Assertions.assertEquals(VALID_SEVERITY, bug.getSeverity()),
                () -> Assertions.assertNotNull(bug.getSteps())
        );
    }

    @Test
    public void changeSeverity_Should_ThrowException_When_NewSeverityIsSameAsOld() {
        Assertions.assertThrows(
                InvalidUserInputException.class,
                () -> bug.changeSeverity(VALID_SEVERITY));
    }

    @Test
    public void changeSeverity_Should_SetNewSeverity_When_ArgumentIsValid() {
        Assertions.assertDoesNotThrow(
                () -> bug.changeSeverity(Severity.CRITICAL));
    }

    @Test
    public void toString_Should_PrintExpectedOutput() {
        String expected = String.format("Task ID%d%n  #Type: %s%n  #Title: %s%n  #Description: %s%n  #Status: %s%n  #Priority: %s%n  #Severity: %s%n  #AssignedTo: %s%n  #StepsToReproduce:%n    %s%n",
                bug.getId(), bug.getTaskType(), bug.getName(),
                bug.getDescription(), bug.getStatus(), bug.getPriority(),
                bug.getSeverity(), bug.getAssignee(),
                String.join(System.lineSeparator() + "    ", bug.getSteps())).trim();

        Assertions.assertEquals(expected, bug.toString());
    }
}
