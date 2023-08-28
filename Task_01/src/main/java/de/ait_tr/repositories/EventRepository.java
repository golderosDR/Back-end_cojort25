package de.ait_tr.repositories;


import de.ait_tr.dtos.EventDTO;

public interface EventRepository extends CRUDRepository<EventDTO> {

    EventDTO findById(String id);
    EventDTO findByTitle(String title);
}
