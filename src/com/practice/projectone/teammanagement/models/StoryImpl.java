package com.practice.projectone.teammanagement.models;

import com.practice.projectone.teammanagement.models.contracts.Member;
import com.practice.projectone.teammanagement.models.contracts.Story;
import com.practice.projectone.teammanagement.models.enums.Priority;
import com.practice.projectone.teammanagement.models.enums.Size;
import com.practice.projectone.teammanagement.models.enums.StatusType;

public class StoryImpl extends Content implements Story {

    private Size size;

    public StoryImpl (int id, String title, String description, StatusType status, Priority priority, Size size, Member assignee) {
        super (id, title, description, status, priority, assignee);
        this.size = size;
    }

    @Override
    public Size getSize() {
        return size;
    }

    @Override
    protected void revertStatus() {

    }

    @Override
    protected void advanceStatus() {

    }
}
