package com.practice.projectone.teammanagement.models;

import com.practice.projectone.teammanagement.models.contracts.EventLog;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class EventLogImpl implements EventLog {

    private final String DESCRIPTION_EMPTY_ERROR = "Description must not be empty";

    private String description;
    private final LocalDateTime timestamp;

    public EventLogImpl () {
        throw new IllegalArgumentException(DESCRIPTION_EMPTY_ERROR);
    }

    public EventLogImpl (String description) {
        setDescription(description);
        timestamp = LocalDateTime.now();
    }

    private void setDescription(String description) {
        if (description.isEmpty()){
            throw new IllegalArgumentException(DESCRIPTION_EMPTY_ERROR);
        }
        this.description = description;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString(){
        DateTimeFormatter date = DateTimeFormatter.ofPattern("dd-MMMM-y HH:mm:ss", Locale.ENGLISH);
        String formattedDate = getTimestamp().format(date);
        return String.format("[%s] %s%n", formattedDate, getDescription());
    }
}
