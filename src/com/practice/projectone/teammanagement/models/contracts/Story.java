package com.practice.projectone.teammanagement.models.contracts;

import com.practice.projectone.teammanagement.models.enums.Size;

public interface Story extends Task, Assignable, Prioritizable{
    Size getSize();
}
