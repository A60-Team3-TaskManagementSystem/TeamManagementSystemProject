package com.practice.projectone.teammanagement.utils;

import com.practice.projectone.teammanagement.models.contracts.Nameable;
import com.practice.projectone.teammanagement.models.tasks.contracts.*;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class ListingHelper {

    public static final String INVALID_SORT_PARAMETER = "Invalid sorting parameter: should be \"title\", \"priority\", \"size\" or \"nosort\"!";


    public static <T extends Task> List<? extends Task> sort(List<T> tasks, String sort) {
        switch (sort) {
            case "title":
                tasks.sort(Comparator.comparing(Nameable::getName));
                break;
            case "priority":
                SpecificTask[] specificTasks = tasks.toArray(new SpecificTask[0]);
                Arrays.sort((specificTasks), Comparator.comparing(SpecificTask::getPriority));
                return List.of(specificTasks);
            case "severity":
                Arrays.sort(tasks.toArray(new Bug[0]), Comparator.comparing(Bug::getSeverity));
                break;
            case "size":
                Arrays.sort(tasks.toArray(new Story[0]), Comparator.comparing(Story::getSize));
                break;
            case "rating":
                Arrays.sort(tasks.toArray(new Feedback[0]), Comparator.comparing(Feedback::getRating));
                break;
            case "nosort":
                break;
            default:
                throw new IllegalArgumentException(INVALID_SORT_PARAMETER);
        }

        return null;
    }
}
