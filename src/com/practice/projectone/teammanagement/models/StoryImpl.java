package com.practice.projectone.teammanagement.models;

import com.practice.projectone.teammanagement.exceptions.InvalidUserInputException;
import com.practice.projectone.teammanagement.models.contracts.Story;
import com.practice.projectone.teammanagement.models.enums.Priority;
import com.practice.projectone.teammanagement.models.enums.Size;
import com.practice.projectone.teammanagement.models.enums.Status;

public class StoryImpl extends Content implements Story {

    private Size size;

    public StoryImpl (String title, String description, Status status, Priority priority, Size size, String assigneeName) {
        super (title, description, status, priority, assigneeName);
        this.size = size;
    }

    @Override
    public Size getSize() {
        return size;
    }

    @Override
    public void changeSize(Size size) {
        if (size.equals(getSize())) {
            throw new InvalidUserInputException(String.format("Can't change, size already at %s", size));
        }

        this.size = size;
        addEventToHistory(new EventLogImpl(String.format("Task size changed to %s", size)));
    }


    @Override
    public void changePriority(Priority priority) {
        if (priority.equals(getPriority())) {
            throw new InvalidUserInputException(String.format("Can't change, priority already at %s", priority));
        }

        setPriority(priority);
        addEventToHistory(new EventLogImpl(String.format("Task priority changed to %s", priority)));
    }

}
