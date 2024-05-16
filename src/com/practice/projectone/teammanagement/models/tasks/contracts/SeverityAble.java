package com.practice.projectone.teammanagement.models.tasks.contracts;

import com.practice.projectone.teammanagement.models.tasks.enums.Severity;

public interface SeverityAble {
    Severity getSeverity();

    public void changeSeverity(Severity newSeverity);
}
