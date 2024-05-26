package com.practice.projectone.teammanagement.commands.listing;

import com.practice.projectone.teammanagement.commands.contracts.Command;
import com.practice.projectone.teammanagement.core.TaskManagementSystemImpl;
import com.practice.projectone.teammanagement.core.contracts.TaskManagementSystemRepository;
import com.practice.projectone.teammanagement.models.tasks.contracts.Story;
import com.practice.projectone.teammanagement.models.tasks.enums.Priority;
import com.practice.projectone.teammanagement.models.tasks.enums.Size;
import com.practice.projectone.teammanagement.models.tasks.enums.Status;
import com.practice.projectone.teammanagement.utils.TestUtilities;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.practice.projectone.teammanagement.utils.Constants.*;

public class ListStoriesCommandTests {
    private static final int MAX_ARGUMENT_COUNT = 3;
    private static final String INVALID_SORT = "asd";

    private TaskManagementSystemRepository repository;
    private Command listStoriesCommand;

    @BeforeEach
    public void beforeEach(){
        repository = new TaskManagementSystemImpl();
        listStoriesCommand = new ListStoriesCommand(repository);
    }

    @Test
    public void should_ThrowException_When_ArgumentCountIsAboveAllowed(){
        List<String> params = TestUtilities.getList(MAX_ARGUMENT_COUNT + 1);
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> listStoriesCommand.execute(params)
        );
    }

    @Test
    public void should_ThrowException_When_SortingArgumentIsInvalid(){
        List<String> params = List.of(INVALID_SORT);
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> listStoriesCommand.execute(params)
        );
    }

    @Test
    public void should_SortByTitle_When_SortingParamIsTitle(){
        repository.createStory(
                VALID_TITLE,
                VALID_DESCRIPTION,
                VALID_SPECIFIC_TASK_PRIORITY,
                VALID_STORY_SIZE
        );
        repository.createStory(
                "abc" + VALID_TITLE,
                VALID_DESCRIPTION,
                VALID_SPECIFIC_TASK_PRIORITY,
                VALID_STORY_SIZE
        );
        List<Story> stories = repository.getStories();
        List<String> params = List.of("title");

        String sorted = stories
                .stream()
                .sorted(Comparator.comparing(Story::getName))
                .map(Story::toString)
                .collect(Collectors.joining(System.lineSeparator()));
        Assertions.assertEquals(sorted, listStoriesCommand.execute(params));
    }

    @Test
    public void should_SortByPriority_When_SortingParamIsPriority(){
        repository.createStory(
                VALID_TITLE,
                VALID_DESCRIPTION,
                VALID_SPECIFIC_TASK_PRIORITY,
                VALID_STORY_SIZE
        );
        repository.createStory(
                VALID_TITLE,
                VALID_DESCRIPTION,
                Priority.HIGH,
                VALID_STORY_SIZE
        );
        List<Story> stories = repository.getStories();
        List<String> params = List.of("priority");

        String sorted = stories
                .stream()
                .sorted(Comparator.comparing(Story::getPriority))
                .map(Story::toString)
                .collect(Collectors.joining(System.lineSeparator()));
        Assertions.assertEquals(sorted, listStoriesCommand.execute(params));
    }

    @Test
    public void should_SortBySize_When_SortingParamIsSize(){
        repository.createStory(
                VALID_TITLE,
                VALID_DESCRIPTION,
                VALID_SPECIFIC_TASK_PRIORITY,
                VALID_STORY_SIZE
        );
        repository.createStory(
                VALID_TITLE,
                VALID_DESCRIPTION,
                VALID_SPECIFIC_TASK_PRIORITY,
                Size.LARGE
        );
        List<Story> stories = repository.getStories();
        List<String> params = List.of("size");

        String sorted = stories
                .stream()
                .sorted(Comparator.comparing(Story::getSize))
                .map(Story::toString)
                .collect(Collectors.joining(System.lineSeparator()));
        Assertions.assertEquals(sorted, listStoriesCommand.execute(params));
    }
    @Test
    public void should_NotSort_When_SortingParamIsNosort(){
        repository.createStory(
                VALID_TITLE,
                VALID_DESCRIPTION,
                VALID_SPECIFIC_TASK_PRIORITY,
                VALID_STORY_SIZE
        );
        repository.createStory(
                "abc" + VALID_TITLE,
                VALID_DESCRIPTION,
                VALID_SPECIFIC_TASK_PRIORITY,
                VALID_STORY_SIZE
        );
        List<Story> stories = repository.getStories();
        List<String> params = List.of("nosort");

        String sorted = stories
                .stream()
                .map(Story::toString)
                .collect(Collectors.joining(System.lineSeparator()));
        Assertions.assertEquals(sorted, listStoriesCommand.execute(params));
    }

    @Test
    public void should_FilterByStatusORAssignee_When_OnlyOneFilter(){
        repository.createStory(
                VALID_TITLE,
                VALID_DESCRIPTION,
                VALID_SPECIFIC_TASK_PRIORITY,
                VALID_STORY_SIZE
        );
        repository.createStory(
                "abc" + VALID_TITLE,
                VALID_DESCRIPTION,
                VALID_SPECIFIC_TASK_PRIORITY,
                VALID_STORY_SIZE
        );
        repository.getStories().get(1).changeStatus(Status.DONE);
        repository.createPerson("Pesho");
        repository.getStories().get(0).changeAssignee("Pesho");
        List<Story> stories = repository.getStories();
        List<String> params = List.of("nosort", "Pesho");

        String filtered = stories
                .stream()
                .filter(story -> params.get(1).equals(story.getAssignee()) || story.getStatus().toString().equals(params.get(1)))
                .map(Story::toString)
                .collect(Collectors.joining(System.lineSeparator()));

        Assertions.assertEquals(filtered, listStoriesCommand.execute(params));
    }

    @Test
    public void should_FilterByStatusANDAssignee_When_TwoFilters(){
        repository.createStory(
                VALID_TITLE,
                VALID_DESCRIPTION,
                VALID_SPECIFIC_TASK_PRIORITY,
                VALID_STORY_SIZE
        );
        repository.createStory(
                "abc" + VALID_TITLE,
                VALID_DESCRIPTION,
                VALID_SPECIFIC_TASK_PRIORITY,
                VALID_STORY_SIZE
        );
        repository.getStories().get(1).changeStatus(Status.DONE);
        repository.createPerson("Pesho");
        repository.getStories().get(0).changeAssignee("Pesho");
        List<Story> stories = repository.getStories();
        List<String> params = List.of("nosort", "Active", "Pesho");

        String filtered = stories
                .stream()
                .filter(story -> params.get(2).equals(story.getAssignee()) && story.getStatus().toString().equals(params.get(1)))
                .map(Story::toString)
                .collect(Collectors.joining(System.lineSeparator()));

        Assertions.assertEquals(filtered, listStoriesCommand.execute(params));
    }
}
