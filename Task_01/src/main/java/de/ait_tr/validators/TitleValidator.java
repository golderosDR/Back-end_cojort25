package de.ait_tr.validators;

import de.ait_tr.exceptions.IllegalDataFormatException;

public class TitleValidator {
    private TitleValidator(){}
    public static boolean validate(String title) {
        if (title.isEmpty() || title.isBlank()) {
            throw  new IllegalDataFormatException("Название события не должно быть пустым!");
        }
        return true;
    }
}
