package de.ait_tr.repositories.impl;

import de.ait_tr.dtos.EventDTO;
import de.ait_tr.dtos.NewEventDTO;
import de.ait_tr.mappers.DTOMapper;
import de.ait_tr.mappers.EventMapper;
import de.ait_tr.models.Event;
import de.ait_tr.repositories.EventRepository;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

public class EventRepositoryFileImpl implements EventRepository {
    private final String fileName;

    public EventRepositoryFileImpl(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<EventDTO> findAll() {
        return getAll().stream()
                .map(DTOMapper::toEventDTO)
                .toList();
    }

    private List<Event> getAll() {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            return reader.lines()
                    .map(EventMapper::toEvent)
                    .toList();

        } catch (IOException e) {
            throw new IllegalStateException("Проблемы с чтением из файла: " + e.getMessage());
        }
    }

    @Override
    public void save(NewEventDTO newEventDTO) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            Event event = DTOMapper.toEvent(newEventDTO);
            writer.write(EventMapper.toLine(event) + System.lineSeparator());
        } catch (IOException e) {
            throw new IllegalStateException("Проблемы с записью в файл: " + e.getMessage());
        }
    }

    @Override
    public void deleteById(String id) {

        String newEventLines = getAll().stream()
                .filter(event -> !event.getId().equals(id))
                .map(EventMapper::toLine)
                .collect(Collectors.joining(System.lineSeparator()));

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(newEventLines + System.lineSeparator());
        } catch (IOException e) {
            throw new IllegalStateException("Проблемы с записью в файл: " + e.getMessage());
        }
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
        String newEventLines = newEventList.stream()
                .map(EventMapper::toLine)
                .collect(Collectors.joining(System.lineSeparator()));

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(newEventLines + System.lineSeparator());
        } catch (IOException e) {
            throw new IllegalStateException("Проблемы с записью в файл: " + e.getMessage());
        }
    }

    @Override
    public EventDTO findById(String id) {
        return getAll().stream()
                .filter(event -> event.getId().equals(id))
                .map(DTOMapper::toEventDTO)
                .findFirst()
                .orElse(null);
    }

    @Override
    public EventDTO findByTitle(String title) {
        return getAll().stream()
                .filter(event -> event.getTitle().equals(title))
                .map(DTOMapper::toEventDTO)
                .findFirst()
                .orElse(null);
    }
}
