package de.ait_tr.services.impl;

import de.ait_tr.dtos.EventDTO;
import de.ait_tr.mappers.DTOMapper;
import de.ait_tr.mappers.EventMapper;
import de.ait_tr.models.Event;
import de.ait_tr.repositories.EventRepository;
import de.ait_tr.repositories.impl.EventRepositoryFileImpl;
import de.ait_tr.services.EventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class EventServiceImplTest {
    private EventService eventService;
    private EventRepository eventRepository;
    private static final String EXIST_EVENT_TITLE = "22";
    private static final String EXIST_EVENT_ID = "8ce454e5-bff6-4a6d-8ac2-dc6883a021b6";
    private static final String NOT_EXIST_EVENT_TITLE = "44";
    private static final String NOT_EXIST_EVENT_ID = "65fee2a0-a089-4c0d-9ed0-e5897b2fd270";
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final LocalDateTime EXIST_EVENT_BEGIN = LocalDateTime.parse("2023-10-15 20:30", formatter);
    private static final LocalDateTime EXIST_EVENT_END = LocalDateTime.parse("2023-10-15 21:30", formatter);
    private static final LocalDateTime NOT_EXIST_EVENT_BEGIN = LocalDateTime.parse("2023-10-15 15:30", formatter);
    private static final LocalDateTime NOT_EXIST_EVENT_END = LocalDateTime.parse("2023-10-15 16:30", formatter);
    private static final Event EXIST_EVENT = new Event(
            EXIST_EVENT_ID,
            EXIST_EVENT_TITLE,
            EXIST_EVENT_BEGIN,
            EXIST_EVENT_END
    );
    private static final Event NOT_EXIST_EVENT = new Event(
            NOT_EXIST_EVENT_ID,
            NOT_EXIST_EVENT_TITLE,
            NOT_EXIST_EVENT_BEGIN,
            NOT_EXIST_EVENT_END
    );

    @BeforeEach
    public void setUp() {
        this.eventRepository = Mockito.mock(EventRepositoryFileImpl.class);

        when(eventRepository.findByTitle(EXIST_EVENT_TITLE)).thenReturn(DTOMapper.toEventDTO(EXIST_EVENT));
        when(eventRepository.findById(EXIST_EVENT_ID)).thenReturn(DTOMapper.toEventDTO(EXIST_EVENT));
        when(eventRepository.findByTitle(NOT_EXIST_EVENT_TITLE)).thenReturn(null);
        when(eventRepository.findById(NOT_EXIST_EVENT_ID)).thenReturn(null);

        this.eventService = new EventServiceImpl(eventRepository);
    }

    @Nested
    @DisplayName(("addEvent"))
    class AddEvent {
        @Test
        public void add_event_success() {
            boolean actual = eventService.addEvent(EventMapper.toNewEventDTO(NOT_EXIST_EVENT));
            verify(eventRepository).save(EventMapper.toNewEventDTO(NOT_EXIST_EVENT));
            assertTrue(actual);
        }
        @Test
        public void add_event_fail() {
            boolean actual = eventService.addEvent(EventMapper.toNewEventDTO(EXIST_EVENT));
            assertFalse(actual);
        }
    }
    @Nested
    @DisplayName(("deleteEvent"))
    class DeleteEvent {
        @Test
        public void delete_event_by_id_success() {
            EventDTO actual = eventService.deleteEventById(EXIST_EVENT_ID);
            verify(eventRepository).deleteById(EXIST_EVENT_ID);
            assertNotNull(actual);
        }
        @Test
        public void ads_event_by_id_fail() {
            EventDTO actual = eventService.deleteEventById(NOT_EXIST_EVENT_ID);
            assertNull(actual);
        }
    }
    @Nested
    @DisplayName(("updateEvent"))
    class UpdateEvent {
        @Test
        public void update_event_success() {
            EventDTO actual = eventService.updateEvent(EXIST_EVENT_ID, EventMapper.toNewEventDTO(NOT_EXIST_EVENT));
            verify(eventRepository).update(EXIST_EVENT_ID, EventMapper.toNewEventDTO(NOT_EXIST_EVENT));
            assertNotNull(actual);
        }
        @Test
        public void update_event_fail() {
            EventDTO actual = eventService.updateEvent(NOT_EXIST_EVENT_ID,EventMapper.toNewEventDTO(NOT_EXIST_EVENT) );
            assertNull(actual);
        }
    }
}