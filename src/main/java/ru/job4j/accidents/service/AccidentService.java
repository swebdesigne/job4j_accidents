package ru.job4j.accidents.service;

import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface AccidentService {
    boolean update(Accident accident);

    Iterable<Accident> findAll();

    Set<Rule> findRulesByIds(String[] ids);

    Set<Rule> findAllRules();

    Optional<Accident> findById(int id);

    List<AccidentType> findAllTypes();

    Optional<Accident> save(Accident accident);

}
