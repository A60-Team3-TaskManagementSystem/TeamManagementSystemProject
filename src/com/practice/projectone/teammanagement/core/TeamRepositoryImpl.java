package com.practice.projectone.teammanagement.core;

import com.practice.projectone.teammanagement.core.contracts.TeamRepository;
import com.practice.projectone.teammanagement.models.contracts.Person;
import com.practice.projectone.teammanagement.models.contracts.Team;

import java.util.ArrayList;
import java.util.List;

public class TeamRepositoryImpl implements TeamRepository {
    private final List<Team> teams;
    private final List<Person> people;

    public TeamRepositoryImpl() {
        teams = new ArrayList<>();
        people = new ArrayList<>();
    }

    @Override
    public List<Team> getTeams() {
        return new ArrayList<>(teams);
    }

    @Override
    public List<Person> getMembers() {
        return new ArrayList<>(people);
    }
}
