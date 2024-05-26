package com.practice.projectone.teammanagement.commands.listing;

import com.practice.projectone.teammanagement.commands.contracts.Command;
import com.practice.projectone.teammanagement.core.TaskManagementSystemImpl;
import com.practice.projectone.teammanagement.core.contracts.TaskManagementSystemRepository;
import com.practice.projectone.teammanagement.models.tasks.contracts.Bug;
import com.practice.projectone.teammanagement.models.tasks.enums.Priority;
import com.practice.projectone.teammanagement.models.tasks.enums.Severity;
import com.practice.projectone.teammanagement.models.tasks.enums.Status;
import com.practice.projectone.teammanagement.utils.TestUtilities;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.practice.projectone.teammanagement.utils.Constants.*;

public class ListBugsCommandTests {
    private static final int MAX_ARGUMENT_COUNT = 3;
    private static final String INVALID_SORT = "asd";

    private TaskManagementSystemRepository repository;
    private Command listBugsCommand;

    @BeforeEach
    public void beforeEach(){
        repository = new TaskManagementSystemImpl();
        listBugsCommand = new ListBugsCommand(repository);
    }

    @Test
    public void should_ThrowException_When_ArgumentCountIsAboveAllowed(){
        List<String> params = TestUtilities.getList(MAX_ARGUMENT_COUNT + 1);
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> listBugsCommand.execute(params)
        );
    }

    @Test
    public void should_ThrowException_When_SortingArgumentIsInvalid(){
        List<String> params = List.of(INVALID_SORT);
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> listBugsCommand.execute(params)
        );
    }

    @Test
    public void should_SortByTitle_When_SortingParamIsTitle(){
        repository.createBug(
                VALID_TITLE,
                VALID_DESCRIPTION,
                VALID_SPECIFIC_TASK_PRIORITY,
                VALID_SEVERITY,
                VALID_STEPS_LIST
        );
        repository.createBug(
                "abc" + VALID_TITLE,
                VALID_DESCRIPTION,
                VALID_SPECIFIC_TASK_PRIORITY,
                VALID_SEVERITY,
                VALID_STEPS_LIST
        );
        List<Bug> bugs = repository.getBugs();
        List<String> params = List.of("title");

        String sorted = bugs
                .stream()
                .sorted(Comparator.comparing(Bug::getName))
                .map(Bug::toString)
                .collect(Collectors.joining(System.lineSeparator()));
        Assertions.assertEquals(sorted, listBugsCommand.execute(params));
    }

    @Test
    public void should_SortByPriority_When_SortingParamIsPriority(){
        repository.createBug(
                VALID_TITLE,
                VALID_DESCRIPTION,
                VALID_SPECIFIC_TASK_PRIORITY,
                VALID_SEVERITY,
                VALID_STEPS_LIST
        );
        repository.createBug(
                VALID_TITLE,
                VALID_DESCRIPTION,
                Priority.HIGH,
                VALID_SEVERITY,
                VALID_STEPS_LIST
        );
        List<Bug> bugs = repository.getBugs();
        List<String> params = List.of("priority");

        String sorted = bugs
                .stream()
                .sorted(Comparator.comparing(Bug::getPriority))
                .map(Bug::toString)
                .collect(Collectors.joining(System.lineSeparator()));
        Assertions.assertEquals(sorted, listBugsCommand.execute(params));
    }

    @Test
    public void should_SortBySeverity_When_SortingParamIsSeverity(){
        repository.createBug(
                VALID_TITLE,
                VALID_DESCRIPTION,
                VALID_SPECIFIC_TASK_PRIORITY,
                VALID_SEVERITY,
                VALID_STEPS_LIST
        );
        repository.createBug(
                VALID_TITLE,
                VALID_DESCRIPTION,
                VALID_SPECIFIC_TASK_PRIORITY,
                Severity.CRITICAL,
                VALID_STEPS_LIST
        );
        List<Bug> bugs = repository.getBugs();
        List<String> params = List.of("severity");

        String sorted = bugs
                .stream()
                .sorted(Comparator.comparing(Bug::getSeverity))
                .map(Bug::toString)
                .collect(Collectors.joining(System.lineSeparator()));
        Assertions.assertEquals(sorted, listBugsCommand.execute(params));
    }
    @Test
    public void should_NotSort_When_SortingParamIsNosort(){
        repository.createBug(
                VALID_TITLE,
                VALID_DESCRIPTION,
                VALID_SPECIFIC_TASK_PRIORITY,
                VALID_SEVERITY,
                VALID_STEPS_LIST
        );
        repository.createBug(
                "abc" + VALID_TITLE,
                VALID_DESCRIPTION,
                VALID_SPECIFIC_TASK_PRIORITY,
                VALID_SEVERITY,
                VALID_STEPS_LIST
        );
        List<Bug> bugs = repository.getBugs();
        List<String> params = List.of("nosort");

        String sorted = bugs
                .stream()
                .map(Bug::toString)
                .collect(Collectors.joining(System.lineSeparator()));
        Assertions.assertEquals(sorted, listBugsCommand.execute(params));
    }

    @Test
    public void should_FilterByStatusORAssignee_When_OnlyOneFilter(){
        repository.createBug(
                VALID_TITLE,
                VALID_DESCRIPTION,
                VALID_SPECIFIC_TASK_PRIORITY,
                VALID_SEVERITY,
                VALID_STEPS_LIST
        );
        repository.createBug(
                "abc" + VALID_TITLE,
                VALID_DESCRIPTION,
                VALID_SPECIFIC_TASK_PRIORITY,
                VALID_SEVERITY,
                VALID_STEPS_LIST
        );
        repository.getBugs().get(1).changeStatus(Status.DONE);
        repository.createPerson("Pesho");
        repository.getBugs().get(0).changeAssignee("Pesho");
        List<Bug> bugs = repository.getBugs();
        List<String> params = List.of("nosort", "Pesho");

        String filtered = bugs
                .stream()
                .filter(bug -> params.get(1).equals(bug.getAssignee()) || bug.getStatus().toString().equals(params.get(1)))
                .map(Bug::toString)
                .collect(Collectors.joining(System.lineSeparator()));

        Assertions.assertEquals(filtered, listBugsCommand.execute(params));
    }

    @Test
    public void should_FilterByStatusANDAssignee_When_TwoFilters(){
        repository.createBug(
                VALID_TITLE,
                VALID_DESCRIPTION,
                VALID_SPECIFIC_TASK_PRIORITY,
                VALID_SEVERITY,
                VALID_STEPS_LIST
        );
        repository.createBug(
                "abc" + VALID_TITLE,
                VALID_DESCRIPTION,
                VALID_SPECIFIC_TASK_PRIORITY,
                VALID_SEVERITY,
                VALID_STEPS_LIST
        );
        repository.getBugs().get(1).changeStatus(Status.DONE);
        repository.createPerson("Pesho");
        repository.getBugs().get(0).changeAssignee("Pesho");
        List<Bug> bugs = repository.getBugs();
        List<String> params = List.of("nosort", "Active", "Pesho");

        String filtered = bugs
                .stream()
                .filter(bug -> params.get(2).equals(bug.getAssignee()) && bug.getStatus().toString().equals(params.get(1)))
                .map(Bug::toString)
                .collect(Collectors.joining(System.lineSeparator()));

        Assertions.assertEquals(filtered, listBugsCommand.execute(params));
    }
}
