package ru.job4j.accidents.repository;

import ru.job4j.accidents.model.Accident;
import org.springframework.data.repository.CrudRepository;

public interface AccidentDataStore extends CrudRepository<Accident, Integer> {
}

