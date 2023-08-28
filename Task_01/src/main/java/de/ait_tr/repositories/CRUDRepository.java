package de.ait_tr.repositories;

import de.ait_tr.dtos.NewEventDTO;

import java.util.List;

public interface CRUDRepository<T> {
    T findById(String id);

    List<T> findAll();

    void save(NewEventDTO model);

    void deleteById(String id);

    void update(String id, NewEventDTO model);
}
