package com.practice.projectone.teammanagement.commands.listing;

import com.practice.projectone.teammanagement.commands.BaseCommand;
import com.practice.projectone.teammanagement.core.contracts.TaskManagementSystemRepository;
import com.practice.projectone.teammanagement.models.tasks.contracts.Bug;
import com.practice.projectone.teammanagement.models.tasks.enums.Status;
import com.practice.projectone.teammanagement.utils.ParsingHelpers;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ListBugsCommand extends BaseCommand {
    public static final int OR_FILTER = 2;
    public static final int AND_FILTER = 3;
    public static final String INVALID_SORT_PARAMETER = "Invalid sorting parameter: should be \"title\", \"priority\", \"severity\" or \"nosort\"!";

    public ListBugsCommand(TaskManagementSystemRepository taskManagementSystemRepository) {
        super(taskManagementSystemRepository);
    }

    @Override
    public String execute(List<String> parameters) {
        if (parameters.size() > 3) {
            throw new IllegalArgumentException("Argument count should be 3 or fewer!");
        }

        List<Bug> bugs = getTMSRepository().getBugs();

        if (!parameters.isEmpty()) {
            String sortArgument = parameters.get(0);

            if (parameters.size() == OR_FILTER) {
                String filter1 = parameters.get(1);

                bugs = filterBugs(bugs, filter1);

            } else if (parameters.size() == AND_FILTER) {
                Status status = ParsingHelpers.tryParseEnum(parameters.get(1), Status.class);
                String assigneeName = parameters.get(2);

                bugs = filterBugs(bugs, status, assigneeName);
            }

            sortBugs(bugs, sortArgument);
        }

        return getResult(bugs);
    }

    private List<Bug> filterBugs(List<Bug> bugs, String filter1) {
        return bugs.stream()
                .filter(bug -> filter1.equals(bug.getAssignee()) || bug.getStatus().toString().equals(filter1))
                .collect(Collectors.toList());
    }

    private List<Bug> filterBugs(List<Bug> bugs, Status status, String assigneeName) {
        return bugs.stream()
                .filter(bug -> assigneeName.equals(bug.getAssignee()) && bug.getStatus().equals(status))
                .collect(Collectors.toList());
    }

    private void sortBugs(List<Bug> bugs, String sort) {
        switch (sort) {
            case "title":
                bugs.sort(Comparator.comparing(Bug::getName));
                break;
            case "priority":
                bugs.sort(Comparator.comparing(Bug::getPriority));
                break;
            case "severity":
                bugs.sort(Comparator.comparing(Bug::getSeverity));
                break;
            case "nosort":
                break;
            default:
                throw new IllegalArgumentException(INVALID_SORT_PARAMETER);
        }
    }

    private String getResult(List<Bug> bugs) {
        return bugs.stream()
                .map(Bug::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
