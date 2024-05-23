package com.practice.projectone.teammanagement.models;

import com.practice.projectone.teammanagement.exceptions.DuplicateEntityException;
import com.practice.projectone.teammanagement.exceptions.ElementNotFoundException;
import com.practice.projectone.teammanagement.models.contracts.Board;
import com.practice.projectone.teammanagement.models.contracts.Person;
import com.practice.projectone.teammanagement.models.contracts.Team;
import com.practice.projectone.teammanagement.utils.ValidationHelpers;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TeamImpl implements Team {

    public static final String BOARD_ALREADY_EXISTS = "Board already exists";
    public static final String MEMBER_ALREADY_EXISTS = "Member already exists";
    public static final int TEAM_NAME_LEN_MIN = 5;
    public static final int TEAM_NAME_LEN_MAX = 15;
    private static final String TEAM_NAME_LEN_ERR = String.format(
            "Team name must be between %s and %s characters long!",
            TEAM_NAME_LEN_MIN,
            TEAM_NAME_LEN_MAX);
    public static final String EMPLOYEE_NOT_FOUND = "Employee %s not in this team";

    private String teamName;
    private final List<Person> people;
    private final List<Board> boards;

    public TeamImpl(String teamName) {
        setTeamName(teamName);
        people = new ArrayList<>();
        boards = new ArrayList<>();
    }

    @Override
    public String getName() {
        return teamName;
    }

    @Override
    public List<Person> getMembers() {
        return new ArrayList<>(people);
    }

    @Override
    public void addMember(Person person) {
        if (getMembers().contains(person)) {
            throw new DuplicateEntityException(MEMBER_ALREADY_EXISTS);
        }
        people.add(person);
    }

    @Override
    public void removeMember(Person person) {
        if (!people.remove(person)) {
            throw new ElementNotFoundException(String.format(EMPLOYEE_NOT_FOUND, person.getName()));
        }
    }

    @Override
    public List<Board> getBoards() {
        return new ArrayList<>(boards);
    }

    @Override
    public void addBoard(Board board) {

        if (getBoards().contains(board)) {
            throw new DuplicateEntityException(BOARD_ALREADY_EXISTS);
        }

        boards.add(board);
    }

    @Override
    public void removeBoard(Board board) {
        if (!boards.remove(board)) {
            throw new ElementNotFoundException(String.format("Board %s does not exist in %s boards list", board.getName(), teamName));
        }
    }

    @Override
    public String toString() {
        return String.format("TEAM: %s, TOTAL MEMBERS: %d, TOTAL BOARDS: %d", teamName, people.size(), boards.size());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeamImpl team = (TeamImpl) o;
        return teamName.equals(team.teamName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teamName, people, boards);
    }

    private void setTeamName(String teamName) {
        ValidationHelpers.validateStringLength(teamName, TEAM_NAME_LEN_MIN, TEAM_NAME_LEN_MAX, TEAM_NAME_LEN_ERR);
        this.teamName = teamName;
    }
}
