package com.practice.projectone.teammanagement.models.contracts;

import java.util.List;

public interface Team extends Nameable {

    List<Person> getMembers();

    void addMember(Person person);

    void removeMember(Person person);

    List<Board> getBoards();

    void addBoard(Board boardName);

    void removeBoard(Board board);
}
