package com.practice.projectone.teammanagement.commands.showcommands;

import com.practice.projectone.teammanagement.commands.BaseCommand;
import com.practice.projectone.teammanagement.core.contracts.TaskManagementSystemRepository;
import com.practice.projectone.teammanagement.models.contracts.Board;
import com.practice.projectone.teammanagement.models.contracts.Person;
import com.practice.projectone.teammanagement.models.contracts.Team;
import com.practice.projectone.teammanagement.utils.ValidationHelpers;

import java.util.List;

public class ShowTeamActivityCommand extends BaseCommand {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;

    public ShowTeamActivityCommand(TaskManagementSystemRepository taskManagementSystemRepository) {
        super(taskManagementSystemRepository);
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        String teamName = parameters.get(0);

        return showTeamActivity(teamName);
    }

    private String showTeamActivity(String teamName) {
        Team team = getTMSRepository().findTeamByName(teamName);

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("****TEAM %s ACTIVITY****", teamName)).append(System.lineSeparator());

        List<Person> members = team.getMembers();
        List<Board> boards = team.getBoards();

        if (members.isEmpty() && boards.isEmpty()) {
            sb.append("--NO RECENT ACTIVITY--").append(System.lineSeparator());

            return sb.toString().trim();
        }

        if (members.isEmpty()) {
            sb.append("--NO MEMBER ACTIVITY--").append(System.lineSeparator());
        } else {
            members.forEach(member -> sb.append(member.viewActivity()));
        }

        if (boards.isEmpty()) {
            sb.append("--NO BOARD ACTIVITY--").append(System.lineSeparator());
        } else {
            boards.forEach(board -> sb.append(board.viewActivity()));
        }

        sb.append(String.format("****TEAM %s ACTIVITY****", teamName)).append(System.lineSeparator());

        return sb.toString().trim();
    }
}
