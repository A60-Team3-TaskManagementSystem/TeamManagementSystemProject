package com.practice.projectone.teammanagement.models.contracts;

import java.util.List;

public interface ActivityAble {
    List<EventLog> getActivityHistory();

    String viewActivity();
}
