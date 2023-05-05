package ru.job4j.accidents.repository;

import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface AccidentRepository {
    Set<Accident> findAll();

    Optional<Accident> save(Accident accident);

    Optional<Accident> findById(int id);

    boolean update(Accident accident);

    List<AccidentType> findAllTypes();

    Set<Rule> findAllRules();

    Set<Rule> findRulesByIds(String[] ids);
}
