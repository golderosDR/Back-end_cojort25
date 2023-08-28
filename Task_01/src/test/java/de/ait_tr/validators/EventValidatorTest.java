package de.ait_tr.validators;

import de.ait_tr.exceptions.IllegalDataFormatException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class EventValidatorTest {
    private static final String VALID_ID = "8ce454e5-bff6-4a6d-8ac2-dc6883a021b6";
    private static final String INVALID_ID = "asda;lsjkdfna";
    private static final String VALID_TITLE = "1111";
    private static final String INVALID_TITLE = "";
    private static final String VALID_BEGIN_DATE = "2023-10-15 20:30";
    private static final String INVALID_BEGIN_DATE = "2023-10-15";
    private static final String VALID_END_DATE = "2023-10-15 21:30";
    private static final String INVALID_END_DATE = "";

    @Test
    public void on_incorrect_title_throws_exception() {
        assertThrows(IllegalDataFormatException.class, () -> EventValidator.validate(
                VALID_ID,
                INVALID_TITLE,
                VALID_BEGIN_DATE,
                VALID_END_DATE
        ));
    }
    @Test
    public void on_incorrect_id_throws_exception() {
        assertThrows(IllegalDataFormatException.class, () -> EventValidator.validate(
                INVALID_ID,
                VALID_TITLE,
                VALID_BEGIN_DATE,
                VALID_END_DATE
        ));
    }
    @Test
    public void on_incorrect_beginDate_throws_exception() {
        assertThrows(IllegalDataFormatException.class, () -> EventValidator.validate(
                VALID_ID,
                VALID_TITLE,
                INVALID_BEGIN_DATE,
                VALID_END_DATE
        ));
    }
    @Test
    public void on_incorrect_endDate_throws_exception() {
        assertThrows(IllegalDataFormatException.class, () -> EventValidator.validate(
                VALID_ID,
                VALID_TITLE,
                VALID_BEGIN_DATE,
                INVALID_END_DATE
        ));
    }
    @Test
    public void on_incorrect_all_throws_exception() {
        assertThrows(IllegalDataFormatException.class, () -> EventValidator.validate(
                INVALID_ID,
                INVALID_TITLE,
                INVALID_BEGIN_DATE,
                INVALID_END_DATE
        ));
    }
    @Test
    public void all_valid_success() {

        assertEquals(new ArrayList<>(), EventValidator.validate(
                VALID_ID,
                VALID_TITLE,
                VALID_BEGIN_DATE,
                VALID_END_DATE
        ));
    }

}