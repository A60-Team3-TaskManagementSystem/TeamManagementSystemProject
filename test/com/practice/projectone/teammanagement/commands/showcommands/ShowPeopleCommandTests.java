package com.practice.projectone.teammanagement.commands.showcommands;

import com.practice.projectone.teammanagement.commands.contracts.Command;
import com.practice.projectone.teammanagement.core.TaskManagementSystemImpl;
import com.practice.projectone.teammanagement.core.contracts.TaskManagementSystemRepository;
import com.practice.projectone.teammanagement.models.contracts.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.practice.projectone.teammanagement.utils.Constants.VALID_NAME;

public class ShowPeopleCommandTests {
    private TaskManagementSystemRepository repository;
    private Command showPeople;

    @BeforeEach
    void beforeEach() {
        repository = new TaskManagementSystemImpl();
        showPeople = new ShowPeople(repository);
    }

    @Test
    public void execute_Should_Report_When_NoPeopleInRepo() {
        List<String> validParams = List.of();

        String execute = showPeople.execute(validParams);

        Assertions.assertEquals(1, execute.split(System.lineSeparator()).length);
    }

    @Test
    public void execute_Should_ReturnBoardActivity_When_InputIsValid() {
        Person person = repository.createPerson(VALID_NAME);
        repository.addPerson(person);

        List<String> validParams = List.of();

        String execute = showPeople.execute(validParams);

        Assertions.assertEquals(2, execute.split(System.lineSeparator()).length);
    }
}
