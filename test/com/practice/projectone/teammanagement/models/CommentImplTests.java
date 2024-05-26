package com.practice.projectone.teammanagement.models;

import com.practice.projectone.teammanagement.models.contracts.Comment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.practice.projectone.teammanagement.utils.Constants.*;

public class CommentImplTests {

    private Comment comment;

    @BeforeEach
    void setup() {
        comment = new CommentImpl(
                VALID_AUTHOR_NAME,
                VALID_COMMENT);
    }

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
        Assertions.assertAll(
                () -> Assertions.assertEquals(VALID_AUTHOR_NAME, comment.getAuthor()),
                () -> Assertions.assertEquals(VALID_COMMENT, comment.getDescription())
        );
    }

    @Test
    public void toString_Should_PrintExpectedOutput() {
        Assertions.assertEquals(4, comment.toString().split(System.lineSeparator()).length);
    }
}
