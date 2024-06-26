package com.practice.projectone.teammanagement.models.contracts;

import java.util.List;

public interface ActivityAble extends Nameable {
    List<EventLog> getActivityHistory();

    default String viewActivity() {
        StringBuilder sb = new StringBuilder();

        String className = this.getClass()
                .getSimpleName()
                .substring(0, this.getClass().getSimpleName().length() - 4)
                .toUpperCase();

        sb.append(String.format("****%s %s****", className, getName())).append(System.lineSeparator());

        List<EventLog> eventLogs = getActivityHistory();

        sb.append("--ACTIVITY HISTORY--").append(System.lineSeparator());
        eventLogs.forEach(sb::append);
        sb.append("--ACTIVITY HISTORY--").append(System.lineSeparator());

        return sb.toString();
    }
}
