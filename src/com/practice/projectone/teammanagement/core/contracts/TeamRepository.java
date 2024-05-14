package com.practice.projectone.teammanagement.core.contracts;

import com.practice.projectone.teammanagement.models.contracts.Board;
import com.practice.projectone.teammanagement.models.contracts.Person;
import com.practice.projectone.teammanagement.models.contracts.Team;

import java.util.List;

public interface TeamRepository {
    List<Person> getMembers();

    List<Team> getTeams();

    Person createPerson(String name);

    Board createBoard(String boardName);

    void addPerson(Person person);

    boolean teamExist(String teamName);

    boolean personExist(String personName);

    Team findTeamByName(String name);

    Person findPersonByName(String name);
}
