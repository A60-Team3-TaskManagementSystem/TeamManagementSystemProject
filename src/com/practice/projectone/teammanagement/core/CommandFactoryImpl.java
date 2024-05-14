package com.practice.projectone.teammanagement.core;

import com.practice.projectone.teammanagement.commands.contracts.Command;
import com.practice.projectone.teammanagement.commands.enums.CommandType;
import com.practice.projectone.teammanagement.core.contracts.CommandFactory;
import com.practice.projectone.teammanagement.core.contracts.TeamRepository;
import com.practice.projectone.teammanagement.utils.ParsingHelpers;

public class CommandFactoryImpl implements CommandFactory {

    @Override
    public Command createCommandFromCommandName(String commandTypeAsString, TeamRepository teamRepository) {
        CommandType commandType = ParsingHelpers.tryParseEnum(commandTypeAsString, CommandType.class);
        switch (commandType) {
            case ADD_BOARD_TO_TEAM:
                return new LoginCommand(vehicleDealershipRepository);
            case LOGOUT:
                return new LogoutCommand(vehicleDealershipRepository);
            case SHOWUSERS:
                return new ShowUsersCommand(vehicleDealershipRepository);
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
