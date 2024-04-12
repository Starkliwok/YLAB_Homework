package com.Y_LAB.homework.audit;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class UserAuditTest {

    @Test
    void addSuccessLog() {
        String expected = UserAudit.getLog().toString();
        LocalDateTime dateTime = LocalDateTime.of(2024, 11,4, 22, 20, 56);
        UserAudit.addLog("some test", dateTime, "random name", UserAuditResult.SUCCESS);
        expected += "some test"
                + " "
                + dateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss"))
                + " username="+"random name"
                + " ["
                + UserAuditResult.SUCCESS
                + "]\n";

        String actual = UserAudit.getLog().toString();

        assertEquals(expected, actual);
    }

    @Test
    void addFailLog() {
        String expected = UserAudit.getLog().toString();
        LocalDateTime dateTime = LocalDateTime.of(2022, 10,3, 12, 22, 23);
        UserAudit.addLog("some test", dateTime, "some name", UserAuditResult.FAIL);
        expected += "some test"
                + " "
                + dateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss"))
                + " username="+"some name"
                + " ["
                + UserAuditResult.FAIL
                + "]\n";

        assertEquals(expected, UserAudit.getLog().toString());
    }
}