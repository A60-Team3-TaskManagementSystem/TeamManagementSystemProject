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

        List<Feedback> feedbacks = getTMSRepository().getFeedbacks();

        if (!parameters.isEmpty()) {
            String sortArgument = parameters.get(0);
            if (parameters.size() == 2) {
                String filter = parameters.get(1);
                feedbacks = filterFeedbacks(feedbacks, filter);
            }
            sortFeedbacks(feedbacks, sortArgument);
        }
        return getResult(feedbacks);
    }

    private List<Feedback> filterFeedbacks(List<Feedback> feedbacks, String filter) {
        return feedbacks
                .stream()
                .filter(feedback -> feedback.getStatus().toString().equals(filter))
                .collect(Collectors.toList());
    }

    private void sortFeedbacks(List<Feedback> feedbacks, String sort) {
        switch (sort) {
            case "title":
                feedbacks.sort(Comparator.comparing(Feedback::getName));
                break;
            case "rating":
                feedbacks.sort(Comparator.comparing(Feedback::getRating));
                break;
            case "nosort":
                break;
            default:
                throw new IllegalArgumentException(INVALID_SORT_PARAMETER);
        }
    }

    private String getResult(List<Feedback> feedbacks) {
        return feedbacks
                .stream()
                .map(Feedback::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
