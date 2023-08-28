package de.ait_tr.mappers;

import de.ait_tr.dtos.NewEventDTO;
import de.ait_tr.models.Event;
import de.ait_tr.validators.EventValidator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class EventMapper {
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private EventMapper() {
    }

    public static Event toEvent(String line) {

        String[] parsed = line.split(";", -1);
        if (parsed.length != 4) {
            throw new IllegalArgumentException("Ошибка чтения базы! База содержит недопустимые значения!");
        }
        ArrayList<String> errors = EventValidator.validate(parsed[0], parsed[1], parsed[2], parsed[3]);

        String id = parsed[0];
        String title = parsed[1];

        LocalDateTime beginDateTime = LocalDateTime.parse(parsed[2], formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(parsed[3], formatter);

        if (endDateTime.isBefore(beginDateTime)) {
            errors.add("Дата окончания не может быть раньше даты начала!");
        }
        if (!errors.isEmpty()) {
            String message = String.join("  ", errors);
            throw new IllegalArgumentException(message);
        }
        return new Event(id, title, beginDateTime, endDateTime);
    }

    public static String toLine(Event event) {
        return event.getId() + ";" +
                event.getTitle() + ";" +
                event.getBeginDateTime().format(formatter) + ";" +
                event.getEndDateTime().format(formatter);
    }
    public static NewEventDTO toNewEventDTO(Event event) {
        return new NewEventDTO(event.getTitle(), event.getBeginDateTime(), event.getEndDateTime());
    }
}
