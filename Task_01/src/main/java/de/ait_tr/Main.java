package de.ait_tr;

import de.ait_tr.controllers.EventController;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        EventController eventController = new EventController(scanner);

        boolean isRun = true;

        while (isRun) {
            String command = scanner.nextLine();

            switch (command) {
                case "/add" ->  eventController.addEvent();
                case "/events" ->  eventController.getAllEvents();
                case "/delete" -> eventController.deleteEventById();
                case "/update" -> eventController.updateEvent();
                case "/findById" -> eventController.getEventById();
                case "/findByTitle" -> eventController.getEventByTitle();
                default -> System.out.println("Команда не распознана!");
                case "/exit" -> isRun = false;
            }
        }
        System.out.println("Выход.");
        scanner.close();
    }
}
