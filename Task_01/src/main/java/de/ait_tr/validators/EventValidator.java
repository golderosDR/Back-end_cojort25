package de.ait_tr.validators;

import de.ait_tr.exceptions.IllegalDataFormatException;

import java.util.ArrayList;

public class EventValidator {
    private EventValidator() {
    }

    public static ArrayList<String> validate(String id, String title, String beginDateTime, String endDateTime) {
        ArrayList<String> errors = new ArrayList<>();
        if (!id.matches("\\w{8}-\\w{4}-\\w{4}-\\w{4}-\\w{12}")) {
            errors.add("ID неверного формата!");
        }
        if (title.isEmpty() || title.isBlank()) {
            errors.add("Название события не должно быть пустым!");
        }
        if (!beginDateTime.matches("[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}")) {
            errors.add("Дата начала неверного формата!");
        }
        if (!endDateTime.matches("[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}")) {
            errors.add("Дата окончания неверного формата!");
        }
        if (!errors.isEmpty()) {
            String message = String.join("  ", errors);
            throw new IllegalDataFormatException(message);
        }
        return errors;
    }
}
