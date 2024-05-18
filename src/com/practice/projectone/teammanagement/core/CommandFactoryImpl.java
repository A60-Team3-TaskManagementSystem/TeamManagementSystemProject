package com.practice.projectone.teammanagement.core;

import com.practice.projectone.teammanagement.commands.AddCommentToTaskCommand;
import com.practice.projectone.teammanagement.commands.AddTeamMember;
import com.practice.projectone.teammanagement.commands.AssignTaskToMember;
import com.practice.projectone.teammanagement.commands.UnassignTaskToMember;
import com.practice.projectone.teammanagement.commands.changecommands.*;
import com.practice.projectone.teammanagement.commands.contracts.Command;
import com.practice.projectone.teammanagement.commands.createcommands.*;
import com.practice.projectone.teammanagement.commands.enums.CommandType;
import com.practice.projectone.teammanagement.commands.listing.*;
import com.practice.projectone.teammanagement.commands.showcommands.*;
import com.practice.projectone.teammanagement.core.contracts.CommandFactory;
import com.practice.projectone.teammanagement.core.contracts.TeamRepository;
import com.practice.projectone.teammanagement.utils.ParsingHelpers;

public class CommandFactoryImpl implements CommandFactory {

    @Override
    public Command createCommandFromCommandName(String commandTypeAsString, TeamRepository teamRepository) {
        CommandType commandType = ParsingHelpers.tryParseEnum(commandTypeAsString, CommandType.class);
        switch (commandType) {
            case CREATENEWBOARDINTEAM:
                return new CreateNewBoardInTeamCommand(teamRepository);
            case CREATEBUG:
                return new CreateBugToBoardCommand(teamRepository);
            case CREATESTORY:
                return new CreateStoryToBoardCommand(teamRepository);
            case ADDCOMMENT:
                return new AddCommentToTaskCommand(teamRepository);
            case CREATEFEEDBACK:
                return new CreateFeedbackToBoardCommand(teamRepository);
            case CREATEPERSON:
                return new CreatePerson(teamRepository);
            case CREATETEAM:
                return new CreateTeam(teamRepository);
            case ADDTEAMMEMBER:
                return new AddTeamMember(teamRepository);
            case ASSIGNTASK:
                return new AssignTaskToMember(teamRepository);
            case UNASSIGNTASK:
                return new UnassignTaskToMember(teamRepository);
            case SHOWBOARDS:
                return new ShowAllTeamBoardsCommand(teamRepository);
            case SHOWBOARDACTIVITY:
                return new ShowBoardActivityCommand(teamRepository);
            case SHOWPEOPLE:
                return new ShowPeople(teamRepository);
            case SHOWPERSONACTIVITY:
                return new ShowPersonActivityLog(teamRepository);
            case SHOWTEAMMEMBERS:
                return new ShowTeamMembers(teamRepository);
            case SHOWTEAMS:
                return new ShowTeams(teamRepository);
            case CHANGEPRIORITY:
                return new ChangePriorityCommand(teamRepository);
            case CHANGERATING:
                return new ChangeRatingCommand(teamRepository);
            case CHANGESEVERITY:
                return new ChangeSeverityCommand(teamRepository);
            case CHANGESIZE:
                return new ChangeSizeCommand(teamRepository);
            case CHANGESTATUS:
                return new ChangeStatusCommand(teamRepository);
            case LISTTASKS:
                return new ListAllTasksAlternativeCommand(teamRepository);
            case LISTBUGS:
                return new ListBugsCommand(teamRepository);
            case LISTSTORIES:
                return new ListStoriesCommand(teamRepository);
            case LISTFEEDBACKS:
                return new ListFeedbacksCommand(teamRepository);
            case LISTASSIGNEDTASKS:
                return new ListOnlyAssignedTasksCommand(teamRepository);
            default:
                throw new IllegalArgumentException("UNKNOWN COMMAND");
        }
    }

}
