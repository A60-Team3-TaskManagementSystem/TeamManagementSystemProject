package com.practice.projectone.teammanagement.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EventLogImplTests {


    @Test
    public void constructor_Should_ThrowException_When_NoArguments(){
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new EventLogImpl()
        );
    }
    @Test
    public void constructor_ShouldThrowException_When_DescriptionIsEmpty(){
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new EventLogImpl("")
        );
    }

    @Test
    public void constructor_Should_InitializeEventLog_When_DescriptionNotEmpty(){
        EventLogImpl eventLog = new EventLogImpl("a");
        Assertions.assertAll(
                () ->Assertions.assertNotNull(eventLog.getDescription()),
                () ->Assertions.assertNotNull(eventLog.getTimestamp())
        );
    }
}
