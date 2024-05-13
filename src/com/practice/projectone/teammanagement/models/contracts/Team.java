package com.practice.projectone.teammanagement.models.contracts;

import java.util.List;

public interface Team{
    String getTeamName();
    List<Member> getMembers();
    List<Board> getBoards();
    void addMember(Member member);
    void addBoard(Board board);
}
