package com.practice.projectone.teammanagement.models.tasks.enums;

public enum Size {
    LARGE,
    MEDIUM,
    SMALL;

    @Override
    public String toString() {
        switch (this) {
            case LARGE:
                return "Large";
            case MEDIUM:
                return "Medium";
            case SMALL:
                return "Small";
            default:
                return "Unknown";
        }
    }
}
