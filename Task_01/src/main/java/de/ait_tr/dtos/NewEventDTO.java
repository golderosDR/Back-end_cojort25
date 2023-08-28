package de.ait_tr.dtos;

import java.time.LocalDateTime;
import java.util.Objects;

public record NewEventDTO(String title, LocalDateTime beginDateTime, LocalDateTime endDateTime) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewEventDTO that = (NewEventDTO) o;
        return Objects.equals(title, that.title) && Objects.equals(beginDateTime, that.beginDateTime) && Objects.equals(endDateTime, that.endDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, beginDateTime, endDateTime);
    }
}
