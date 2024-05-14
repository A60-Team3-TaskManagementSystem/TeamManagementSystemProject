package com.practice.projectone.teammanagement.models.contracts;

import com.practice.projectone.teammanagement.models.enums.Priority;

public interface Prioritizable {
    Priority getPriority();
    void changePriority(Priority priority);
}
