package de.ait_tr.mappers;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class EventMapperTest {

    @ParameterizedTest
    @ValueSource(strings = {
            "65fee2a0-a089-4c0d-9ed0-e5897b2fd270;2023-10-15 20:30;2023-10-15 21:30",
            "2;2023-10-15 20:30;2023-10-15 21:30",
            "65fee2a0-a089-4c0d-9ed0-e5897b2fd270;2;2023-10-15 21:30",
            "65fee2a0-a089-4c0d-9ed0-e5897b2fd270;2;2023-10-15 20:30",
            "65fee2a0-a089-4c0d-9ed0-e5897b2fd270;2;2023-10-15 20:30;2023-10-15 19:30",
    })
    void toEvent(String argument) {
        assertThrows(IllegalArgumentException.class, () -> EventMapper.toEvent(argument));
    }
}