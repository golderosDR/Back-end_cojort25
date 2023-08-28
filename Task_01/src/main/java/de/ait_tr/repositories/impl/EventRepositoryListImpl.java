package de.ait_tr.repositories.impl;

import de.ait_tr.dtos.EventDTO;
import de.ait_tr.dtos.NewEventDTO;
import de.ait_tr.mappers.DTOMapper;
import de.ait_tr.models.Event;
import de.ait_tr.repositories.EventRepository;

import java.util.ArrayList;
import java.util.List;

public class EventRepositoryListImpl implements EventRepository {
    private List<Event> eventList;

    public EventRepositoryListImpl() {
        this.eventList = new ArrayList<>();
    }


    @Override
    public EventDTO findById(String id) {
        Event foundedById = eventList.stream().filter(event -> event.getId().equals(id)).findFirst().orElse(null);
        return foundedById != null ? DTOMapper.toEventDTO(foundedById) : null;
    }

    @Override
    public EventDTO findByTitle(String title) {
        Event foundedByTitle = eventList.stream().filter(event -> event.getTitle().equals(title)).findFirst().orElse(null);
        return foundedByTitle != null ? DTOMapper.toEventDTO(foundedByTitle) : null;
    }

    @Override
    public List<EventDTO> findAll() {
        return getAll().stream().map(DTOMapper::toEventDTO).toList();
    }

    private List<Event> getAll() {
        return eventList;
    }


    @Override
    public void save(NewEventDTO eventDTO) {
        eventList.add(DTOMapper.toEvent(eventDTO));
    }

    @Override
    public void deleteById(String id) {
        eventList = eventList.stream().filter(event -> !event.getId().equals(id)).toList();
    }

    @Override
    public void update(String id, NewEventDTO eventDTO) {
        List<Event> newEventList = getAll();
        for (Event event : newEventList) {
            if (event.getId().equals(id)) {
                event.setTitle(eventDTO.title());
                event.setBeginDateTime(eventDTO.beginDateTime());
                event.setEndDateTime(eventDTO.endDateTime());
            }
        }
    }
}

