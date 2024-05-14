package com.practice.projectone.teammanagement.commands;

import com.practice.projectone.teammanagement.core.contracts.TeamRepository;
import com.practice.projectone.teammanagement.models.enums.Priority;
import com.practice.projectone.teammanagement.models.enums.Size;
import com.practice.projectone.teammanagement.models.enums.Status;
import com.practice.projectone.teammanagement.utils.ParsingHelpers;
import com.practice.projectone.teammanagement.utils.ValidationHelpers;

import java.util.List;

public class CreateTaskCommand extends BaseCommand {
    private static final int EXPECTED_PARAMETERS_COUNT = 4;
    public static final String INVALID_RATING = "Invalid value for rating. Should be a number.";
    private static final Status BUG_INITIAL_STATUS = Status.ACTIVE;


    public CreateTaskCommand(TeamRepository teamRepository) {
        super(teamRepository);
    }

    @Override
    public String execute(List<String> parameters) {
//        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_PARAMETERS_COUNT);
//
//        String boardName = parameters.get(0);
//        String title = parameters.get(1);
//        String description = parameters.get(2);
//
//        if (parameters.size() == EXPECTED_PARAMETERS_COUNT) {
//
//            double rating = ParsingHelpers.tryParseDouble(parameters.get(3), INVALID_RATING);
//
//        } else if (parameters.size() == EXPECTED_PARAMETERS_COUNT + 1) {
//
//            Priority priority = ParsingHelpers.tryParseEnum(parameters.get(3), Priority.class);
//            Size size = ParsingHelpers.tryParseEnum(parameters.get(4), Size.class);
//
//
//        } else if (())
        return null;
    }

//    private String createTask(){
//
//    }
}
