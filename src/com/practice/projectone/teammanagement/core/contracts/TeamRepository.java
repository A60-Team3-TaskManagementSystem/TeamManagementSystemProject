package com.practice.projectone.teammanagement.core.contracts;

import com.practice.projectone.teammanagement.models.contracts.Board;
import com.practice.projectone.teammanagement.models.contracts.Person;
import com.practice.projectone.teammanagement.models.contracts.Task;
import com.practice.projectone.teammanagement.models.contracts.Team;

import java.util.List;

public interface TeamRepository {
    List<Person> getMembers();

    List<Team> getTeams();

    Person createPerson(String name);

    Team createTeam(String teamName);

    void addPerson(Person person);

    void addTeam(Team team);

    void addMemberToTeam(Person person, Team team);

    boolean teamExist(String teamName);

    boolean personExist(String personName);

    Team findTeamByName(String name);

    Person findPersonByName(String name);

    Board findBoardByName(String name);

    void createBoard(String team, String boardName);

    void createTask(Board board, Task bug);
}
