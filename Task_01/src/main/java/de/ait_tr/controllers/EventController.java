package de.ait_tr.controllers;

import de.ait_tr.dtos.EventDTO;
import de.ait_tr.mappers.DTOMapper;
import de.ait_tr.repositories.impl.EventRepositoryFileImpl;
import de.ait_tr.services.EventService;
import de.ait_tr.services.impl.EventServiceImpl;
import de.ait_tr.validators.IdValidator;
import de.ait_tr.validators.NewEventValidator;
import de.ait_tr.validators.TitleValidator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class EventController {
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private final Scanner scanner;
    private final EventService eventService;

    public EventController(Scanner scanner) {
        this.scanner = scanner;
        this.eventService = new EventServiceImpl(new EventRepositoryFileImpl("./db/Events.csv"));
        //this.eventService = new EventServiceImpl(new EventRepositoryListImpl());
    }

    public void addEvent() {
        boolean result = false;
        System.out.println("Введите название события:");
        String title = scanner.nextLine();
        System.out.println("Введите дату начала события в формате 'yyyy-MM-dd HH:mm':");
        String beginDateTimeString = scanner.nextLine();
        System.out.println("Введите дату окончания события в формате 'yyyy-MM-dd HH:mm':");
        String endDateTimeString = scanner.nextLine();

        List<String> errors = NewEventValidator.validate(title, beginDateTimeString, endDateTimeString);
        if (errors.isEmpty()) {
            LocalDateTime beginDateTime = LocalDateTime.parse(beginDateTimeString, formatter);
            LocalDateTime endDateTime = LocalDateTime.parse(endDateTimeString, formatter);

            result = eventService.addEvent(DTOMapper.toNewEventDTO(title, beginDateTime, endDateTime));

        }
        System.out.println(result ? "Событие успешно добавлено!" : "Ошибка добавления события!");
    }

    public void getAllEvents() {
        printAllEvents(eventService.getAllEvents());
    }

    private void printEvent(EventDTO eventDTO) {
        System.out.println(DTOMapper.toLine(eventDTO));
    }

    private void printAllEvents(List<EventDTO> eventDTOList) {
        System.out.println(eventDTOList.stream()
                .map(DTOMapper::toLine)
                .collect(Collectors.joining(System.lineSeparator())));
    }

    public void getEventById() {
        System.out.println("Введите Id события для поиска:");
        String id = scanner.nextLine();
        if (IdValidator.validate(id)) {
            EventDTO founded = eventService.findEventByID(id);
            if (founded!=null) {
                printEvent(founded);
            } else System.out.println("Ничего не найдено!");
        }
    }

    public void getEventByTitle() {
        System.out.println("Введите название события для поиска:");
        String title = scanner.nextLine();
        if (TitleValidator.validate(title)) {
            EventDTO founded = eventService.findEventByTitle(title);
            if (founded!=null) {
                printEvent(founded);
            } else System.out.println("Ничего не найдено!");
        }
    }

    public void deleteEventById() {
        System.out.println("Введите Id события для удаления:");
        String id = scanner.nextLine();
        if (IdValidator.validate(id)) {
            EventDTO deletedEvent = eventService.deleteEventById(id);
            if (deletedEvent != null) {
                System.out.println("Удаленный эдемент:");
                printEvent(deletedEvent);
            } else {
                System.out.println("Элемент с Id=" + id + " отсутствует.");
            }
        }
    }

    public void updateEvent() {
        EventDTO updatedEvent = null;
        System.out.println("Введите Id события для корректировки:");
        String id = scanner.nextLine();

        if (IdValidator.validate(id)) {


            System.out.println("Введите новое название события:");
            String title = scanner.nextLine();
            System.out.println("Введите новую дату начала события в формате 'yyyy-MM-dd HH:mm':");
            String beginDateTimeString = scanner.nextLine();
            System.out.println("Введите новую дату окончания события в формате 'yyyy-MM-dd HH:mm':");
            String endDateTimeString = scanner.nextLine();

            List<String> errors = NewEventValidator.validate(title, beginDateTimeString, endDateTimeString);
            if (errors.isEmpty()) {
                LocalDateTime beginDateTime = LocalDateTime.parse(beginDateTimeString, formatter);
                LocalDateTime endDateTime = LocalDateTime.parse(endDateTimeString, formatter);

                updatedEvent = eventService.updateEvent(id, DTOMapper.toNewEventDTO(title, beginDateTime, endDateTime));

            }
        }
        if (updatedEvent != null) {
            System.out.println("Событие с Id=" + id + " успешно обновлено!");
        } else {
            System.out.println("Ошибка обновления события!");
        }
    }
}

