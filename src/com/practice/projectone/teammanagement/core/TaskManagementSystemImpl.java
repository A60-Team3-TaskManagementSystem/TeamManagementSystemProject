package com.practice.projectone.teammanagement.core;

import com.practice.projectone.teammanagement.core.contracts.TaskManagementSystemRepository;
import com.practice.projectone.teammanagement.exceptions.DuplicateEntityException;
import com.practice.projectone.teammanagement.exceptions.ElementNotFoundException;
import com.practice.projectone.teammanagement.models.BoardImpl;
import com.practice.projectone.teammanagement.models.CommentImpl;
import com.practice.projectone.teammanagement.models.PersonImpl;
import com.practice.projectone.teammanagement.models.TeamImpl;
import com.practice.projectone.teammanagement.models.contracts.*;
import com.practice.projectone.teammanagement.models.tasks.BugImpl;
import com.practice.projectone.teammanagement.models.tasks.FeedbackImpl;
import com.practice.projectone.teammanagement.models.tasks.StoryImpl;
import com.practice.projectone.teammanagement.models.tasks.contracts.*;
import com.practice.projectone.teammanagement.models.tasks.enums.Priority;
import com.practice.projectone.teammanagement.models.tasks.enums.Severity;
import com.practice.projectone.teammanagement.models.tasks.enums.Size;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TaskManagementSystemImpl implements TaskManagementSystemRepository {
    private final static String PERSON_ALREADY_EXIST = "Person %s already exist. Choose a different name!";
    private final static String TEAM_ALREADY_EXIST = "Team %s already exist. Choose a different name!";
    private final static String PERSON_ALREADY_MEMBER = "%s is already a member of this team!";
    public static final String DUPLICATE_BOARD_NAME = "Board name already taken. Choose another";
    public static final String DUPLICATE_TASK = "Task already exist";
    public static final String NO_SUCH_ELEMENT = "No such %s found";
    private final List<Team> teams;
    private final List<Person> people;
    private final List<Bug> bugs;
    private final List<Story> stories;
    private final List<Feedback> feedbacks;

    public TaskManagementSystemImpl() {
        teams = new ArrayList<>();
        people = new ArrayList<>();
        bugs = new ArrayList<>();
        stories = new ArrayList<>();
        feedbacks = new ArrayList<>();
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
    public List<Bug> getBugs() {
        return new ArrayList<>(bugs);
    }

    @Override
    public List<Story> getStories() {
        return new ArrayList<>(stories);
    }

    @Override
    public List<Feedback> getFeedbacks() {
        return new ArrayList<>(feedbacks);
    }

    @Override
    public List<Task> getTasks() {
        List<Task> tasks = new ArrayList<>(bugs);
        tasks.addAll(stories);
        tasks.addAll(feedbacks);

        return tasks;
    }

    @Override
    public List<SpecificTask> getSpecificTasks() {
        List<SpecificTask> specificTasks = new ArrayList<>(bugs);
        specificTasks.addAll(stories);

        return specificTasks;
    }

    @Override
    public Person createPerson(String name) {
        return new PersonImpl(name);
    }

    @Override
    public Team createTeam(String teamName) {
        return new TeamImpl(teamName);
    }

    @Override
    public Bug createBug(String title, String description, Priority priority, Severity severity, List<String> steps) {
        return new BugImpl(title, description, priority, severity, steps);
    }

    @Override
    public Story createStory(String title, String description, Priority priority, Size size) {
        return new StoryImpl(title, description, priority, size);
    }

    @Override
    public Feedback createFeedback(String title, String description, int rating) {
        return new FeedbackImpl(title, description, rating);
    }

    @Override
    public Comment createComment(String author, String description) {
        return new CommentImpl(author, description);
    }

    @Override
    public Board createBoard(String boardName) {
        return new BoardImpl(boardName);
    }

    @Override
    public void addPerson(Person person) {
        if (people.contains(person)) {
            throw new IllegalArgumentException(String.format(PERSON_ALREADY_EXIST, person.getName()));
        }
        people.add(person);
    }

    @Override
    public void addTeam(Team team) {
        if (teams.contains(team)) {
            throw new IllegalArgumentException(String.format(TEAM_ALREADY_EXIST, team.getName()));
        }
        teams.add(team);
    }

    @Override
    public void addBug(Bug bug) {
        addTask(bugs, bug);
    }

    @Override
    public void addStory(Story story) {
        addTask(stories, story);
    }

    @Override
    public void addFeedback(Feedback feedback) {
        addTask(feedbacks, feedback);
    }

    @Override
    public void addBoardToTeam(Board board, Team team) {
        if (team.getBoards().contains(board)) {
            throw new DuplicateEntityException(DUPLICATE_BOARD_NAME);
        }

        team.addBoard(board);
    }

    @Override
    public void addMemberToTeam(Person person, Team team) {
        if (team.getMembers().contains(person)) {
            throw new DuplicateEntityException(String.format(PERSON_ALREADY_MEMBER, person.getName()));
        }
        team.addMember(person);
    }

    @Override
    public void addTaskToBoard(Board board, Task task) {
        if (board.getTasks().contains(task)) {
            throw new DuplicateEntityException(DUPLICATE_TASK);
        }
        board.addTask(task);
    }

    @Override
    public Team findTeamByName(String name) {
        return findElementByName(name, teams, "team");
    }

    @Override
    public Person findPersonByName(String name) {
        return findElementByName(name, people, "person");
    }

    @Override
    public Board findBoardByName(String name) {
        List<Board> boards = getTeams()
                .stream()
                .flatMap(team -> team.getBoards().stream())
                .filter(board -> board.getName().equals(name))
                .collect(Collectors.toList());

        if (boards.isEmpty()) {
            throw new ElementNotFoundException(String.format(NO_SUCH_ELEMENT, "board"));
        } if (boards.size() > 1) {
            throw new DuplicateEntityException(String.format("Multiple boards with name %s", name));
        }

        return boards.get(0);
    }

    @Override
    public Task findTaskByID(long id) {
        return findElementByID(id, getTasks(), "task");
    }

    @Override
    public SpecificTask findSpecificTask(long id) {
        return findElementByID(id, getSpecificTasks(), "task");
    }

    @Override
    public Bug findBugByID(long id) {
        return findElementByID(id, getBugs(), "bug");
    }

    @Override
    public Story findStoryByID(long id) {
        return findElementByID(id, getStories(), "story");
    }

    @Override
    public Feedback findFeedbackById(long id) {
        return findElementByID(id, getFeedbacks(), "feedback");
    }

    private <T extends Nameable> T findElementByName(String name, List<T> list, String lookingFor) {
        return list.stream()
                .filter(e -> e.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new ElementNotFoundException(String.format(NO_SUCH_ELEMENT, lookingFor)));
    }

    private <T extends Identifiable> T findElementByID(long ID, List<T> list, String lookingFor) {
        return list.stream()
                .filter(e -> e.getId() == ID)
                .findFirst()
                .orElseThrow(() -> new ElementNotFoundException(String.format(NO_SUCH_ELEMENT, lookingFor)));
    }

    private <T extends Task> void addTask(List<T> list, T task) {
        if (getTasks().contains(task)) {
            throw new DuplicateEntityException(DUPLICATE_TASK);
        }

        list.add(task);
    }
}
