package com.practice.projectone.teammanagement.commands.listing;

import com.practice.projectone.teammanagement.commands.contracts.Command;
import com.practice.projectone.teammanagement.core.TaskManagementSystemImpl;
import com.practice.projectone.teammanagement.core.contracts.TaskManagementSystemRepository;
import com.practice.projectone.teammanagement.models.tasks.contracts.Feedback;
import com.practice.projectone.teammanagement.models.tasks.enums.Status;
import com.practice.projectone.teammanagement.utils.TestUtilities;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.practice.projectone.teammanagement.utils.Constants.*;

public class ListFeedbacksTests {
    private static final int MAX_ARGUMENT_COUNT = 2;
    private static final String INVALID_SORT = "asd";

    private TaskManagementSystemRepository repository;
    private Command listFeedbacksCommand;

    @BeforeEach
    public void beforeEach(){
        repository = new TaskManagementSystemImpl();
        listFeedbacksCommand = new ListFeedbacksCommand(repository);
    }

    @Test
    public void should_ThrowException_When_ArgumentCountIsAboveAllowed(){
        List<String> params = TestUtilities.getList(MAX_ARGUMENT_COUNT + 1);
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> listFeedbacksCommand.execute(params)
        );
    }

    @Test
    public void should_ThrowException_When_SortingArgumentIsInvalid(){
        List<String> params = List.of(INVALID_SORT);
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> listFeedbacksCommand.execute(params)
        );
    }

    @Test
    public void should_SortByTitle_When_SortingParamIsTitle(){
        repository.createFeedback(
                VALID_TITLE,
                VALID_DESCRIPTION,
                VALID_RATING
        );
        repository.createFeedback(
                "abc" + VALID_TITLE,
                VALID_DESCRIPTION,
                VALID_RATING
        );
        List<Feedback> feedbacks = repository.getFeedbacks();
        List<String> params = List.of("title");

        String sorted = feedbacks
                .stream()
                .sorted(Comparator.comparing(Feedback::getName))
                .map(Feedback::toString)
                .collect(Collectors.joining(System.lineSeparator()));
        Assertions.assertEquals(sorted, listFeedbacksCommand.execute(params));
    }

    @Test
    public void should_SortByRating_When_SortingParamIsRating(){
        repository.createFeedback(
                VALID_TITLE,
                VALID_DESCRIPTION,
                VALID_RATING + 1
        );
        repository.createFeedback(
                VALID_TITLE,
                VALID_DESCRIPTION,
                VALID_RATING
        );
        List<Feedback> feedbacks = repository.getFeedbacks();
        List<String> params = List.of("rating");

        String sorted = feedbacks
                .stream()
                .sorted(Comparator.comparing(Feedback::getRating))
                .map(Feedback::toString)
                .collect(Collectors.joining(System.lineSeparator()));
        Assertions.assertEquals(sorted, listFeedbacksCommand.execute(params));
    }

    @Test
    public void should_NotSort_When_SortingParamIsNosort(){
        repository.createFeedback(
                VALID_TITLE,
                VALID_DESCRIPTION,
                VALID_RATING
        );
        repository.createFeedback(
                "asd" + VALID_TITLE,
                VALID_DESCRIPTION,
                VALID_RATING
        );
        List<Feedback> feedbacks = repository.getFeedbacks();
        List<String> params = List.of("nosort");

        String sorted = feedbacks
                .stream()
                .map(Feedback::toString)
                .collect(Collectors.joining(System.lineSeparator()));
        Assertions.assertEquals(sorted, listFeedbacksCommand.execute(params));
    }

    @Test
    public void should_FilterByStatus_When_ThereIsFilter(){
        Feedback feedback1 = repository.createFeedback(
                VALID_TITLE,
                VALID_DESCRIPTION,
                VALID_RATING
        );
        Feedback feedback2 = repository.createFeedback(
                "asd" + VALID_TITLE,
                VALID_DESCRIPTION,
                VALID_RATING
        );

        repository.addFeedback(feedback1);
        repository.addFeedback(feedback2);

        repository.getFeedbacks().get(1).changeStatus(Status.DONE);
        List<Feedback> feedbacks = repository.getFeedbacks();
        List<String> params = List.of("nosort", "Done");

        String filtered = feedbacks
                .stream()
                .filter(feedback -> feedback.getStatus().toString().equals(params.get(1)))
                .map(Feedback::toString)
                .collect(Collectors.joining(System.lineSeparator()));

        Assertions.assertEquals(filtered, listFeedbacksCommand.execute(params));
    }

}
