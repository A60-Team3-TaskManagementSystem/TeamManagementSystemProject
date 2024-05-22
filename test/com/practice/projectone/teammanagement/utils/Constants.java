package com.practice.projectone.teammanagement.utils;

import com.practice.projectone.teammanagement.models.tasks.enums.Priority;
import com.practice.projectone.teammanagement.models.tasks.enums.Size;

public class Constants {
    public static final int TITLE_LEN_MIN = 10;
    public static final int TITLE_LEN_MAX = 100;
    public static final int DESCRIPTION_LEN_MIN = 10;
    public static final int DESCRIPTION_LEN_MAX = 500;
    public static final int RATING_MIN_VALUE = 0;

    public static final String VALID_TITLE = TestUtilities.getString(TITLE_LEN_MIN + 1);
    public static final String VALID_DESCRIPTION = TestUtilities.getString(DESCRIPTION_LEN_MIN + 1);
    public static final int VALID_RATING = RATING_MIN_VALUE + 1;
    public static final Size VALID_STORY_SIZE = Size.MEDIUM;
    public static final Priority VALID_SPECIFIC_TASK_PRIORITY = Priority.MEDIUM;
    public static final String INVALID_TITLE_MIN = TestUtilities.getString(TITLE_LEN_MIN - 1);
    public static final String INVALID_TITLE_MAX = TestUtilities.getString(TITLE_LEN_MAX + 1);
    public static final String INVALID_DESCRIPTION_MIN = TestUtilities.getString(DESCRIPTION_LEN_MIN - 1);
    public static final String INVALID_DESCRIPTION_MAX = TestUtilities.getString(DESCRIPTION_LEN_MAX + 1);
    public static final int INVALID_RATING = RATING_MIN_VALUE - 1;
}
