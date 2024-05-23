package com.practice.projectone.teammanagement.models.tasks;

import com.practice.projectone.teammanagement.exceptions.InvalidUserInputException;
import com.practice.projectone.teammanagement.models.tasks.enums.Severity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.practice.projectone.teammanagement.utils.Constants.*;

public class BugImplTests {

    @Test
    public void constructor_Should_ThrowException_When_TitleIsInvalid(){
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new BugImpl(
                        INVALID_TITLE_MIN,
                        VALID_DESCRIPTION,
                        VALID_SPECIFIC_TASK_PRIORITY,
                        VALID_SEVERITY,
                        VALID_STEPS_LIST
                )
        );
    }

    @Test
    public void constructor_Should_ThrowException_When_DescriptionIsInvalid(){
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new BugImpl(
                        VALID_TITLE,
                        INVALID_DESCRIPTION_MIN,
                        VALID_SPECIFIC_TASK_PRIORITY,
                        VALID_SEVERITY,
                        VALID_STEPS_LIST
                )
        );
    }

    @Test
    public void constructor_Should_ThrowException_When_StepsListIsEmpty(){
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new BugImpl(
                        VALID_TITLE,
                        VALID_DESCRIPTION,
                        VALID_SPECIFIC_TASK_PRIORITY,
                        VALID_SEVERITY,
                        INVALID_STEPS_LIST
                )
        );
    }

    @Test
    public void constructor_Should_InitializeBug_When_ArgumentsAreValid(){
        BugImpl bug = initializeBug();

        Assertions.assertAll(
                () -> Assertions.assertEquals(VALID_TITLE, bug.getName()),
                () -> Assertions.assertEquals(VALID_DESCRIPTION, bug.getDescription()),
                () -> Assertions.assertEquals(VALID_SPECIFIC_TASK_PRIORITY, bug.getPriority()),
                () -> Assertions.assertEquals(VALID_SEVERITY, bug.getSeverity()),
                () -> Assertions.assertNotNull(bug.getSteps())
        );
    }

    @Test
    public void changeSeverity_Should_ThrowException_When_NewSeverityIsSameAsOld(){
        BugImpl bug = initializeBug();
        Assertions.assertThrows(
                InvalidUserInputException.class,
                () -> bug.changeSeverity(VALID_SEVERITY));
    }

    @Test
    public void changeSeverity_Should_SetNewSeverity_When_ArgumentIsValid(){
        BugImpl bug = initializeBug();
        Assertions.assertDoesNotThrow(
                () -> bug.changeSeverity(Severity.CRITICAL));
    }

    public static BugImpl initializeBug(){
        return new BugImpl(
                VALID_TITLE,
                VALID_DESCRIPTION,
                VALID_SPECIFIC_TASK_PRIORITY,
                VALID_SEVERITY,
                VALID_STEPS_LIST
        );
    }

}
