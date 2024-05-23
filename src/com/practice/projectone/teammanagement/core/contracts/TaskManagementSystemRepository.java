package com.practice.projectone.teammanagement.core.contracts;

import com.practice.projectone.teammanagement.models.contracts.Board;
import com.practice.projectone.teammanagement.models.contracts.Comment;
import com.practice.projectone.teammanagement.models.contracts.Person;
import com.practice.projectone.teammanagement.models.contracts.Team;
import com.practice.projectone.teammanagement.models.tasks.contracts.*;
import com.practice.projectone.teammanagement.models.tasks.enums.Priority;
import com.practice.projectone.teammanagement.models.tasks.enums.Severity;
import com.practice.projectone.teammanagement.models.tasks.enums.Size;

import java.util.List;

public interface TaskManagementSystemRepository {
    List<Person> getMembers();

    List<Team> getTeams();

    List<Bug> getBugs();

    List<Story> getStories();

    List<Feedback> getFeedbacks();

    List<Task> getTasks();

    List<SpecificTask> getSpecificTasks();

    void addBoardToTeam(Board board, Team team);

    void addPerson(Person person);

    void addTeam(Team team);

    void addMemberToTeam(Person person, Team team);

    Person createPerson(String name);

    Team createTeam(String teamName);

    Board createBoard(String boardName);
    void addTaskToBoard(Board board, Task task);

    Bug createBug(String teamName, String description, Priority priority, Severity severity, List<String> steps);

    Story createStory(String title, String description, Priority priority, Size size);

    Feedback createFeedback(String title, String description, int rating);

    Comment createComment(String author, String description);

    Team findTeamByName(String name);

    Person findPersonByName(String name);

    Board findBoardByName(String name);

    Task findTaskByID(int id);

    Bug findBugByID(int id);

    Story findStoryByID(int id);

    Feedback findFeedbackById(int id);

    SpecificTask findSpecificTask(int id);
}
