package com.practice.projectone.teammanagement.core.contracts;

import com.practice.projectone.teammanagement.models.contracts.Person;
import com.practice.projectone.teammanagement.models.contracts.Team;

import java.util.List;

public interface TeamRepository {
    List<Person> getMembers();
    List<Team> getTeams();
    Person createPerson(String name);
    void addPerson(Person person);
}
