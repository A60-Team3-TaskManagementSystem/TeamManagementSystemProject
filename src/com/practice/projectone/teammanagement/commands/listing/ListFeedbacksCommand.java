package com.practice.projectone.teammanagement.commands.listing;

import com.practice.projectone.teammanagement.commands.BaseCommand;
import com.practice.projectone.teammanagement.core.contracts.TeamRepository;
import com.practice.projectone.teammanagement.models.contracts.Feedback;
import com.practice.projectone.teammanagement.models.enums.Status;
import com.practice.projectone.teammanagement.utils.ValidationHelpers;

import java.util.Comparator;
import java.util.List;

public class ListFeedbacksCommand extends BaseCommand {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 2;

    public ListFeedbacksCommand(TeamRepository teamRepository) {
        super(teamRepository);
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        String sort = parameters.get(0);
        String filter = parameters.get(1);
        return listFeedbacks(sort, filter);
    }

    private String listFeedbacks(String sort, String filter) {
        StringBuilder builder = new StringBuilder();
        List<Feedback> feedbacks = getTeamRepository().getFeedbacks();
        if (sort.equalsIgnoreCase("nosort")){
            if (filter.equalsIgnoreCase("nofilter")){
                feedbacks.stream().forEach(builder::append);
            } else if (filter.equalsIgnoreCase("new")){
                feedbacks.stream().filter(feedback -> feedback.getStatus() == Status.NEW).forEach(builder::append);
            } else if (filter.equalsIgnoreCase("unscheduled")){
                feedbacks.stream().filter(feedback -> feedback.getStatus() == Status.UNSCHEDULED).forEach(builder::append);
            } else if (filter.equalsIgnoreCase("scheduled")){
                feedbacks.stream().filter(feedback -> feedback.getStatus() == Status.SCHEDULED).forEach(builder::append);
            } else if (filter.equalsIgnoreCase("done")){
                feedbacks.stream().filter(feedback -> feedback.getStatus() == Status.DONE).forEach(builder::append);
            }
        } else if (sort.equalsIgnoreCase("title")){
            if (filter.equalsIgnoreCase("nofilter")){
                feedbacks.stream().sorted(Comparator.comparing(Feedback::getName)).forEach(builder::append);
            } else if (filter.equalsIgnoreCase("new")){
                feedbacks.stream().filter(feedback -> feedback.getStatus() == Status.NEW).sorted(Comparator.comparing(Feedback::getName)).forEach(builder::append);
            } else if (filter.equalsIgnoreCase("unscheduled")){
                feedbacks.stream().filter(feedback -> feedback.getStatus() == Status.UNSCHEDULED).sorted(Comparator.comparing(Feedback::getName)).forEach(builder::append);
            } else if (filter.equalsIgnoreCase("scheduled")){
                feedbacks.stream().filter(feedback -> feedback.getStatus() == Status.SCHEDULED).sorted(Comparator.comparing(Feedback::getName)).forEach(builder::append);
            } else if (filter.equalsIgnoreCase("done")){
                feedbacks.stream().filter(feedback -> feedback.getStatus() == Status.DONE).sorted(Comparator.comparing(Feedback::getName)).forEach(builder::append);
            }
        } else if (sort.equalsIgnoreCase("rating")){
            if (filter.equalsIgnoreCase("nofilter")){
                feedbacks.stream().sorted(Comparator.comparing(Feedback::getRating)).forEach(builder::append);
            } else if (filter.equalsIgnoreCase("new")){
                feedbacks.stream().filter(feedback -> feedback.getStatus() == Status.NEW).sorted(Comparator.comparing(Feedback::getRating)).forEach(builder::append);
            } else if (filter.equalsIgnoreCase("unscheduled")){
                feedbacks.stream().filter(feedback -> feedback.getStatus() == Status.UNSCHEDULED).sorted(Comparator.comparing(Feedback::getRating)).forEach(builder::append);
            } else if (filter.equalsIgnoreCase("scheduled")){
                feedbacks.stream().filter(feedback -> feedback.getStatus() == Status.SCHEDULED).sorted(Comparator.comparing(Feedback::getRating)).forEach(builder::append);
            } else if (filter.equalsIgnoreCase("done")){
                feedbacks.stream().filter(feedback -> feedback.getStatus() == Status.DONE).sorted(Comparator.comparing(Feedback::getRating)).forEach(builder::append);
            }
        } else {
            throw new IllegalArgumentException("Invalid sorting parameter: should be \"title\", \"rating\" or \"nosort\"");
        }
        return builder.toString();
    }
}
