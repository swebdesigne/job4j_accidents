package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.AccidentMem;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@ThreadSafe
@AllArgsConstructor
@Service
public class AccidentService {
    private final AccidentMem accidentMem;

    public List<Accident> findAll() {
        return accidentMem.findAll();
    }

    public Accident save(Accident accident) {
        return accidentMem.save(accident);
    }

    public Optional<Accident> findById(int id) {
        return accidentMem.findById(id);
    }

    public void update(Accident accident) {
        accidentMem.update(accident);
    }

    public List<AccidentType> findAllTypes() {
        return accidentMem.findAllTypes();
    }

    public List<Rule> findAllRules() {
        return accidentMem.findAllRules();
    }

    public Set<Rule> findRulesByIds(String[] ids) {
        return accidentMem.findRulesByIds(ids);
    }
}
