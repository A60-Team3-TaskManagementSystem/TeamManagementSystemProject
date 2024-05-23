package com.practice.projectone.teammanagement.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.practice.projectone.teammanagement.utils.Constants.*;

public class CommentImplTests {

    @Test
    public void constructor_Should_ThrowException_When_AuthorIsInvalid(){
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new CommentImpl(
                        INVALID_AUTHOR_NAME_MIN,
                        VALID_COMMENT
                )
        );
    }

    @Test
    public void constructor_Should_ThrowException_When_CommentIsInvalid(){
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new CommentImpl(
                        VALID_AUTHOR_NAME,
                        INVALID_COMMENT_MIN
                )
        );
    }

    @Test
    public void constructor_Should_InitializeComment_When_ArgumentsAreValid(){
        CommentImpl comment = initializeComment();

        Assertions.assertAll(
                () -> Assertions.assertEquals(VALID_AUTHOR_NAME, comment.getAuthor()),
                () -> Assertions.assertEquals(VALID_COMMENT, comment.getDescription())
        );
    }

    public CommentImpl initializeComment(){
        return new CommentImpl(
                VALID_AUTHOR_NAME,
                VALID_COMMENT
        );
    }
}
