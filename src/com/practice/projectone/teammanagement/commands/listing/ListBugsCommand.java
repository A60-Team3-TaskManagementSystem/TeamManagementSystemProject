package com.practice.projectone.teammanagement.commands.listing;

import com.practice.projectone.teammanagement.commands.BaseCommand;
import com.practice.projectone.teammanagement.core.contracts.TeamRepository;
import com.practice.projectone.teammanagement.models.contracts.Bug;
import com.practice.projectone.teammanagement.models.enums.Status;
import com.practice.projectone.teammanagement.utils.ValidationHelpers;

import java.util.Comparator;
import java.util.List;

public class ListBugsCommand extends BaseCommand {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 3;

    public ListBugsCommand(TeamRepository teamRepository) {
        super(teamRepository);
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        String sort = parameters.get(0);
        String filter1 = parameters.get(1);
        String filter2 = parameters.get(2);
        return listBugs(sort, filter1, filter2);
    }

    private String listBugs(String sort, String filter1, String filter2) {
        StringBuilder builder = new StringBuilder();
        List<Bug> bugs = getTeamRepository().getBugs();
        if (sort.equalsIgnoreCase("nosort")) {
            if (filter1.equalsIgnoreCase("nofilter")) {
                if (filter2.equalsIgnoreCase("nofilter")) {
                    bugs.stream().forEach(builder::append);
                } else {
                    bugs.stream().filter(bug -> bug.getAssignee().equals(filter2)).forEach(builder::append);
                }
            } else if (filter1.equalsIgnoreCase("active")) {
                if (filter2.equalsIgnoreCase("nofilter")) {
                    bugs.stream().filter(bug -> bug.getStatus() == Status.ACTIVE).forEach(builder::append);
                } else {
                    bugs.stream().filter(bug -> bug.getStatus() == Status.ACTIVE).filter(bug -> bug.getAssignee().equals(filter2)).forEach(builder::append);
                }
            } else if (filter1.equalsIgnoreCase("done")) {
                if (filter2.equalsIgnoreCase("nofilter")) {
                    bugs.stream().filter(bug -> bug.getStatus() == Status.DONE).forEach(builder::append);
                } else {
                    bugs.stream().filter(bug -> bug.getStatus() == Status.DONE).filter(bug -> bug.getAssignee().equals(filter2)).forEach(builder::append);
                }
            }
        } else if (sort.equalsIgnoreCase("title")) {
            if (filter1.equalsIgnoreCase("nofilter")) {
                if (filter2.equalsIgnoreCase("nofilter")) {
                    bugs.stream().sorted(Comparator.comparing(Bug::getName)).forEach(builder::append);
                } else {
                    bugs.stream().filter(bug -> bug.getAssignee().equals(filter2)).sorted(Comparator.comparing(Bug::getName)).forEach(builder::append);
                }
            } else if (filter1.equalsIgnoreCase("active")) {
                if (filter2.equalsIgnoreCase("nofilter")) {
                    bugs.stream().filter(bug -> bug.getStatus() == Status.ACTIVE).sorted(Comparator.comparing(Bug::getName)).forEach(builder::append);
                } else {
                    bugs.stream().filter(bug -> bug.getStatus() == Status.ACTIVE).sorted(Comparator.comparing(Bug::getName)).filter(bug -> bug.getAssignee().equals(filter2)).forEach(builder::append);
                }
            } else if (filter1.equalsIgnoreCase("done")) {
                if (filter2.equalsIgnoreCase("nofilter")) {
                    bugs.stream().filter(bug -> bug.getStatus() == Status.DONE).sorted(Comparator.comparing(Bug::getName)).forEach(builder::append);
                } else {
                    bugs.stream().filter(bug -> bug.getStatus() == Status.DONE).sorted(Comparator.comparing(Bug::getName)).filter(bug -> bug.getAssignee().equals(filter2)).forEach(builder::append);
                }
            }
        } else if (sort.equalsIgnoreCase("priority")) {
            if (filter1.equalsIgnoreCase("nofilter")) {
                if (filter2.equalsIgnoreCase("nofilter")) {
                    bugs.stream().sorted(Comparator.comparing(Bug::getPriority)).forEach(builder::append);
                } else {
                    bugs.stream().filter(bug -> bug.getAssignee().equals(filter2)).sorted(Comparator.comparing(Bug::getPriority)).forEach(builder::append);
                }
            } else if (filter1.equalsIgnoreCase("active")) {
                if (filter2.equalsIgnoreCase("nofilter")) {
                    bugs.stream().filter(bug -> bug.getStatus() == Status.ACTIVE).sorted(Comparator.comparing(Bug::getPriority)).forEach(builder::append);
                } else {
                    bugs.stream().filter(bug -> bug.getStatus() == Status.ACTIVE).sorted(Comparator.comparing(Bug::getPriority)).filter(bug -> bug.getAssignee().equals(filter2)).forEach(builder::append);
                }
            } else if (filter1.equalsIgnoreCase("done")) {
                if (filter2.equalsIgnoreCase("nofilter")) {
                    bugs.stream().filter(bug -> bug.getStatus() == Status.DONE).sorted(Comparator.comparing(Bug::getPriority)).forEach(builder::append);
                } else {
                    bugs.stream().filter(bug -> bug.getStatus() == Status.DONE).sorted(Comparator.comparing(Bug::getPriority)).filter(bug -> bug.getAssignee().equals(filter2)).forEach(builder::append);
                }
            }
        } else if (sort.equalsIgnoreCase("severity")) {
            if (filter1.equalsIgnoreCase("nofilter")) {
                if (filter2.equalsIgnoreCase("nofilter")) {
                    bugs.stream().sorted(Comparator.comparing(Bug::getSeverity)).forEach(builder::append);
                } else {
                    bugs.stream().filter(bug -> bug.getAssignee().equals(filter2)).sorted(Comparator.comparing(Bug::getSeverity)).forEach(builder::append);
                }
            } else if (filter1.equalsIgnoreCase("active")) {
                if (filter2.equalsIgnoreCase("nofilter")) {
                    bugs.stream().filter(bug -> bug.getStatus() == Status.ACTIVE).sorted(Comparator.comparing(Bug::getSeverity)).forEach(builder::append);
                } else {
                    bugs.stream().filter(bug -> bug.getStatus() == Status.ACTIVE).sorted(Comparator.comparing(Bug::getSeverity)).filter(bug -> bug.getAssignee().equals(filter2)).forEach(builder::append);
                }
            } else if (filter1.equalsIgnoreCase("done")) {
                if (filter2.equalsIgnoreCase("nofilter")) {
                    bugs.stream().filter(bug -> bug.getStatus() == Status.DONE).sorted(Comparator.comparing(Bug::getSeverity)).forEach(builder::append);
                } else {
                    bugs.stream().filter(bug -> bug.getStatus() == Status.DONE).sorted(Comparator.comparing(Bug::getSeverity)).filter(bug -> bug.getAssignee().equals(filter2)).forEach(builder::append);
                }
            }
        } else {
            throw new IllegalArgumentException("Invalid sorting parameter: should be \"title\", \"priority\", \"severity\" or \"nosort\"");
        }
        return builder.toString();
    }
}
