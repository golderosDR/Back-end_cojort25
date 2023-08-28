package de.ait_tr.services.impl;

import de.ait_tr.dtos.EventDTO;
import de.ait_tr.dtos.NewEventDTO;
import de.ait_tr.repositories.EventRepository;
import de.ait_tr.services.EventService;

import java.util.List;

public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;

    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public boolean addEvent(NewEventDTO newEventDTO) {
        EventDTO foundedEvent = eventRepository.findByTitle(newEventDTO.title());
        if (foundedEvent == null) {
            eventRepository.save(newEventDTO);
            return true;
        }
        return false;
    }

    @Override
    public List<EventDTO> getAllEvents() {
        return eventRepository.findAll();
    }

    @Override
    public EventDTO deleteEventById(String id) {
        EventDTO foundedEvent = eventRepository.findById(id);
        if (foundedEvent != null) {
            eventRepository.deleteById(id);
            return foundedEvent;
        }
        return null;
    }

    @Override
    public EventDTO updateEvent(String id, NewEventDTO newEventDTO) {
        EventDTO foundedEvent = eventRepository.findById(id);
        if (foundedEvent != null) {
            if (eventRepository.findByTitle(newEventDTO.title()) == null) {
                eventRepository.update(id, newEventDTO);
                return eventRepository.findById(id);
            }
        }
        return null;
    }

    @Override
    public EventDTO findEventByID(String id) {
        return eventRepository.findById(id);
    }

    @Override
    public EventDTO findEventByTitle(String title) {
        return eventRepository.findByTitle(title);
    }
}
