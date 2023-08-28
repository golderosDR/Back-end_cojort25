package de.ait_tr.validators;

import de.ait_tr.exceptions.IllegalDataFormatException;

public class IdValidator {
    private IdValidator(){}
    public static boolean validate(String id) {
        if (!id.matches("\\w{8}-\\w{4}-\\w{4}-\\w{4}-\\w{12}")) {
           throw new IllegalDataFormatException("ID неверного формата!");
        }
        return true;
    }
}
