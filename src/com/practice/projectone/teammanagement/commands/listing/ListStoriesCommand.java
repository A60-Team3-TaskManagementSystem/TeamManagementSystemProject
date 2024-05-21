package com.practice.projectone.teammanagement.commands.listing;

import com.practice.projectone.teammanagement.commands.BaseCommand;
import com.practice.projectone.teammanagement.core.contracts.TaskManagementSystemRepository;
import com.practice.projectone.teammanagement.models.tasks.contracts.Story;
import com.practice.projectone.teammanagement.models.tasks.enums.Status;
import com.practice.projectone.teammanagement.utils.ParsingHelpers;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ListStoriesCommand extends BaseCommand {

    public static final String INVALID_SORT_PARAMETER = "Invalid sorting parameter: should be \"title\", \"priority\", \"size\" or \"nosort\"!";

    public ListStoriesCommand(TaskManagementSystemRepository taskManagementSystemRepository) {
        super(taskManagementSystemRepository);
    }

    @Override
    public String execute(List<String> parameters) {
        if (parameters.size() > 3){
            throw new IllegalArgumentException("Argument count should be 3 or fewer!");
        }

        List<Story> stories = getTMSRepository().getStories();

        if (!parameters.isEmpty()) {
            String sortArgument = parameters.get(0);
            if (parameters.size() == 2) {
                String filter1 = parameters.get(1);
                stories = filterStories(stories, filter1);
            } else if (parameters.size() == 3) {
                Status status = ParsingHelpers.tryParseEnum(parameters.get(1), Status.class);
                String assigneeName = parameters.get(2);
                stories = filterStories(stories, status, assigneeName);
            }
            sortStories(stories, sortArgument);
        }

        return getResult(stories);
    }

    private List<Story> filterStories(List<Story> stories, String filter1) {
        return stories
                .stream()
                .filter(story -> filter1.equals(story.getAssignee()) || story.getStatus().toString().equals(filter1))
                .collect(Collectors.toList());
    }

    private List<Story> filterStories(List<Story> stories, Status status, String assigneeName) {
        return stories
                .stream()
                .filter(story -> assigneeName.equals(story.getAssignee()) && story.getStatus().equals(status))
                .collect(Collectors.toList());
    }

    private void sortStories(List<Story> stories, String sort) {
        switch(sort) {
            case "title":
                stories.sort(Comparator.comparing(Story::getName));
                break;
            case "priority":
                stories.sort(Comparator.comparing(Story::getPriority));
                break;
            case "size":
                stories.sort(Comparator.comparing(Story::getSize));
                break;
            case "nosort":
                break;
            default:
                throw new IllegalArgumentException(INVALID_SORT_PARAMETER);
        }
    }

    private String getResult(List<Story> stories) {
        return stories
                .stream()
                .map(Story::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }
}