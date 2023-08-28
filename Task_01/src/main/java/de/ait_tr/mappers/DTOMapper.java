package de.ait_tr.mappers;

import de.ait_tr.dtos.EventDTO;
import de.ait_tr.dtos.NewEventDTO;
import de.ait_tr.models.Event;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class DTOMapper {
    private DTOMapper() {

    }

    public static EventDTO toEventDTO(Event event) {
        return new EventDTO(event.getId(), event.getTitle(), event.getBeginDateTime(), event.getEndDateTime());
    }

    public static Event toEvent(NewEventDTO newEventDTO) {
        return new Event(UUID.randomUUID().toString(), newEventDTO.title(), newEventDTO.beginDateTime(), newEventDTO.endDateTime());
    }

    public static String toLine(EventDTO eventDTO) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return String.format("id=%s, '%s',begins: %s, ends: %s.",
                eventDTO.id(),
                eventDTO.title(),
                eventDTO.beginDateTime().format(formatter),
                eventDTO.endDateTime().format(formatter)
        );
    }

    public static NewEventDTO toNewEventDTO(String title, LocalDateTime beginDateTime, LocalDateTime endDateTime) {
        if (beginDateTime.isAfter(endDateTime)) {
            throw new IllegalArgumentException("Дата окончания не может быть раньше даты начала!");
        }
        return new NewEventDTO(title, beginDateTime, endDateTime);
    }
}
