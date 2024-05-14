package com.practice.projectone.teammanagement.models;

import com.practice.projectone.teammanagement.models.contracts.Board;
import com.practice.projectone.teammanagement.models.contracts.Person;
import com.practice.projectone.teammanagement.models.contracts.Team;
import com.practice.projectone.teammanagement.utils.ValidationHelpers;

import java.util.ArrayList;
import java.util.List;

public class TeamImpl implements Team {

    public static final int TEAM_NAME_LEN_MIN = 5;
    public static final int TEAM_NAME_LEN_MAX = 15;
    private static final String TEAM_NAME_LEN_ERR = String.format(
            "Team name must be between %s and %s characters long!",
            TEAM_NAME_LEN_MIN,
            TEAM_NAME_LEN_MAX);

    private String teamName;
    private final List<Person> people;
    private final List<Board> boards;

    public TeamImpl(String teamName) {
        setTeamName(teamName);
        people = new ArrayList<>();
        boards = new ArrayList<>();
    }

    private void setTeamName(String teamName) {
        ValidationHelpers.validateStringLength(teamName, TEAM_NAME_LEN_MIN, TEAM_NAME_LEN_MAX, TEAM_NAME_LEN_ERR);
        this.teamName = teamName;
    }

    @Override
    public String getTeamName() {
        return teamName;
    }

    @Override
    public List<Person> getMembers() {
        return new ArrayList<>(people);
    }

    @Override
    public List<Board> getBoards() {
        return new ArrayList<>(boards);
    }

    @Override
    public void addMember(Person person) {
        people.add(person);
    }

    @Override
    public void addBoard(Board board) {
        boards.add(board);
    }
}
