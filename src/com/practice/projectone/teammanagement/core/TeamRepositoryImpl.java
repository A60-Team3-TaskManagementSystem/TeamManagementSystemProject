package com.practice.projectone.teammanagement.core;

import com.practice.projectone.teammanagement.core.contracts.TeamRepository;
import com.practice.projectone.teammanagement.exceptions.ElementNotFoundException;
import com.practice.projectone.teammanagement.models.BoardImpl;
import com.practice.projectone.teammanagement.models.PersonImpl;
import com.practice.projectone.teammanagement.models.TeamImpl;
import com.practice.projectone.teammanagement.models.contracts.*;

import java.util.ArrayList;
import java.util.List;

public class TeamRepositoryImpl implements TeamRepository {
    private final static String PERSON_ALREADY_EXIST = "Person %s already exist. Choose a different name!";
    private final static String TEAM_ALREADY_EXIST = "Team %s already exist. Choose a different name!";
    private final static String PERSON_ALREADY_MEMBER = "%s is already a member of this team!";
    private final List<Team> teams;
    private final List<Person> people;
    private final List<Board> boards;
    private final List<Task> tasks;

    public TeamRepositoryImpl() {
        teams = new ArrayList<>();
        people = new ArrayList<>();
        boards = new ArrayList<>();
        tasks = new ArrayList<>();
    }

    @Override
    public List<Team> getTeams() {
        return new ArrayList<>(teams);
    }

    @Override
    public List<Person> getMembers() {
        return new ArrayList<>(people);
    }

    @Override
    public Person createPerson(String name) {
        return new PersonImpl(name);
    }

    @Override
    public Team createTeam(String teamName) {
        return new TeamImpl(teamName);
    }

    @Override
    public void createBoard(String teamName, String boardName) {
        Team team = findTeamByName(teamName);
        Board board = new BoardImpl(boardName);

        team.createBoard(board);

        boards.add(board);
    }

    @Override
    public void createTask(Board board, Task bug) {
        board.addTask(bug);
        tasks.add(bug);
    }

    @Override
    public void addPerson(Person person) {
        if (people.contains(person)) {
            throw new IllegalArgumentException(String.format(PERSON_ALREADY_EXIST, person.getName()));
        }
        people.add(person);
    }

    @Override
    public void addTeam(Team team) {
        if (teams.contains(team)) {
            throw new IllegalArgumentException(String.format(TEAM_ALREADY_EXIST, team.getName()));
        }
        teams.add(team);
    }

    @Override
    public void addMemberToTeam(Person person, Team team) {
        if (team.getMembers().contains(person)) {
            throw new IllegalArgumentException(String.format(PERSON_ALREADY_MEMBER, person.getName()));
        }
        team.addMember(person);
    }

    @Override
    public boolean teamExist(String teamName) {
        return findElementByName(teamName, teams, "team") != null;
    }

    @Override
    public boolean personExist(String personName) {
        return findElementByName(personName, people, "person") != null;
    }

    @Override
    public Team findTeamByName(String name) {
        return findElementByName(name, teams, "team");
    }

    @Override
    public Person findPersonByName(String name) {
        return findElementByName(name, people, "person");
    }

    @Override
    public Board findBoardByName(String name) {
        return findElementByName(name, boards, "board");
    }

    private <T extends Nameable> T findElementByName(String name, List<T> list, String lookingFor) {
        T element = list.stream()
                .filter(e -> e.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new ElementNotFoundException(String.format("No such %s found", lookingFor)));
        return element;
    }
}
