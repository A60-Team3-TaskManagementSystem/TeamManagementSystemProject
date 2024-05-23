package com.practice.projectone.teammanagement.models;

import com.practice.projectone.teammanagement.exceptions.DuplicateEntityException;
import com.practice.projectone.teammanagement.exceptions.ElementNotFoundException;
import com.practice.projectone.teammanagement.models.tasks.FeedbackImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.practice.projectone.teammanagement.utils.Constants.*;


public class BoardImplTests {

    private BoardImpl board;

    @BeforeEach
    void beforeEach() {
        board = new BoardImpl(VALID_NAME);
    }

    @Test
    public void constructor_Should_ThrowException_When_BoardNameIsInvalid() {
        Assertions.assertAll(
                () -> Assertions.assertThrows(IllegalArgumentException.class,
                        () -> new BoardImpl(INVALID_NAME_MIN)
                ),
                () -> Assertions.assertThrows(IllegalArgumentException.class,
                        () -> new BoardImpl(INVALID_NAME_MAX))
        );
    }

    @Test
    public void constructor_Should_CreateNewBoard_When_NameIsValid() {
        Assertions.assertAll(
                () -> Assertions.assertEquals(VALID_NAME, board.getName()),
                () -> Assertions.assertDoesNotThrow(() -> board.getTasks()),
                () -> Assertions.assertDoesNotThrow(() -> board.getActivityHistory())

        );
    }

    @Test
    public void constructor_Should_LogEvent_When_BoardIsCreated() {
        Assertions.assertEquals(1, board.getActivityHistory().size());
    }

    @Test
    public void getActivityHistory_Should_ReturnNewArrays_When_Invoked() {
        Assertions.assertNotSame(board.getActivityHistory(), board.getActivityHistory());
    }

    @Test
    public void getTasks_Should_ReturnNewArray_When_Invoked() {
        Assertions.assertNotSame(board.getTasks(), board.getTasks());
    }

    @Test
    public void addTask_Should_ThrowException_When_TaskAlreadyExists() {
        FeedbackImpl feedback = new FeedbackImpl(VALID_TITLE, VALID_DESCRIPTION, VALID_RATING);

        board.addTask(feedback);

        Assertions.assertThrows(DuplicateEntityException.class, () -> board.addTask(feedback));
    }

    @Test
    public void addTask_Should_AddTask_When_Successful() {
        FeedbackImpl feedback = new FeedbackImpl(VALID_TITLE, VALID_DESCRIPTION, VALID_RATING);

        board.addTask(feedback);

        Assertions.assertTrue(board.getTasks().contains(feedback));
    }

    @Test
    public void addTask_Should_LogEvent_When_TaskIsAdded() {
        FeedbackImpl feedback = new FeedbackImpl(VALID_TITLE, VALID_DESCRIPTION, VALID_RATING);

        board.addTask(feedback);

        Assertions.assertEquals(2, board.getActivityHistory().size());
    }

    @Test
    public void removeTask_Should_ThrowException_When_TaskDoesNotExist() {
        FeedbackImpl feedback = new FeedbackImpl(VALID_TITLE, VALID_DESCRIPTION, VALID_RATING);

        Assertions.assertThrows(ElementNotFoundException.class, () -> board.removeTask(feedback));
    }

    @Test
    public void removeTask_Should_RemoveTask_When_TaskIsPresent() {
        FeedbackImpl feedback = new FeedbackImpl(VALID_TITLE, VALID_DESCRIPTION, VALID_RATING);

        board.addTask(feedback);
        board.removeTask(feedback);

        Assertions.assertFalse(board.getTasks().contains(feedback));
    }

    @Test
    public void removeTask_Should_LogEvent_When_TaskIsRemoved() {
        FeedbackImpl feedback = new FeedbackImpl(VALID_TITLE, VALID_DESCRIPTION, VALID_RATING);

        board.addTask(feedback);
        board.removeTask(feedback);

        Assertions.assertEquals(3, board.getActivityHistory().size());
    }

    @Test
    public void toString_Should_PrintExpectedOutput() {
        String expected = String.format("BOARD: %s, TOTAL TASKS: %d", board.getName(), board.getTasks().size());

        Assertions.assertEquals(expected, board.toString());
    }

    @Test
    public void equals_Should_AssertEquality() {
        BoardImpl sameBoard = new BoardImpl("NewBoard");

        Assertions.assertAll(
                () -> Assertions.assertNotEquals(board, sameBoard),
                () -> Assertions.assertEquals(board, board)
        );
    }

    @Test
    public void hashCode_Should_AssertEquality() {
        BoardImpl sameBoard = new BoardImpl("NewBoard");

        Assertions.assertAll(
                () -> Assertions.assertNotEquals(board.hashCode(), sameBoard.hashCode()),
                () -> Assertions.assertEquals(board.hashCode(), board.hashCode())
        );
    }
}
