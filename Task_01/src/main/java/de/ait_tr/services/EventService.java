package de.ait_tr.services;

import de.ait_tr.dtos.EventDTO;
import de.ait_tr.dtos.NewEventDTO;

import java.util.List;

public interface EventService {
    boolean addEvent(NewEventDTO newEventDTO);

    List<EventDTO> getAllEvents();

    EventDTO deleteEventById(String id);

    EventDTO updateEvent(String id, NewEventDTO newEventDTO);

    EventDTO findEventByID(String id);

    EventDTO findEventByTitle(String title);

}
