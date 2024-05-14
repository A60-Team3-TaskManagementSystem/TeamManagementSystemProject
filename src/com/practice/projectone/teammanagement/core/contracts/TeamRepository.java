package com.practice.projectone.teammanagement.core.contracts;

import com.practice.projectone.teammanagement.models.contracts.Board;
import com.practice.projectone.teammanagement.models.contracts.Person;
import com.practice.projectone.teammanagement.models.contracts.Team;

import java.util.List;

public interface TeamRepository {
    List<Person> getMembers();
    List<Team> getTeams();

    List<Board> getBoards();

    Person createPerson(String name);
    void addPerson(Person person);
    boolean teamExist(String teamName);
    boolean boardExist(String boardName);

    boolean personExist(String personName);

    Team findTeamByName(String name);

    Board findBoardByName(String name);

    Person findPersonByName(String name);
}
