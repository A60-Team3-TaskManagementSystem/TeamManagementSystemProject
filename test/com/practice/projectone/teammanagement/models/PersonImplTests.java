package com.practice.projectone.teammanagement.models;

import com.practice.projectone.teammanagement.exceptions.ElementNotFoundException;
import com.practice.projectone.teammanagement.models.tasks.BugImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.practice.projectone.teammanagement.utils.Constants.*;

public class PersonImplTests {

    @Test
    public void constructor_Should_ThrowException_When_NameIsInvalid(){
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new PersonImpl(INVALID_NAME_MIN)
        );
    }

    @Test
    public void constructor_Should_InitializePerson_When_ArgumentsAreValid(){
        PersonImpl person = initializePerson();
        Assertions.assertAll(
                () -> Assertions.assertEquals(VALID_NAME, person.getName()),
                () -> Assertions.assertNotNull(person.getTasks()),
                () -> Assertions.assertNotNull(person.getActivityHistory())
        );
    }

    @Test
    public void getTasks_Should_ReturnCopyOfCollection(){
        PersonImpl person = initializePerson();
        person.getTasks().add(new BugImpl(
                VALID_TITLE,
                VALID_DESCRIPTION,
                VALID_SPECIFIC_TASK_PRIORITY,
                VALID_SEVERITY,
                VALID_STEPS_LIST));
        Assertions.assertEquals(0, person.getTasks().size());
    }

    @Test
    public void getActivityHistory_Should_ReturnCopyOfCollection(){
        PersonImpl person = initializePerson();
        person.getActivityHistory().add(new EventLogImpl("test"));
        Assertions.assertEquals(1, person.getActivityHistory().size());
    }

    @Test
    public void assignTask_Should_AddTaskToTaskList(){
        PersonImpl person = initializePerson();
        person.assignTask(new BugImpl(
                VALID_TITLE,
                VALID_DESCRIPTION,
                VALID_SPECIFIC_TASK_PRIORITY,
                VALID_SEVERITY,
                VALID_STEPS_LIST
        ));
        Assertions.assertEquals(1, person.getTasks().size());
    }

    @Test
    public void unassignTask_Should_ThrowException_When_TaskDoesNotExist(){
        BugImpl bug = new BugImpl(VALID_TITLE,
                VALID_DESCRIPTION,
                VALID_SPECIFIC_TASK_PRIORITY,
                VALID_SEVERITY,
                VALID_STEPS_LIST);
        BugImpl bug2 = new BugImpl(VALID_TITLE,
                VALID_DESCRIPTION,
                VALID_SPECIFIC_TASK_PRIORITY,
                VALID_SEVERITY,
                VALID_STEPS_LIST);
        PersonImpl person = initializePerson();
        person.assignTask(bug);
        Assertions.assertThrows(
                ElementNotFoundException.class,
                () -> person.unassignTask(bug2));
    }

    @Test
    public void unassignTask_Should_RemoveItemWhenExists(){
        BugImpl bug = new BugImpl(VALID_TITLE,
                VALID_DESCRIPTION,
                VALID_SPECIFIC_TASK_PRIORITY,
                VALID_SEVERITY,
                VALID_STEPS_LIST);
        PersonImpl person = initializePerson();
        person.assignTask(bug);
        person.unassignTask(bug);
        Assertions.assertEquals(0, person.getTasks().size());
    }

    @Test
    public void equals_Should_AssertEquality() {
        PersonImpl person = new PersonImpl("LakeCity");
        PersonImpl person1 = initializePerson();

        Assertions.assertAll(
                () -> Assertions.assertNotEquals(person, person1),
                () -> Assertions.assertEquals(person, person)
        );
    }

    @Test
    public void hashCode_Should_AssertEquality() {
        PersonImpl person = new PersonImpl("LakeCity");
        PersonImpl person1 = initializePerson();

        Assertions.assertAll(
                () -> Assertions.assertNotEquals(person.hashCode(), person1.hashCode()),
                () -> Assertions.assertEquals(person.hashCode(), person.hashCode())
        );
    }

    public PersonImpl initializePerson(){
        return new PersonImpl(VALID_NAME);
    }
}
