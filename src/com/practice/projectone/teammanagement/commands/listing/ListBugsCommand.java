package com.practice.projectone.teammanagement.commands.listing;

import com.practice.projectone.teammanagement.commands.BaseCommand;
import com.practice.projectone.teammanagement.core.contracts.TaskManagementSystemRepository;
import com.practice.projectone.teammanagement.models.tasks.contracts.Bug;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ListBugsCommand extends BaseCommand {

    public static final String INVALID_SORT_PARAMETER = "Invalid sorting parameter: should be \"title\", \"priority\", \"severity\" or \"nosort\"!";

    public ListBugsCommand(TaskManagementSystemRepository taskManagementSystemRepository) {
        super(taskManagementSystemRepository);
    }

    @Override
    public String execute(List<String> parameters) {
        if (parameters.size() > 3){
            throw new IllegalArgumentException("Argument count should be 3 or fewer!");
        }
        if (parameters.size() == 1){
            String sort = parameters.get(0);
            return listBugs(sort);
        } else if (parameters.size() == 2) {
            String sort = parameters.get(0);
            String filter1 = parameters.get(1);
            return listBugs(sort, filter1);
        } else if (parameters.size() == 3) {
            String sort = parameters.get(0);
            String filter1 = parameters.get(1);
            String filter2 = parameters.get(2);
            return listBugs(sort, filter1, filter2);
        }
        return listAllBugs();
    }

    private String listBugs(String sort) {
        String result;
        switch (sort) {
            case "title":
                result = getTeamRepository().getBugs()
                        .stream()
                        .sorted(Comparator.comparing(Bug::getName))
                        .map(Bug::toString)
                        .collect(Collectors.joining(System.lineSeparator()));
                break;
            case "priority":
                result = getTeamRepository().getBugs()
                        .stream()
                        .sorted(Comparator.comparing(Bug::getPriority))
                        .map(Bug::toString)
                        .collect(Collectors.joining(System.lineSeparator()));
                break;
            case "severity":
                result = getTeamRepository().getBugs()
                        .stream()
                        .sorted(Comparator.comparing(Bug::getSeverity))
                        .map(Bug::toString)
                        .collect(Collectors.joining(System.lineSeparator()));
                break;
            case "nosort":
                return listAllBugs();
            default:
                throw new IllegalArgumentException(INVALID_SORT_PARAMETER);
        }
        return result;
    }

    private String listBugs(String sort, String filter1) {
        String result;
        switch (sort) {
            case "title":
                result = getTeamRepository().getBugs()
                        .stream()
                        .filter(bug -> filter1.equals(bug.getAssignee()) || bug.getStatus().toString().equals(filter1))
                        .sorted(Comparator.comparing(Bug::getName))
                        .map(Bug::toString)
                        .collect(Collectors.joining(System.lineSeparator()));
                break;
            case "priority":
                result = getTeamRepository().getBugs()
                        .stream()
                        .filter(bug -> filter1.equals(bug.getAssignee()) || bug.getStatus().toString().equals(filter1))
                        .sorted(Comparator.comparing(Bug::getPriority))
                        .map(Bug::toString)
                        .collect(Collectors.joining(System.lineSeparator()));
                break;
            case "severity":
                result = getTeamRepository().getBugs()
                        .stream()
                        .filter(bug -> filter1.equals(bug.getAssignee()) || bug.getStatus().toString().equals(filter1))
                        .sorted(Comparator.comparing(Bug::getSeverity))
                        .map(Bug::toString)
                        .collect(Collectors.joining(System.lineSeparator()));
                break;
            case "nosort":
                result = getTeamRepository().getBugs()
                        .stream()
                        .filter(bug -> filter1.equals(bug.getAssignee()) || bug.getStatus().toString().equals(filter1))
                        .map(Bug::toString)
                        .collect(Collectors.joining(System.lineSeparator()));
                break;
            default:
                throw new IllegalArgumentException(INVALID_SORT_PARAMETER);
        }
        return result;
    }

    private String listBugs(String sort, String filter1, String filter2) {
        String result;
        switch (sort) {
            case "title":
                result = getTeamRepository().getBugs()
                        .stream()
                        .filter(bug -> filter1.equals(bug.getAssignee()))
                        .filter(bug -> bug.getStatus().toString().equals(filter2))
                        .sorted(Comparator.comparing(Bug::getName))
                        .map(Bug::toString)
                        .collect(Collectors.joining(System.lineSeparator()));
                break;
            case "priority":
                result = getTeamRepository().getBugs()
                        .stream()
                        .filter(bug -> filter1.equals(bug.getAssignee()))
                        .filter(bug -> bug.getStatus().toString().equals(filter2))
                        .sorted(Comparator.comparing(Bug::getPriority))
                        .map(Bug::toString)
                        .collect(Collectors.joining(System.lineSeparator()));
                break;
            case "severity":
                result = getTeamRepository().getBugs()
                        .stream()
                        .filter(bug -> filter1.equals(bug.getAssignee()))
                        .filter(bug -> bug.getStatus().toString().equals(filter2))
                        .sorted(Comparator.comparing(Bug::getSeverity))
                        .map(Bug::toString)
                        .collect(Collectors.joining(System.lineSeparator()));
                break;
            case "nosort":
                result = getTeamRepository().getBugs()
                        .stream()
                        .filter(bug -> filter1.equals(bug.getAssignee()))
                        .filter(bug -> bug.getStatus().toString().equals(filter2))
                        .map(Bug::toString)
                        .collect(Collectors.joining(System.lineSeparator()));
                break;
            default:
                throw new IllegalArgumentException(INVALID_SORT_PARAMETER);
        }
        return result;
    }

    private String listAllBugs() {
        return getTeamRepository().getBugs()
                .stream()
                .map(Bug::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
