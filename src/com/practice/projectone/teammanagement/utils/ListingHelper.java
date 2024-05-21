package com.practice.projectone.teammanagement.utils;

import com.practice.projectone.teammanagement.models.tasks.contracts.*;
import com.practice.projectone.teammanagement.utils.comparators.TaskNameComparator;
import com.practice.projectone.teammanagement.utils.comparators.TaskPriorityComparator;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ListingHelper {

    public static final String INVALID_SORT_PARAMETER = "Invalid sorting parameter: should be \"title\", \"priority\", \"size\" or \"nosort\"!";


    public static <T extends Task> void sort(List<T> tasks, String sort) {
        switch (sort) {
            case "title":
                TaskNameComparator taskNameComparator = new TaskNameComparator();
                tasks.sort(taskNameComparator);
                break;
            case "priority":
                TaskPriorityComparator taskPriorityComparator = new TaskPriorityComparator()
                tasks.sort(taskPriorityComparator);
                break;
            case "severity":
                tasks.sort(Comparator.comparing());
                break;
            case "size":
                tasks.sort(Comparator.comparing(Story::getSize));
                break;
            case "rating":
                tasks.sort(Comparator.comparing(Feedback::getRating));
                break;
            case "nosort":
                break;
            default:
                throw new IllegalArgumentException(INVALID_SORT_PARAMETER);
        }
    }
}
