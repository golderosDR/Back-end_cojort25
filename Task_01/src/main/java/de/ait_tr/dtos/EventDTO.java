package de.ait_tr.dtos;

import java.time.LocalDateTime;

public record EventDTO(String id, String title, LocalDateTime beginDateTime, LocalDateTime endDateTime) {

}
