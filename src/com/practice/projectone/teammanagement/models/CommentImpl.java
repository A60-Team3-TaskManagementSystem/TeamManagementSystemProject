package com.practice.projectone.teammanagement.models;

import com.practice.projectone.teammanagement.models.contracts.Comment;
import com.practice.projectone.teammanagement.utils.ValidationHelpers;

public class CommentImpl implements Comment {

    private final int AUTHOR_MIN_SYMBOLS = 5;
    private final int AUTHOR_MAX_SYMBOLS = 15;
    private final int DESCRIPTION_MIN_LENGTH = 3;
    private final int DESCRIPTION_MAX_LENGTH = 200;
    private final String AUTHOR_NAME_ERROR = String.format(
            "Author name should be between %d and %d symbols.",
            AUTHOR_MIN_SYMBOLS,
            AUTHOR_MAX_SYMBOLS
    );
    private final String DESCRIPTION_LENGTH_ERROR = String.format(
            "Comment must be between %d and %d symbols",
            DESCRIPTION_MIN_LENGTH,
            DESCRIPTION_MAX_LENGTH
    );
    private String author;
    private String description;

    public CommentImpl (String author, String description){
        setAuthor(author);
        setDescription(description);
    }

    private void setAuthor(String author) {
        ValidationHelpers.validateStringLength(
                author,
                AUTHOR_MIN_SYMBOLS,
                AUTHOR_MAX_SYMBOLS,
                AUTHOR_NAME_ERROR
        );
        this.author = author;
    }

    private void setDescription(String description) {
        ValidationHelpers.validateStringLength(
                description,
                DESCRIPTION_MIN_LENGTH,
                DESCRIPTION_MAX_LENGTH,
                DESCRIPTION_LENGTH_ERROR
        );
        this.description = description;
    }

    @Override
    public String getAuthor() {
        return author;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String toString(){
        return String.format("----------" +
                "%n%s" +
                "%nAuthor: %s" +
                "%n----------%n", getDescription(), getAuthor());
    }
}
