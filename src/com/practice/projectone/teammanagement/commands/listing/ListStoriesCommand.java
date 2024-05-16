package com.practice.projectone.teammanagement.commands.listing;

import com.practice.projectone.teammanagement.commands.BaseCommand;
import com.practice.projectone.teammanagement.core.contracts.TeamRepository;
import com.practice.projectone.teammanagement.models.contracts.Bug;
import com.practice.projectone.teammanagement.models.contracts.Story;
import com.practice.projectone.teammanagement.models.enums.Status;
import com.practice.projectone.teammanagement.utils.ValidationHelpers;

import java.util.Comparator;
import java.util.List;

public class ListStoriesCommand extends BaseCommand {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 3;

    public ListStoriesCommand(TeamRepository teamRepository) {
        super(teamRepository);
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        String sort = parameters.get(0);
        String filter1 = parameters.get(1);
        String filter2 = parameters.get(2);
        return listStories(sort, filter1, filter2);
    }

    private String listStories(String sort, String filter1, String filter2) {
        StringBuilder builder = new StringBuilder();
        List<Story> stories = getTeamRepository().getStories();
        if (sort.equalsIgnoreCase("nosort")) {
            if (filter1.equalsIgnoreCase("nofilter")) {
                if (filter2.equalsIgnoreCase("nofilter")) {
                    stories.stream().forEach(builder::append);
                } else {
                    stories.stream().filter(story -> story.getAssignee().equals(filter2)).forEach(builder::append);
                }
            } else if (filter1.equalsIgnoreCase("notdone")) {
                if (filter2.equalsIgnoreCase("nofilter")) {
                    stories.stream().filter(story -> story.getStatus() == Status.NOT_DONE).forEach(builder::append);
                } else {
                    stories.stream().filter(story -> story.getStatus() == Status.NOT_DONE).filter(story -> story.getAssignee().equals(filter2)).forEach(builder::append);
                }
            } else if (filter1.equalsIgnoreCase("inprogress")) {
                if (filter2.equalsIgnoreCase("nofilter")) {
                    stories.stream().filter(story -> story.getStatus() == Status.IN_PROGRESS).forEach(builder::append);
                } else {
                    stories.stream().filter(story -> story.getStatus() == Status.IN_PROGRESS).filter(story -> story.getAssignee().equals(filter2)).forEach(builder::append);
                }
            } else if (filter1.equalsIgnoreCase("done")) {
                if (filter2.equalsIgnoreCase("nofilter")) {
                    stories.stream().filter(story -> story.getStatus() == Status.DONE).forEach(builder::append);
                } else {
                    stories.stream().filter(story -> story.getStatus() == Status.DONE).filter(story -> story.getAssignee().equals(filter2)).forEach(builder::append);
                }
            }
        } else if (sort.equalsIgnoreCase("title")) {
            if (filter1.equalsIgnoreCase("nofilter")) {
                if (filter2.equalsIgnoreCase("nofilter")) {
                    stories.stream().sorted(Comparator.comparing(Story::getName)).forEach(builder::append);
                } else {
                    stories.stream().filter(story -> story.getAssignee().equals(filter2)).sorted(Comparator.comparing(Story::getName)).forEach(builder::append);
                }
            } else if (filter1.equalsIgnoreCase("notdone")) {
                if (filter2.equalsIgnoreCase("nofilter")) {
                    stories.stream().filter(story -> story.getStatus() == Status.NOT_DONE).sorted(Comparator.comparing(Story::getName)).forEach(builder::append);
                } else {
                    stories.stream().filter(story -> story.getStatus() == Status.NOT_DONE).sorted(Comparator.comparing(Story::getName)).filter(story -> story.getAssignee().equals(filter2)).forEach(builder::append);
                }
            } else if (filter1.equalsIgnoreCase("inprogress")) {
                if (filter2.equalsIgnoreCase("nofilter")) {
                    stories.stream().filter(story -> story.getStatus() == Status.IN_PROGRESS).sorted(Comparator.comparing(Story::getName)).forEach(builder::append);
                } else {
                    stories.stream().filter(story -> story.getStatus() == Status.IN_PROGRESS).sorted(Comparator.comparing(Story::getName)).filter(story -> story.getAssignee().equals(filter2)).forEach(builder::append);
                }
            } else if (filter1.equalsIgnoreCase("done")) {
                if (filter2.equalsIgnoreCase("nofilter")) {
                    stories.stream().filter(story -> story.getStatus() == Status.DONE).sorted(Comparator.comparing(Story::getName)).forEach(builder::append);
                } else {
                    stories.stream().filter(story -> story.getStatus() == Status.DONE).sorted(Comparator.comparing(Story::getName)).filter(story -> story.getAssignee().equals(filter2)).forEach(builder::append);
                }
            }
        } else if (sort.equalsIgnoreCase("priority")) {
            if (filter1.equalsIgnoreCase("nofilter")) {
                if (filter2.equalsIgnoreCase("nofilter")) {
                    stories.stream().sorted(Comparator.comparing(Story::getPriority)).forEach(builder::append);
                } else {
                    stories.stream().filter(story -> story.getAssignee().equals(filter2)).sorted(Comparator.comparing(Story::getPriority)).forEach(builder::append);
                }
            } else if (filter1.equalsIgnoreCase("notdone")) {
                if (filter2.equalsIgnoreCase("nofilter")) {
                    stories.stream().filter(story -> story.getStatus() == Status.NOT_DONE).sorted(Comparator.comparing(Story::getPriority)).forEach(builder::append);
                } else {
                    stories.stream().filter(story -> story.getStatus() == Status.NOT_DONE).sorted(Comparator.comparing(Story::getPriority)).filter(story -> story.getAssignee().equals(filter2)).forEach(builder::append);
                }
            } else if (filter1.equalsIgnoreCase("inprogress")) {
                if (filter2.equalsIgnoreCase("nofilter")) {
                    stories.stream().filter(story -> story.getStatus() == Status.IN_PROGRESS).sorted(Comparator.comparing(Story::getPriority)).forEach(builder::append);
                } else {
                    stories.stream().filter(story -> story.getStatus() == Status.IN_PROGRESS).sorted(Comparator.comparing(Story::getPriority)).filter(story -> story.getAssignee().equals(filter2)).forEach(builder::append);
                }
            } else if (filter1.equalsIgnoreCase("done")) {
                if (filter2.equalsIgnoreCase("nofilter")) {
                    stories.stream().filter(story -> story.getStatus() == Status.DONE).sorted(Comparator.comparing(Story::getPriority)).forEach(builder::append);
                } else {
                    stories.stream().filter(story -> story.getStatus() == Status.DONE).sorted(Comparator.comparing(Story::getPriority)).filter(story -> story.getAssignee().equals(filter2)).forEach(builder::append);
                }
            }
        } else if (sort.equalsIgnoreCase("size")) {
            if (filter1.equalsIgnoreCase("nofilter")) {
                if (filter2.equalsIgnoreCase("nofilter")) {
                    stories.stream().sorted(Comparator.comparing(Story::getSize)).forEach(builder::append);
                } else {
                    stories.stream().filter(story -> story.getAssignee().equals(filter2)).sorted(Comparator.comparing(Story::getSize)).forEach(builder::append);
                }
            } else if (filter1.equalsIgnoreCase("notdone")) {
                if (filter2.equalsIgnoreCase("nofilter")) {
                    stories.stream().filter(story -> story.getStatus() == Status.NOT_DONE).sorted(Comparator.comparing(Story::getSize)).forEach(builder::append);
                } else {
                    stories.stream().filter(story -> story.getStatus() == Status.NOT_DONE).sorted(Comparator.comparing(Story::getSize)).filter(story -> story.getAssignee().equals(filter2)).forEach(builder::append);
                }
            } else if (filter1.equalsIgnoreCase("inprogress")) {
                if (filter2.equalsIgnoreCase("nofilter")) {
                    stories.stream().filter(story -> story.getStatus() == Status.IN_PROGRESS).sorted(Comparator.comparing(Story::getSize)).forEach(builder::append);
                } else {
                    stories.stream().filter(story -> story.getStatus() == Status.IN_PROGRESS).sorted(Comparator.comparing(Story::getSize)).filter(story -> story.getAssignee().equals(filter2)).forEach(builder::append);
                }
            } else if (filter1.equalsIgnoreCase("done")) {
                if (filter2.equalsIgnoreCase("nofilter")) {
                    stories.stream().filter(story -> story.getStatus() == Status.DONE).sorted(Comparator.comparing(Story::getSize)).forEach(builder::append);
                } else {
                    stories.stream().filter(story -> story.getStatus() == Status.DONE).sorted(Comparator.comparing(Story::getSize)).filter(story -> story.getAssignee().equals(filter2)).forEach(builder::append);
                }
            }
        } else {
            throw new IllegalArgumentException("Invalid sorting parameter: should be \"title\", \"priority\", \"size\" or \"nosort\"");
        }
        return builder.toString();
    }
}
