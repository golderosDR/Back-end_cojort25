package de.ait_tr.models;

import java.time.LocalDateTime;
import java.util.Objects;

public class Event {
    private final String id;
    private String title;
    private LocalDateTime beginDateTime;
    private LocalDateTime endDateTime;

    public Event(String id, String title, LocalDateTime beginDateTime, LocalDateTime endDateTime) {
        this.id = id;
        this.title = title;
        this.beginDateTime = beginDateTime;
        this.endDateTime = endDateTime;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBeginDateTime(LocalDateTime beginDateTime) {
        this.beginDateTime = beginDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getBeginDateTime() {
        return beginDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(id, event.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Event{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", beginDateTime=" + beginDateTime +
                ", endDateTime=" + endDateTime +
                '}';
    }
}
