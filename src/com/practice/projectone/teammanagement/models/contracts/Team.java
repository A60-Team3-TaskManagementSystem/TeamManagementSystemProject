package com.practice.projectone.teammanagement.models.contracts;

import java.util.List;

public interface Team extends Nameable{

    List<Person> getMembers();

    List<Board> getBoards();

    void addMember(Person person);

    void addBoard(Board board);
}
