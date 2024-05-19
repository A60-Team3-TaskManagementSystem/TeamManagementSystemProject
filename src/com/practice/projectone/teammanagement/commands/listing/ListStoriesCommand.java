package com.practice.projectone.teammanagement.commands.listing;

import com.practice.projectone.teammanagement.commands.BaseCommand;
import com.practice.projectone.teammanagement.core.contracts.TeamRepository;
import com.practice.projectone.teammanagement.models.tasks.contracts.Story;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ListStoriesCommand extends BaseCommand {

    public static final String INVALID_SORT_PARAMETER = "Invalid sorting parameter: should be \"title\", \"priority\", \"size\" or \"nosort\"!";

    public ListStoriesCommand(TeamRepository teamRepository) {
        super(teamRepository);
    }

    @Override
    public String execute(List<String> parameters) {
        if (parameters.size() > 3){
            throw new IllegalArgumentException("Argument count should be 3 or fewer!");
        }
        if (parameters.size() == 1){
            String sort = parameters.get(0);
            return listStories(sort);
        } else if (parameters.size() == 2) {
            String sort = parameters.get(0);
            String filter1 = parameters.get(1);
            return listStories(sort, filter1);
        } else if (parameters.size() == 3) {
            String sort = parameters.get(0);
            String filter1 = parameters.get(1);
            String filter2 = parameters.get(2);
            return listStories(sort, filter1, filter2);
        }
        return listAllStories();
    }

    private String listStories(String sort) {
        String result;
        switch (sort) {
            case "title":
                result = getTeamRepository().getStories()
                        .stream()
                        .sorted(Comparator.comparing(Story::getName))
                        .map(Story::toString)
                        .collect(Collectors.joining(System.lineSeparator()));
                break;
            case "priority":
                result = getTeamRepository().getStories()
                        .stream()
                        .sorted(Comparator.comparing(Story::getPriority))
                        .map(Story::toString)
                        .collect(Collectors.joining(System.lineSeparator()));
                break;
            case "size":
                result = getTeamRepository().getStories()
                        .stream()
                        .sorted(Comparator.comparing(Story::getSize))
                        .map(Story::toString)
                        .collect(Collectors.joining(System.lineSeparator()));
                break;
            case "nosort":
                return listAllStories();
            default:
                throw new IllegalArgumentException(INVALID_SORT_PARAMETER);
        }
        return result;
    }

    private String listStories(String sort, String filter1) {
        String result;
        switch (sort) {
            case "title":
                result = getTeamRepository().getStories()
                        .stream()
                        .filter(story -> filter1.equals(story.getAssignee()) || story.getStatus().toString().equals(filter1))
                        .sorted(Comparator.comparing(Story::getName))
                        .map(Story::toString)
                        .collect(Collectors.joining(System.lineSeparator()));
                break;
            case "priority":
                result = getTeamRepository().getStories()
                        .stream()
                        .filter(story -> filter1.equals(story.getAssignee()) || story.getStatus().toString().equals(filter1))
                        .sorted(Comparator.comparing(Story::getPriority))
                        .map(Story::toString)
                        .collect(Collectors.joining(System.lineSeparator()));
                break;
            case "size":
                result = getTeamRepository().getStories()
                        .stream()
                        .filter(story -> filter1.equals(story.getAssignee()) || story.getStatus().toString().equals(filter1))
                        .sorted(Comparator.comparing(Story::getSize))
                        .map(Story::toString)
                        .collect(Collectors.joining(System.lineSeparator()));
                break;
            case "nosort":
                result = getTeamRepository().getStories()
                        .stream()
                        .filter(story -> filter1.equals(story.getAssignee()) || story.getStatus().toString().equals(filter1))
                        .map(Story::toString)
                        .collect(Collectors.joining(System.lineSeparator()));
                break;
            default:
                throw new IllegalArgumentException(INVALID_SORT_PARAMETER);
        }
        return result;
    }

    private String listStories(String sort, String filter1, String filter2) {
        String result;
        switch (sort) {
            case "title":
                result = getTeamRepository().getStories()
                        .stream()
                        .filter(story -> filter1.equals(story.getAssignee()))
                        .filter(story -> story.getStatus().toString().equals(filter2))
                        .sorted(Comparator.comparing(Story::getName))
                        .map(Story::toString)
                        .collect(Collectors.joining(System.lineSeparator()));
                break;
            case "priority":
                result = getTeamRepository().getStories()
                        .stream()
                        .filter(story -> filter1.equals(story.getAssignee()))
                        .filter(story -> story.getStatus().toString().equals(filter2))
                        .sorted(Comparator.comparing(Story::getPriority))
                        .map(Story::toString)
                        .collect(Collectors.joining(System.lineSeparator()));
                break;
            case "size":
                result = getTeamRepository().getStories()
                        .stream()
                        .filter(story -> filter1.equals(story.getAssignee()))
                        .filter(story -> story.getStatus().toString().equals(filter2))
                        .sorted(Comparator.comparing(Story::getSize))
                        .map(Story::toString)
                        .collect(Collectors.joining(System.lineSeparator()));
                break;
            case "nosort":
                result = getTeamRepository().getStories()
                        .stream()
                        .filter(story -> filter1.equals(story.getAssignee()))
                        .filter(story -> story.getStatus().toString().equals(filter2))
                        .map(Story::toString)
                        .collect(Collectors.joining(System.lineSeparator()));
                break;
            default:
                throw new IllegalArgumentException(INVALID_SORT_PARAMETER);
        }
        return result;
    }

    private String listAllStories() {
        return getTeamRepository().getStories()
                .stream()
                .map(Story::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
