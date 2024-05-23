package com.practice.projectone.teammanagement.models;

import com.practice.projectone.teammanagement.exceptions.DuplicateEntityException;
import com.practice.projectone.teammanagement.exceptions.ElementNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.practice.projectone.teammanagement.utils.Constants.*;

public class TeamImplTests {

    private TeamImpl team;

    @BeforeEach
    void beforeEach() {
        team = new TeamImpl(VALID_NAME);
    }

    @Test
    public void constructor_Should_ThrowException_When_BoardNameIsInvalid() {
        Assertions.assertAll(
                () -> Assertions.assertThrows(IllegalArgumentException.class,
                        () -> new TeamImpl(INVALID_NAME_MIN)
                ),
                () -> Assertions.assertThrows(IllegalArgumentException.class,
                        () -> new TeamImpl(INVALID_NAME_MAX))
        );
    }

    @Test
    public void constructor_Should_CreateNewBoard_When_NameIsValid() {
        Assertions.assertAll(
                () -> Assertions.assertEquals(VALID_NAME, team.getName()),
                () -> Assertions.assertDoesNotThrow(() -> team.getBoards()),
                () -> Assertions.assertDoesNotThrow(() -> team.getMembers())

        );
    }

    @Test
    public void getBoards_Should_ReturnNewArrays_When_Invoked() {
        Assertions.assertNotSame(team.getBoards(), team.getBoards());
    }

    @Test
    public void addBoard_Should_ThrowException_When_BoardAlreadyAdded() {
        BoardImpl board = new BoardImpl(VALID_NAME);

        team.addBoard(board);

        Assertions.assertThrows(DuplicateEntityException.class, () -> team.addBoard(board));
    }

    @Test
    public void addBoard_Should_AddBoard_When_NotExists() {
        BoardImpl board = new BoardImpl(VALID_NAME);

        team.addBoard(board);

        Assertions.assertTrue(team.getBoards().contains(board));
    }

    @Test
    public void removeBoard_Should_ThrowException_When_BoardDoesNotExist() {
        BoardImpl board = new BoardImpl(VALID_NAME);

        Assertions.assertThrows(ElementNotFoundException.class, () -> team.removeBoard(board));
    }

    @Test
    public void removeBoard_Should_RemoveBoard_When_BoardExists() {
        BoardImpl board = new BoardImpl(VALID_NAME);

        team.addBoard(board);
        team.removeBoard(board);

        Assertions.assertFalse(team.getBoards().contains(board));
    }

    @Test
    public void getMembers_Should_ReturnNewArray_When_Invoked() {
        Assertions.assertNotSame(team.getMembers(), team.getMembers());
    }

    @Test
    public void addMember_Should_ThrowException_When_MemberAlreadyAdded() {
        PersonImpl person = new PersonImpl(VALID_NAME);

        team.addMember(person);

        Assertions.assertThrows(DuplicateEntityException.class, () -> team.addMember(person));
    }

    @Test
    public void addMember_Should_AddMember_When_MemberIsUnique() {
        PersonImpl person = new PersonImpl(VALID_NAME);

        team.addMember(person);

        Assertions.assertTrue(team.getMembers().contains(person));
    }

    @Test
    public void removeMember_Should_ThrowException_When_MemberDoesNotExist() {
        PersonImpl person = new PersonImpl(VALID_NAME);

        Assertions.assertThrows(ElementNotFoundException.class, () -> team.removeMember(person));
    }

    @Test
    public void removeMember_Should_RemoveMember_When_MemberExists() {
        PersonImpl person = new PersonImpl(VALID_NAME);

        team.addMember(person);
        team.removeMember(person);

        Assertions.assertFalse(team.getMembers().contains(person));
    }

    @Test
    public void toString_Should_PrintExpectedOutput() {
        String expected = String.format("TEAM: %s, TOTAL MEMBERS: %d, TOTAL BOARDS: %d",
                team.getName(), team.getMembers().size(), team.getBoards().size());

        Assertions.assertEquals(expected, team.toString());
    }

    @Test
    public void equals_Should_AssertEquality() {
        TeamImpl sameTeam = new TeamImpl("LakeCity");

        Assertions.assertAll(
                () -> Assertions.assertNotEquals(team, sameTeam),
                () -> Assertions.assertEquals(team, team)
        );
    }

    @Test
    public void hashCode_Should_AssertEquality() {
        TeamImpl sameTeam = new TeamImpl("LakeCity");

        Assertions.assertAll(
                () -> Assertions.assertNotEquals(team.hashCode(), sameTeam.hashCode()),
                () -> Assertions.assertEquals(team.hashCode(), team.hashCode())
        );
    }
}
