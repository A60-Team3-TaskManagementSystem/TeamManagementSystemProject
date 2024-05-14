package com.practice.projectone.teammanagement.core;

import com.practice.projectone.teammanagement.core.contracts.TeamRepository;
import com.practice.projectone.teammanagement.exceptions.ElementNotFoundException;
import com.practice.projectone.teammanagement.models.PersonImpl;
import com.practice.projectone.teammanagement.models.contracts.Board;
import com.practice.projectone.teammanagement.models.contracts.Nameable;
import com.practice.projectone.teammanagement.models.contracts.Person;
import com.practice.projectone.teammanagement.models.contracts.Team;

import java.util.ArrayList;
import java.util.List;

public class TeamRepositoryImpl implements TeamRepository {
    private final static String PERSON_ALREADY_EXIST = "Person %s already exist. Choose a different name!";
    private final List<Team> teams;
    private final List<Person> people;
    private final List<Board> boards;

    public TeamRepositoryImpl() {
        teams = new ArrayList<>();
        people = new ArrayList<>();
        boards = new ArrayList<>();
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
    public List<Board> getBoards() {
        return new ArrayList<>(boards);
    }

    @Override
    public Person createPerson(String name) {
        return new PersonImpl(name);
    }

    @Override
    public void addPerson(Person person) {
        if (people.contains(person)) {
            throw new IllegalArgumentException(String.format(PERSON_ALREADY_EXIST, person.getName()));
        }
        people.add(person);
    }

    @Override
    public boolean teamExist(String teamName) {
        return findElementByName(teamName, teams, "team") != null;
    }

    @Override
    public boolean boardExist(String boardName) {
        return findElementByName(boardName, boards, "board") != null;
    }

    @Override
    public boolean personExist(String personName) {
        return findElementByName(personName, people, "person") != null;
    }


    private <T extends Nameable> T findElementByName(String name, List<T> list, String lookingFor) {
        T element = list.stream()
                .filter(e -> e.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new ElementNotFoundException(String.format("No such %s found", lookingFor)));
        return element;
    }
}
