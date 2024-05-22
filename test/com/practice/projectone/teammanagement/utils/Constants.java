package com.practice.projectone.teammanagement.utils;

public class Constants {
    public static final int TITLE_LEN_MIN = 10;
    public static final int DESCRIPTION_LEN_MIN = 10;
    public static final int RATING_MIN_VALUE = 0;

    public static final String VALID_TITLE = TestUtilities.getString(TITLE_LEN_MIN + 1);
    public static final String VALID_DESCRIPTION = TestUtilities.getString(DESCRIPTION_LEN_MIN + 1);
    public static final int VALID_RATING = RATING_MIN_VALUE + 1;
    public static final String INVALID_TITLE = TestUtilities.getString(TITLE_LEN_MIN - 1);
    public static final String INVALID_DESCRIPTION = TestUtilities.getString(DESCRIPTION_LEN_MIN - 1);
    public static final int INVALID_RATING = RATING_MIN_VALUE - 1;
}
