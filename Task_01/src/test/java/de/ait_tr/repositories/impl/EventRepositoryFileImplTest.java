package de.ait_tr.repositories.impl;

import de.ait_tr.dtos.EventDTO;
import de.ait_tr.dtos.NewEventDTO;
import de.ait_tr.mappers.DTOMapper;
import de.ait_tr.mappers.EventMapper;
import org.junit.jupiter.api.*;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EventRepositoryFileImplTest {
    private static final String TEMP_EVENTS_FILE_NAME = "events_test.txt";
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private EventRepositoryFileImpl eventRepository;

    @BeforeEach
    public void setUp() throws Exception {
        createNewFileForTest();
        this.eventRepository = new EventRepositoryFileImpl(TEMP_EVENTS_FILE_NAME);
    }

    @AfterEach
    public void tearDown() {
        deleteFileAfterTest();
    }

    @DisplayName("save():")
    @Nested
    class Save {
        @Test
        public void writes_correct_event_to_file() throws Exception {

            NewEventDTO expected = new NewEventDTO(
                    "1",
                    LocalDateTime.parse("2023-10-15 20:30", formatter),
                    LocalDateTime.parse("2023-10-15 21:30", formatter));
            eventRepository.save(expected);
            BufferedReader reader = new BufferedReader(new FileReader(TEMP_EVENTS_FILE_NAME));
            NewEventDTO actual = EventMapper.toNewEventDTO(EventMapper.toEvent(reader.readLine()));
            reader.close();
            assertEquals(expected, actual);
        }
    }

    @DisplayName("delete by Id")
    @Nested
    class DeleteById {
        @Test
        public void delete_existed_event_from_file() throws Exception {
            String expected = "";
            BufferedWriter writer = new BufferedWriter(new FileWriter(TEMP_EVENTS_FILE_NAME));
            writer.write("8ce454e5-bff6-4a6d-8ac2-dc6883a021b6;3;2023-10-15 20:30;2023-10-15 21:45");
            writer.close();
            eventRepository.deleteById("8ce454e5-bff6-4a6d-8ac2-dc6883a021b6");
            BufferedReader reader = new BufferedReader(new FileReader(TEMP_EVENTS_FILE_NAME));
            String actual = reader.readLine();
            reader.close();
            assertEquals(expected, actual);

        }

        @Test
        public void delete_unexisted_event_from_file() throws Exception {
            String expected = "8ce454e5-bff6-4a6d-8ac2-dc6883a021b6;3;2023-10-15 20:30;2023-10-15 21:45";
            BufferedWriter writer = new BufferedWriter(new FileWriter(TEMP_EVENTS_FILE_NAME));
            writer.write("8ce454e5-bff6-4a6d-8ac2-dc6883a021b6;3;2023-10-15 20:30;2023-10-15 21:45");
            writer.close();
            eventRepository.deleteById("8Ae454e5-bff6-4a6d-8ac2-dc6883a021b6");
            BufferedReader reader = new BufferedReader(new FileReader(TEMP_EVENTS_FILE_NAME));
            String actual = reader.readLine();
            reader.close();
            assertEquals(expected, actual);

        }
    }

    @DisplayName("update")
    @Nested
    class Update {
        @Test
        public void update_event_in_file() throws Exception {
            String expected = "8ce454e5-bff6-4a6d-8ac2-dc6883a021b6;3;2023-10-15 20:30;2023-10-15 21:55";
            BufferedWriter writer = new BufferedWriter(new FileWriter(TEMP_EVENTS_FILE_NAME));
            writer.write("8ce454e5-bff6-4a6d-8ac2-dc6883a021b6;3;2023-10-15 20:30;2023-10-15 21:45");
            writer.close();
            NewEventDTO updated = new NewEventDTO(
                    "3",
                    LocalDateTime.parse("2023-10-15 20:30", formatter),
                    LocalDateTime.parse("2023-10-15 21:55", formatter));

            eventRepository.update("8ce454e5-bff6-4a6d-8ac2-dc6883a021b6", updated);

            BufferedReader reader = new BufferedReader(new FileReader(TEMP_EVENTS_FILE_NAME));
            String actual = reader.readLine();
            reader.close();

            assertEquals(expected, actual);
        }
    }

    @DisplayName("findAll")
    @Nested
    class findAll {
        @Test
        public void findAll_events_from_file() throws Exception {

            BufferedWriter writer = new BufferedWriter(new FileWriter(TEMP_EVENTS_FILE_NAME));
            writer.write(
                    "65fee2a0-a089-4c0d-9ed0-e5897b2fd270;2;2023-10-15 20:30;2023-10-15 21:30" +
                            System.lineSeparator() +
                            "8ce454e5-bff6-4a6d-8ac2-dc6883a021b6;3;2023-10-15 20:30;2023-10-15 21:45");
            writer.close();
            List<EventDTO> expected = new ArrayList<>();
            expected.add(DTOMapper.toEventDTO(EventMapper.toEvent("65fee2a0-a089-4c0d-9ed0-e5897b2fd270;2;2023-10-15 20:30;2023-10-15 21:30")));
            expected.add(DTOMapper.toEventDTO(EventMapper.toEvent("8ce454e5-bff6-4a6d-8ac2-dc6883a021b6;3;2023-10-15 20:30;2023-10-15 21:45")));

            List<EventDTO> actual = eventRepository.findAll();


            assertEquals(expected, actual);
        }
    }
    @DisplayName("findById")
    @Nested
    class findById {
        @Test
        public void find_event_by_correct_id_from_file() throws Exception {

            BufferedWriter writer = new BufferedWriter(new FileWriter(TEMP_EVENTS_FILE_NAME));
            writer.write(
                    "65fee2a0-a089-4c0d-9ed0-e5897b2fd270;2;2023-10-15 20:30;2023-10-15 21:30" +
                            System.lineSeparator() +
                            "8ce454e5-bff6-4a6d-8ac2-dc6883a021b6;3;2023-10-15 20:30;2023-10-15 21:45");
            writer.close();

            EventDTO expected = DTOMapper.toEventDTO(EventMapper.toEvent("65fee2a0-a089-4c0d-9ed0-e5897b2fd270;2;2023-10-15 20:30;2023-10-15 21:30"));

            EventDTO actual = eventRepository.findById("65fee2a0-a089-4c0d-9ed0-e5897b2fd270");


            assertEquals(expected, actual);
        }
        @Test
        public void find_event_by_incorrect_id_from_file() throws Exception {

            BufferedWriter writer = new BufferedWriter(new FileWriter(TEMP_EVENTS_FILE_NAME));
            writer.write(
                    "65fee2a0-a089-4c0d-9ed0-e5897b2fd270;2;2023-10-15 20:30;2023-10-15 21:30" +
                            System.lineSeparator() +
                            "8ce454e5-bff6-4a6d-8ac2-dc6883a021b6;3;2023-10-15 20:30;2023-10-15 21:45");
            writer.close();

            EventDTO actual = eventRepository.findById("611fee2a0-a089-4c0d-9ed0-e5897b2fd270");


            assertNull(actual);
        }
    }
    @DisplayName("findByTitle")
    @Nested
    class findByTitle {
        @Test
        public void find_event_by_correct_title_from_file() throws Exception {

            BufferedWriter writer = new BufferedWriter(new FileWriter(TEMP_EVENTS_FILE_NAME));
            writer.write(
                    "65fee2a0-a089-4c0d-9ed0-e5897b2fd270;2;2023-10-15 20:30;2023-10-15 21:30" +
                            System.lineSeparator() +
                            "8ce454e5-bff6-4a6d-8ac2-dc6883a021b6;3;2023-10-15 20:30;2023-10-15 21:45");
            writer.close();

            EventDTO expected = DTOMapper.toEventDTO(EventMapper.toEvent("65fee2a0-a089-4c0d-9ed0-e5897b2fd270;2;2023-10-15 20:30;2023-10-15 21:30"));

            EventDTO actual = eventRepository.findByTitle("2");


            assertEquals(expected, actual);
        }
        @Test
        public void find_event_by_incorrect_id_from_file() throws Exception {

            BufferedWriter writer = new BufferedWriter(new FileWriter(TEMP_EVENTS_FILE_NAME));
            writer.write(
                    "65fee2a0-a089-4c0d-9ed0-e5897b2fd270;2;2023-10-15 20:30;2023-10-15 21:30" +
                            System.lineSeparator() +
                            "8ce454e5-bff6-4a6d-8ac2-dc6883a021b6;3;2023-10-15 20:30;2023-10-15 21:45");
            writer.close();

            EventDTO actual = eventRepository.findByTitle("4");


            assertNull(actual);
        }
    }
    private static void createNewFileForTest() throws IOException {

        File file = new File(TEMP_EVENTS_FILE_NAME);
        deleteIfExists(file);
        boolean result = file.createNewFile();
        if (!result) {
            throw new IllegalStateException("Problems with file create");
        }
    }

    private static void deleteFileAfterTest() {
        File file = new File(TEMP_EVENTS_FILE_NAME);
        deleteIfExists(file);
    }

    private static void deleteIfExists(File file) {
        if (file.exists()) {
            boolean result = file.delete();
            if (!result) {
                throw new IllegalStateException("Problems with file delete");
            }
        }
    }
}