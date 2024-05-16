package com.practice.projectone.teammanagement.models.tasks.contracts;

import com.practice.projectone.teammanagement.models.tasks.enums.Size;

public interface Story extends Task, AssigneeAble, PrioritizeAble {
    Size getSize();

    public void changeSize(Size newSize);
}
