package com.practice.projectone.teammanagement.commands.listing;

import com.practice.projectone.teammanagement.commands.BaseCommand;
import com.practice.projectone.teammanagement.core.contracts.TaskManagementSystemRepository;
import com.practice.projectone.teammanagement.models.tasks.contracts.Feedback;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ListFeedbacksCommand extends BaseCommand {

    public static final String INVALID_SORT_PARAMETER = "Invalid sorting parameter: should be \"title\", \"rating\" or \"nosort\"!";

    public ListFeedbacksCommand(TaskManagementSystemRepository taskManagementSystemRepository) {
        super(taskManagementSystemRepository);
    }

    @Override
    public String execute(List<String> parameters) {
        if (parameters.size() > 2){
            throw new IllegalArgumentException("Argument count should be 2 or fewer!");
        }
        if (parameters.size() == 1){
            String sort = parameters.get(0);
            return listFeedbacks(sort);
        } else if (parameters.size() == 2) {
            String sort = parameters.get(0);
            String filter = parameters.get(1);
            return listFeedbacks(sort, filter);
        }
        return listAllFeedbacks();
    }

    private String listFeedbacks(String sort) {
        String result;
        switch (sort) {
            case "title":
                result = getTeamRepository().getFeedbacks()
                        .stream()
                        .sorted(Comparator.comparing(Feedback::getName))
                        .map(Feedback::toString)
                        .collect(Collectors.joining(System.lineSeparator()));
                break;
            case "rating":
                result = getTeamRepository().getFeedbacks()
                        .stream()
                        .sorted(Comparator.comparing(Feedback::getRating))
                        .map(Feedback::toString)
                        .collect(Collectors.joining(System.lineSeparator()));
                break;
            case "nosort":
                return listAllFeedbacks();
            default:
                throw new IllegalArgumentException(INVALID_SORT_PARAMETER);
        }
        return result;
    }

    private String listFeedbacks(String sort, String filter) {
        String result;
        switch (sort) {
            case "title":
                result = getTeamRepository().getFeedbacks()
                        .stream()
                        .filter(feedback -> feedback.getStatus().toString().equals(filter))
                        .sorted(Comparator.comparing(Feedback::getName))
                        .map(Feedback::toString)
                        .collect(Collectors.joining(System.lineSeparator()));
                break;
            case "rating":
                result = getTeamRepository().getFeedbacks()
                        .stream()
                        .filter(feedback -> feedback.getStatus().toString().equals(filter))
                        .sorted(Comparator.comparing(Feedback::getRating))
                        .map(Feedback::toString)
                        .collect(Collectors.joining(System.lineSeparator()));
                break;
            case "nosort":
                result = getTeamRepository().getFeedbacks()
                        .stream()
                        .filter(feedback -> feedback.getStatus().toString().equals(filter))
                        .map(Feedback::toString)
                        .collect(Collectors.joining(System.lineSeparator()));
                break;
            default:
                throw new IllegalArgumentException(INVALID_SORT_PARAMETER);
        }
        return result;
    }

    private String listAllFeedbacks() {
        return getTeamRepository().getFeedbacks()
                .stream()
                .map(Feedback::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
