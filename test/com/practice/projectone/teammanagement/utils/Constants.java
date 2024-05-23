package com.practice.projectone.teammanagement.utils;

import com.practice.projectone.teammanagement.models.tasks.enums.Priority;
import com.practice.projectone.teammanagement.models.tasks.enums.Severity;
import com.practice.projectone.teammanagement.models.tasks.enums.Size;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Constants {
    public static final int TITLE_LEN_MIN = 10;
    public static final int TITLE_LEN_MAX = 100;
    public static final int DESCRIPTION_LEN_MIN = 10;
    public static final int DESCRIPTION_LEN_MAX = 500;
    public static final int RATING_MIN_VALUE = 0;
    public static final int AUTHOR_MIN_SYMBOLS = 5;
    public static final int AUTHOR_MAX_SYMBOLS = 15;
    public static final int COMMENT_MIN_LENGTH = 3;
    public static final int COMMENT_MAX_LENGTH = 200;
    public static final int NAME_MIN_LENGTH = 5;
    public static final int NAME_MAX_LENGTH = 15;

    public static final String VALID_TITLE = TestUtilities.getString(TITLE_LEN_MIN + 1);
    public static final String VALID_DESCRIPTION = TestUtilities.getString(DESCRIPTION_LEN_MIN + 1);
    public static final int VALID_RATING = RATING_MIN_VALUE + 1;
    public static final Size VALID_STORY_SIZE = Size.MEDIUM;
    public static final Priority VALID_SPECIFIC_TASK_PRIORITY = Priority.MEDIUM;
    public static final Severity VALID_SEVERITY = Severity.MAJOR;
    public static final List<String> VALID_STEPS_LIST = Arrays.asList("step1", "step2");
    public static final String VALID_AUTHOR_NAME = TestUtilities.getString(AUTHOR_MIN_SYMBOLS + 1);
    public static final String VALID_COMMENT = TestUtilities.getString(COMMENT_MIN_LENGTH + 1);
    public static final String VALID_NAME = TestUtilities.getString(NAME_MIN_LENGTH + 1);

    public static final String INVALID_TITLE_MIN = TestUtilities.getString(TITLE_LEN_MIN - 1);
    public static final String INVALID_TITLE_MAX = TestUtilities.getString(TITLE_LEN_MAX + 1);
    public static final String INVALID_DESCRIPTION_MIN = TestUtilities.getString(DESCRIPTION_LEN_MIN - 1);
    public static final String INVALID_DESCRIPTION_MAX = TestUtilities.getString(DESCRIPTION_LEN_MAX + 1);
    public static final int INVALID_RATING = RATING_MIN_VALUE - 1;
    public static final List<String> INVALID_STEPS_LIST = new ArrayList<>(0);
    public static final String INVALID_AUTHOR_NAME_MIN = TestUtilities.getString(AUTHOR_MIN_SYMBOLS - 1);
    public static final String INVALID_AUTHOR_NAME_MAX = TestUtilities.getString(AUTHOR_MAX_SYMBOLS + 1);
    public static final String INVALID_COMMENT_MIN = TestUtilities.getString(COMMENT_MIN_LENGTH - 1);
    public static final String INVALID_COMMENT_MAX = TestUtilities.getString(COMMENT_MAX_LENGTH + 1);
    public static final String INVALID_NAME_MIN = TestUtilities.getString(NAME_MIN_LENGTH - 1);
    public static final String INVALID_NAME_MAX = TestUtilities.getString(NAME_MAX_LENGTH + 1);
}
