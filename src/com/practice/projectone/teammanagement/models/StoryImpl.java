package com.practice.projectone.teammanagement.models;

import com.practice.projectone.teammanagement.exceptions.InvalidTaskException;
import com.practice.projectone.teammanagement.exceptions.InvalidUserInputException;
import com.practice.projectone.teammanagement.models.contracts.Story;
import com.practice.projectone.teammanagement.models.enums.Priority;
import com.practice.projectone.teammanagement.models.enums.Severity;
import com.practice.projectone.teammanagement.models.enums.Size;
import com.practice.projectone.teammanagement.models.enums.Status;

public class StoryImpl extends Content implements Story {
    private static final String SIZE_CHANGED = "The size of item with ID %d switched from %s to %s";
    private static final String SIZE_SAME_ERR = "Can't change, size already at %s";
    private static final Status INITIAL_STATUS = Status.NOT_DONE;

    private Size size;

    public StoryImpl(String title, String description, Priority priority, Size size) {
        super(title, description, INITIAL_STATUS, priority);
        this.size = size;
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
    public void changeSeverity(Severity severity) {
        throw new InvalidTaskException("Bug/Story doesn't have severity");
    }

    @Override
    protected void validateStatus(Status status) {
        if (!status.getTaskType().equals("Story") && !status.getTaskType().equals("All")) {
            throw new IllegalArgumentException("Please provide valid story status");
        }
    }
}
