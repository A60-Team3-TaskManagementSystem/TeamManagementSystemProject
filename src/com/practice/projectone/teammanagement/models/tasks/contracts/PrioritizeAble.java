package com.practice.projectone.teammanagement.models.tasks.contracts;

import com.practice.projectone.teammanagement.models.contracts.Identifiable;
import com.practice.projectone.teammanagement.models.tasks.enums.Priority;

public interface PrioritizeAble extends Identifiable {
    Priority getPriority();
    public void changePriority(Priority newPriority);
}
