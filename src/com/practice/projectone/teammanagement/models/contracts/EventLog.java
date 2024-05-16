package com.practice.projectone.teammanagement.models.contracts;

import java.time.LocalDateTime;

public interface EventLog {
    String getDescription();

    LocalDateTime getTimestamp();
}
