package com.practice.projectone.teammanagement.models.contracts;

import com.practice.projectone.teammanagement.models.enums.Size;

public interface Story extends Task{
    Size getSize();
    void changeSize(Size size);
}
