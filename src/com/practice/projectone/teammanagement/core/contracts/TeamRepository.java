package com.practice.projectone.teammanagement.core.contracts;

import com.practice.projectone.teammanagement.models.contracts.*;
import com.practice.projectone.teammanagement.models.enums.Priority;
import com.practice.projectone.teammanagement.models.enums.Severity;
import com.practice.projectone.teammanagement.models.enums.Size;

import java.util.List;

public interface TeamRepository {
    List<Person> getMembers();

    List<Team> getTeams();

    List<Task> getTasks();

    void addPerson(Person person);

    void addTeam(Team team);

    void addMemberToTeam(Person person, Team team);

    Person createPerson(String name);

    Team createTeam(String teamName);

    void createBoard(String team, String boardName);

    void createTask(Board board, Task task);

    Bug createBug(String teamName, String description, Priority priority, Severity severity, String assigneeName, List<String> steps);

    Story createStory(String title, String description, Priority priority, Size size, String assigneeName);

    Feedback createFeedback(String title, String description, int rating);

    Comment createComment(String author, String description);

    Team findTeamByName(String name);

    Person findPersonByName(String name);

    Board findBoardByName(String name);

    Task findTaskByID(int id);

    boolean teamExist(String teamName);

    boolean personExist(String personName);

    boolean boardExists(String boardName);
}
