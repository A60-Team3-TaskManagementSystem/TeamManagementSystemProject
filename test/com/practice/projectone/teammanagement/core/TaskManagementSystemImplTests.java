package com.practice.projectone.teammanagement.core;

import com.practice.projectone.teammanagement.core.contracts.TaskManagementSystemRepository;
import com.practice.projectone.teammanagement.exceptions.DuplicateEntityException;
import com.practice.projectone.teammanagement.exceptions.ElementNotFoundException;
import com.practice.projectone.teammanagement.models.contracts.Board;
import com.practice.projectone.teammanagement.models.contracts.Comment;
import com.practice.projectone.teammanagement.models.contracts.Person;
import com.practice.projectone.teammanagement.models.contracts.Team;
import com.practice.projectone.teammanagement.models.tasks.FeedbackImpl;
import com.practice.projectone.teammanagement.models.tasks.StoryImpl;
import com.practice.projectone.teammanagement.models.tasks.contracts.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.practice.projectone.teammanagement.utils.Constants.*;


public class TaskManagementSystemImplTests {

    public static final String VALID_NAME = "Person";
    private TaskManagementSystemRepository repository;

    @BeforeEach
    void setup() {
        repository = new TaskManagementSystemImpl();
    }

    @Test
    public void getTeams_Should_ReturnCopyOfCollection() {
        Assertions.assertNotSame(repository.getTeams(), repository.getTeams());
    }

    @Test
    public void getMembers_Should_ReturnCopyOfCollection() {
        Assertions.assertNotSame(repository.getMembers(), repository.getMembers());
    }

    @Test
    public void getBugs_Should_ReturnCopyOfCollection() {
        Assertions.assertNotSame(repository.getBugs(), repository.getBugs());
    }

    @Test
    public void getStories_Should_ReturnCopyOfCollection() {
        Assertions.assertNotSame(repository.getStories(), repository.getStories());
    }

    @Test
    public void getFeedbacks_Should_ReturnCopyOfCollection() {
        Assertions.assertNotSame(repository.getFeedbacks(), repository.getFeedbacks());
    }

    @Test
    public void getTasks_Should_ReturnCopyOfCollection() {
        List<Task> tasks = repository.getTasks();
        tasks.add(new FeedbackImpl(VALID_TITLE, VALID_DESCRIPTION, VALID_RATING));

        Assertions.assertEquals(0, repository.getTasks().size());
    }

    @Test
    public void getSpecificTasks_Should_ReturnCopyOfCollection() {
        List<SpecificTask> tasks = repository.getSpecificTasks();
        tasks.add(new StoryImpl(VALID_TITLE, VALID_DESCRIPTION, VALID_SPECIFIC_TASK_PRIORITY, VALID_STORY_SIZE));

        Assertions.assertEquals(0, repository.getSpecificTasks().size());
    }

    @Test
    public void createPerson_Should_ReturnPerson_When_InputIsValid() {
        Person person = repository.createPerson(VALID_NAME);

        Assertions.assertEquals(VALID_NAME, person.getName());
    }

    @Test
    public void createTeam_Should_ReturnTeam_When_InputIsValid() {
        Team team = repository.createTeam(VALID_NAME);

        Assertions.assertEquals(VALID_NAME, team.getName());
    }

    @Test
    public void createComment_Should_ReturnComment_When_InputIsValid() {
        Comment comment = repository.createComment(VALID_NAME, VALID_DESCRIPTION);

        Assertions.assertAll(
                () -> Assertions.assertEquals(VALID_NAME, comment.getAuthor()),
                () -> Assertions.assertEquals(VALID_DESCRIPTION, comment.getDescription())
        );
    }

    @Test
    public void createBoard_Should_ReturnBoard_When_InputIsValid() {
        Board board = repository.createBoard(VALID_NAME);

        Assertions.assertEquals(VALID_NAME, board.getName());
    }

    @Test
    public void createBug_Should_ReturnBug_When_InputIsValid() {
        Bug bug = repository.createBug(VALID_TITLE,
                VALID_DESCRIPTION,
                VALID_SPECIFIC_TASK_PRIORITY,
                VALID_SEVERITY,
                VALID_STEPS_LIST);

        Assertions.assertAll(
                () -> Assertions.assertEquals(VALID_TITLE, bug.getName()),
                () -> Assertions.assertEquals(VALID_DESCRIPTION, bug.getDescription()),
                () -> Assertions.assertEquals(VALID_SPECIFIC_TASK_PRIORITY, bug.getPriority()),
                () -> Assertions.assertEquals(VALID_SEVERITY, bug.getSeverity()),
                () -> Assertions.assertNotNull(bug.getSteps())
        );
    }

    @Test
    public void createStory_Should_ReturnStory_When_InputIsValid() {
        Story story = repository.createStory(VALID_TITLE,
                VALID_DESCRIPTION,
                VALID_SPECIFIC_TASK_PRIORITY,
                VALID_STORY_SIZE);

        Assertions.assertAll(
                () -> Assertions.assertEquals(VALID_TITLE, story.getName()),
                () -> Assertions.assertEquals(VALID_DESCRIPTION, story.getDescription()),
                () -> Assertions.assertEquals(VALID_SPECIFIC_TASK_PRIORITY, story.getPriority()),
                () -> Assertions.assertEquals(VALID_STORY_SIZE, story.getSize()),
                () -> Assertions.assertDoesNotThrow(() -> story.getComments()),
                () -> Assertions.assertDoesNotThrow(() -> story.getActivityHistory())
        );
    }

    @Test
    public void createFeedback_Should_ReturnFeedback_When_InputIsValid() {
        Feedback feedback = repository.createFeedback(VALID_TITLE,
                VALID_DESCRIPTION,
                VALID_RATING);

        Assertions.assertAll(
                () -> Assertions.assertEquals(VALID_TITLE, feedback.getName()),
                () -> Assertions.assertEquals(VALID_DESCRIPTION, feedback.getDescription()),
                () -> Assertions.assertEquals(VALID_RATING, feedback.getRating()),
                () -> Assertions.assertDoesNotThrow(() -> feedback.getComments()),
                () -> Assertions.assertDoesNotThrow(() -> feedback.getActivityHistory())
        );
    }

    @Test
    public void addPerson_Should_AddPersonToRepository_When_InputIsValid() {
        Person person = repository.createPerson(VALID_NAME);

        repository.addPerson(person);

        Assertions.assertEquals(1, repository.getMembers().size());
    }

    @Test
    public void addPerson_Should_ThrowException_When_PersonExists() {
        Person person = repository.createPerson(VALID_NAME);

        repository.addPerson(person);

        Assertions.assertThrows(IllegalArgumentException.class, () -> repository.addPerson(person));
    }

    @Test
    public void addTeam_Should_AddTeamToRepository_When_InputIsValid() {
        Team team = repository.createTeam(VALID_NAME);

        repository.addTeam(team);

        Assertions.assertEquals(1, repository.getTeams().size());
    }

    @Test
    public void addTeam_Should_ThrowException_When_TeamExists() {
        Team team = repository.createTeam(VALID_NAME);

        repository.addTeam(team);

        Assertions.assertThrows(IllegalArgumentException.class, () -> repository.addTeam(team));
    }

    @Test
    public void addBug_Should_AddBugToRepository_When_InputIsValid() {
        Bug bug = repository.createBug(VALID_TITLE, VALID_DESCRIPTION,
                VALID_SPECIFIC_TASK_PRIORITY, VALID_SEVERITY, VALID_STEPS_LIST);

        repository.addBug(bug);

        Assertions.assertEquals(1, repository.getBugs().size());
    }

    @Test
    public void addBug_Should_ThrowException_When_BugExists() {
        Bug bug = repository.createBug(VALID_TITLE, VALID_DESCRIPTION,
                VALID_SPECIFIC_TASK_PRIORITY, VALID_SEVERITY, VALID_STEPS_LIST);

        repository.addBug(bug);

        Assertions.assertThrows(DuplicateEntityException.class, () -> repository.addBug(bug));
    }

    @Test
    public void addStory_Should_AddStoryToRepository_When_InputIsValid() {
        Story story = repository.createStory(VALID_TITLE, VALID_DESCRIPTION,
                VALID_SPECIFIC_TASK_PRIORITY, VALID_STORY_SIZE);

        repository.addStory(story);

        Assertions.assertEquals(1, repository.getStories().size());
    }

    @Test
    public void addStory_Should_ThrowException_When_StoryExists() {
        Story story = repository.createStory(VALID_TITLE, VALID_DESCRIPTION,
                VALID_SPECIFIC_TASK_PRIORITY, VALID_STORY_SIZE);

        repository.addStory(story);

        Assertions.assertThrows(DuplicateEntityException.class, () -> repository.addStory(story));
    }

    @Test
    public void addFeedback_Should_AddFeedbackToRepository_When_InputIsValid() {
        Feedback feedback = repository.createFeedback(VALID_TITLE, VALID_DESCRIPTION, VALID_RATING);

        repository.addFeedback(feedback);

        Assertions.assertEquals(1, repository.getFeedbacks().size());
    }

    @Test
    public void addFeedback_Should_ThrowException_When_FeedbackExists() {
        Feedback feedback = repository.createFeedback(VALID_TITLE, VALID_DESCRIPTION, VALID_RATING);

        repository.addFeedback(feedback);

        Assertions.assertThrows(DuplicateEntityException.class, () -> repository.addFeedback(feedback));
    }

    @Test
    public void addBoardToTeam_Should_ThrowException_When_BoardAlreadyExistsInTeam() {
        Board board = repository.createBoard(VALID_NAME);
        Team team = repository.createTeam(VALID_NAME);

        repository.addBoardToTeam(board, team);

        Assertions.assertThrows(DuplicateEntityException.class, () -> repository.addBoardToTeam(board, team));
    }

    @Test
    public void addBoardToTeam_Should_AddBoardToTeam_When_BoardIsNotAlreadyAssignedToTeam() {
        Board board = repository.createBoard(VALID_NAME);
        Team team = repository.createTeam(VALID_NAME);

        repository.addBoardToTeam(board, team);

        Assertions.assertEquals(1, team.getBoards().size());
    }

    @Test
    public void addMemberToTeam_Should_ThrowException_When_MemberAlreadyPartOfTeam() {
        Person person = repository.createPerson(VALID_NAME);
        Team team = repository.createTeam(VALID_NAME);

        repository.addMemberToTeam(person, team);

        Assertions.assertThrows(DuplicateEntityException.class, () -> repository.addMemberToTeam(person, team));
    }

    @Test
    public void addMemberToTeam_Should_AddMemberToTeam_When_MemberIsNotInTeam() {
        Person person = repository.createPerson(VALID_NAME);
        Team team = repository.createTeam(VALID_NAME);

        repository.addMemberToTeam(person, team);

        Assertions.assertEquals(1, team.getMembers().size());
    }

    @Test
    public void addTaskToBoard_Should_ThrowException_When_TaskExistsInBoard() {
        Board board = repository.createBoard(VALID_NAME);
        Feedback feedback = repository.createFeedback(VALID_TITLE, VALID_DESCRIPTION, VALID_RATING);

        repository.addTaskToBoard(board, feedback);

        Assertions.assertThrows(DuplicateEntityException.class, () -> repository.addTaskToBoard(board, feedback));
    }

    @Test
    public void addTaskToBoard_Should_AddTaskToBoard_When_TaskIsNotOnBoard() {
        Board board = repository.createBoard(VALID_NAME);
        Feedback feedback = repository.createFeedback(VALID_TITLE, VALID_DESCRIPTION, VALID_RATING);

        repository.addTaskToBoard(board, feedback);

        Assertions.assertEquals(1, board.getTasks().size());
    }

    @Test
    public void findTeamByName_Should_ThrowException_When_TeamNotFound() {
        Assertions.assertThrows(ElementNotFoundException.class, () -> repository.findTeamByName(VALID_NAME));
    }

    @Test
    public void findTeamByName_Should_ReturnTeam_When_TeamFound() {
        Team team = repository.createTeam(VALID_NAME);

        repository.addTeam(team);

        Assertions.assertEquals(team, repository.findTeamByName(VALID_NAME));
    }

    @Test
    public void findPersonByName_Should_ThrowException_When_PersonNotFound() {
        Assertions.assertThrows(ElementNotFoundException.class, () -> repository.findPersonByName(VALID_NAME));
    }

    @Test
    public void findPersonByName_Should_ReturnPerson_When_PersonFound() {
        Person person = repository.createPerson(VALID_NAME);

        repository.addPerson(person);

        Assertions.assertEquals(person, repository.findPersonByName(VALID_NAME));
    }

    @Test
    public void findBoardByName_Should_ThrowException_When_BoardNotFound() {
        Assertions.assertThrows(ElementNotFoundException.class, () -> repository.findBoardByName(VALID_NAME));
    }

    @Test
    public void findBoardByName_Should_ThrowException_When_DuplicateBoardName() {
        Board board = repository.createBoard(VALID_NAME);
        Team team = repository.createTeam(VALID_TITLE);
        Team otherTeam = repository.createTeam(VALID_NAME);

        repository.addTeam(team);
        repository.addTeam(otherTeam);
        repository.addBoardToTeam(board, team);
        repository.addBoardToTeam(board, otherTeam);

        Assertions.assertThrows(DuplicateEntityException.class, () -> repository.findBoardByName(VALID_NAME));
    }

    @Test
    public void findBoardByName_Should_ReturnBoard_When_UniqueBoardNameIsFound() {
        Board board = repository.createBoard(VALID_NAME);
        Team team = repository.createTeam(VALID_NAME);

        repository.addTeam(team);
        repository.addBoardToTeam(board, team);

        Assertions.assertEquals(board, repository.findBoardByName(VALID_NAME));
    }

    @Test
    public void findTaskById_Should_ThrowException_When_TaskNotFound(){
        Assertions.assertThrows(ElementNotFoundException.class, () -> repository.findTaskByID(1));
    }

    @Test
    public void findTaskById_Should_ReturnTask_When_TaskFound(){
        Feedback feedback = repository.createFeedback(VALID_TITLE, VALID_DESCRIPTION, VALID_RATING);

        repository.addFeedback(feedback);

        Assertions.assertEquals(feedback, repository.findTaskByID(feedback.getId()));
    }

    @Test
    public void findSpecificTaskById_Should_ThrowException_When_TaskNotFound(){
        Assertions.assertThrows(ElementNotFoundException.class, () -> repository.findSpecificTask(1));
    }

    @Test
    public void findSpecificTaskById_Should_ReturnSpecificTask_When_TaskFound(){
        Story story = repository.createStory(VALID_TITLE, VALID_DESCRIPTION,
                                            VALID_SPECIFIC_TASK_PRIORITY, VALID_STORY_SIZE);

        repository.addStory(story);

        Assertions.assertEquals(story, repository.findSpecificTask(story.getId()));
    }

    @Test
    public void findStoryById_Should_ThrowException_When_StoryNotFound(){
        Assertions.assertThrows(ElementNotFoundException.class, () -> repository.findStoryByID(1));
    }

    @Test
    public void findStoryById_Should_ReturnStory_When_StoryFound(){
        Story story = repository.createStory(VALID_TITLE, VALID_DESCRIPTION,
                VALID_SPECIFIC_TASK_PRIORITY, VALID_STORY_SIZE);

        repository.addStory(story);

        Assertions.assertEquals(story, repository.findStoryByID(story.getId()));
    }

    @Test
    public void findFeedbackById_Should_ThrowException_When_FeedbackNotFound(){
        Assertions.assertThrows(ElementNotFoundException.class, () -> repository.findFeedbackById(1));
    }

    @Test
    public void findFeedbackById_Should_ReturnFeedback_When_FeedbackFound(){
        Feedback feedback = repository.createFeedback(VALID_TITLE, VALID_DESCRIPTION, VALID_RATING);

        repository.addFeedback(feedback);

        Assertions.assertEquals(feedback, repository.findFeedbackById(feedback.getId()));
    }

    @Test
    public void findBugById_Should_ThrowException_When_BugNotFound(){
        Assertions.assertThrows(ElementNotFoundException.class, () -> repository.findBugByID(1));
    }

    @Test
    public void findBugById_Should_ReturnBug_When_BugFound(){
        Bug bug = repository.createBug(VALID_TITLE, VALID_DESCRIPTION,
                VALID_SPECIFIC_TASK_PRIORITY, VALID_SEVERITY, VALID_STEPS_LIST);

        repository.addBug(bug);

        Assertions.assertEquals(bug, repository.findBugByID(bug.getId()));
    }
}
