package com.practice.projectone.teammanagement.core;

import com.practice.projectone.teammanagement.commands.AddCommentToTaskCommand;
import com.practice.projectone.teammanagement.commands.AddTeamMemberCommand;
import com.practice.projectone.teammanagement.commands.AssignTaskToMemberCommand;
import com.practice.projectone.teammanagement.commands.UnassignTaskToMemberCommand;
import com.practice.projectone.teammanagement.commands.changecommands.*;
import com.practice.projectone.teammanagement.commands.contracts.Command;
import com.practice.projectone.teammanagement.commands.createcommands.*;
import com.practice.projectone.teammanagement.commands.enums.CommandType;
import com.practice.projectone.teammanagement.commands.listing.*;
import com.practice.projectone.teammanagement.commands.showcommands.*;
import com.practice.projectone.teammanagement.core.contracts.CommandFactory;
import com.practice.projectone.teammanagement.core.contracts.TaskManagementSystemRepository;
import com.practice.projectone.teammanagement.utils.ParsingHelpers;

public class CommandFactoryImpl implements CommandFactory {

    @Override
    public Command createCommandFromCommandName(String commandTypeAsString, TaskManagementSystemRepository taskManagementSystemRepository) {
        CommandType commandType = ParsingHelpers.tryParseEnum(commandTypeAsString, CommandType.class);
        switch (commandType) {
            case CREATENEWBOARDINTEAM:
                return new CreateNewBoardInTeamCommand(taskManagementSystemRepository);
            case CREATEBUG:
                return new CreateBugToBoardCommand(taskManagementSystemRepository);
            case CREATESTORY:
                return new CreateStoryToBoardCommand(taskManagementSystemRepository);
            case ADDCOMMENT:
                return new AddCommentToTaskCommand(taskManagementSystemRepository);
            case CREATEFEEDBACK:
                return new CreateFeedbackToBoardCommand(taskManagementSystemRepository);
            case CREATEPERSON:
                return new CreatePersonCommand(taskManagementSystemRepository);
            case CREATETEAM:
                return new CreateTeamCommand(taskManagementSystemRepository);
            case ADDTEAMMEMBER:
                return new AddTeamMemberCommand(taskManagementSystemRepository);
            case ASSIGNTASK:
                return new AssignTaskToMemberCommand(taskManagementSystemRepository);
            case UNASSIGNTASK:
                return new UnassignTaskToMemberCommand(taskManagementSystemRepository);
            case SHOWBOARDS:
                return new ShowAllTeamBoardsCommand(taskManagementSystemRepository);
            case SHOWBOARDACTIVITY:
                return new ShowBoardActivityCommand(taskManagementSystemRepository);
            case SHOWPEOPLE:
                return new ShowPeople(taskManagementSystemRepository);
            case SHOWPERSONACTIVITY:
                return new ShowPersonActivityLog(taskManagementSystemRepository);
            case SHOWTEAMMEMBERS:
                return new ShowTeamMembers(taskManagementSystemRepository);
            case SHOWTEAMACTIVITY:
                return new ShowTeamActivityCommand(taskManagementSystemRepository);
            case SHOWTEAMS:
                return new ShowTeams(taskManagementSystemRepository);
            case CHANGEPRIORITY:
                return new ChangePriorityCommand(taskManagementSystemRepository);
            case CHANGERATING:
                return new ChangeRatingCommand(taskManagementSystemRepository);
            case CHANGESEVERITY:
                return new ChangeSeverityCommand(taskManagementSystemRepository);
            case CHANGESIZE:
                return new ChangeSizeCommand(taskManagementSystemRepository);
            case CHANGESTATUS:
                return new ChangeStatusCommand(taskManagementSystemRepository);
            case LISTTASKS:
                return new ListTasksCommand(taskManagementSystemRepository);
            case LISTBUGS:
                return new ListBugsCommand(taskManagementSystemRepository);
            case LISTSTORIES:
                return new ListStoriesCommand(taskManagementSystemRepository);
            case LISTFEEDBACKS:
                return new ListFeedbacksCommand(taskManagementSystemRepository);
            case LISTASSIGNEDTASKS:
                return new ListOnlyAssignedTasksCommand(taskManagementSystemRepository);
            default:
                throw new IllegalArgumentException("UNKNOWN COMMAND");
        }
    }

}
