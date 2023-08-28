package de.ait_tr.services.impl;

import de.ait_tr.models.Event;

import static org.junit.jupiter.api.Assertions.*;

class EventServiceImplTest {
    private static final String EXIST_USER_EMAIL = "user@gmail.com";
    private static final String NOT_EXIST_USER_EMAIL = "marsel@gmail.com";
    private static final String DEFAULT_PASSWORD = "qwerty007";
    private static final Event NOT_EXIST_USER = new (NOT_EXIST_USER_EMAIL, DEFAULT_PASSWORD);
    private static final Event EXIST_USER = new User(EXIST_USER_EMAIL, DEFAULT_PASSWORD);


    private UsersServiceImpl usersService; // объект, который мы будем тестировать

    private UsersRepository usersRepository;

}