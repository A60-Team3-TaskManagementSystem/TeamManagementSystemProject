package com.practice.projectone.teammanagement.core;

import com.practice.projectone.teammanagement.commands.contracts.Command;
import com.practice.projectone.teammanagement.commands.createcommands.CreateBugToBoardCommand;
import com.practice.projectone.teammanagement.commands.createcommands.CreateNewBoardInTeamCommand;
import com.practice.projectone.teammanagement.commands.createcommands.CreateStoryToBoardCommand;
import com.practice.projectone.teammanagement.commands.enums.CommandType;
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
                return new AddCommentCommand(vehicleDealershipRepository);
            case ADDVEHICLE:
                return new AddVehicleCommand(vehicleDealershipRepository);
            case REGISTERUSER:
                return new RegisterUserCommand(vehicleDealershipRepository);
            case SHOWVEHICLES:
                return new ShowVehiclesCommand(vehicleDealershipRepository);
            case REMOVECOMMENT:
                return new RemoveCommentCommand(vehicleDealershipRepository);
            case REMOVEVEHICLE:
                return new RemoveVehicleCommand(vehicleDealershipRepository);
            default:
                throw new IllegalArgumentException();
        }
    }

}
