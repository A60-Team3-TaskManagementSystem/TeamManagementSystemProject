package com.practice.projectone.teammanagement.models.tasks;

import com.practice.projectone.teammanagement.exceptions.InvalidUserInputException;
import com.practice.projectone.teammanagement.models.EventLogImpl;
import com.practice.projectone.teammanagement.models.tasks.contracts.Story;
import com.practice.projectone.teammanagement.models.tasks.enums.Priority;
import com.practice.projectone.teammanagement.models.tasks.enums.Size;
import com.practice.projectone.teammanagement.models.tasks.enums.Status;

public class StoryImpl extends SpecificTaskImpl implements Story {
    private static final String SIZE_CHANGED = "The size of item with ID %d switched from %s to %s";
    private static final String SIZE_SAME_ERR = "Can't change, size already at %s";
    private static final Status INITIAL_STATUS = Status.NOT_DONE;

    private Size size;

    public StoryImpl(String title, String description, Priority priority, Size size) {
        super(title, description, INITIAL_STATUS, priority);
        this.size = size;
    }

    @Override
    public String getTaskType() {
        return getClass().getSimpleName().substring(0, getClass().getSimpleName().length() - 4);
    }

    @Override
    public Size getSize() {
        return size;
    }

    @Override
    public void changeSize(Size newSize) {
        Size oldSize = getSize();

        if (newSize.equals(oldSize)) {
            throw new InvalidUserInputException(String.format(SIZE_SAME_ERR, newSize));
        }

        this.size = newSize;
        addEventToHistory(new EventLogImpl(String.format(SIZE_CHANGED, getId(), oldSize, newSize)));
    }

    @Override
    public String toString() {
        return String.format("%s  #Size: %s%n  #AssignedTo: %s%n", super.toString(), size, getAssignee()).trim();
    }
    @Override
    protected void validateStatus(Status status) {
        if (!status.getTaskType().equals("Story") && !status.getTaskType().equals("All")) {
            throw new IllegalArgumentException("Please provide valid story status");
        }
    }
}
