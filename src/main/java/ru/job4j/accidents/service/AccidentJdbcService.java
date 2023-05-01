package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.AccidentJdbcTemplate;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
@ThreadSafe
public class AccidentJdbcService implements AccidentService {
    private final AccidentJdbcTemplate accidentJdbcTemplate;

    @Override
    public boolean update(Accident accident) {
        return accidentJdbcTemplate.update(accident);
    }

    @Override
    public List<Accident> findAll() {
        return accidentJdbcTemplate.findAll();
    }

    @Override
    public Set<Rule> findRulesByIds(String[] ids) {
        return accidentJdbcTemplate.findRulesByIds(ids);
    }

    @Override
    public Set<Rule> findAllRules() {
        return accidentJdbcTemplate.findAllRules();
    }

    @Override
    public Optional<Accident> findById(int id) {
        return accidentJdbcTemplate.findById(id);
    }

    @Override
    public AccidentType findTypeById(int id) {
        return accidentJdbcTemplate.findTypeById(id);
    }

    @Override
    public List<AccidentType> findAllTypes() {
        return accidentJdbcTemplate.findAllTypes();
    }

    @Override
    public Optional<Accident> save(Accident accident) {
        return accidentJdbcTemplate.save(accident);
    }
}
