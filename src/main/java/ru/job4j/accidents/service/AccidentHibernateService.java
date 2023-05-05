package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.AccidentHibernate;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
@ThreadSafe
@Primary
public class AccidentHibernateService implements AccidentService {
    private final AccidentHibernate accidentHibernate;

    @Override
    public boolean update(Accident accident) {
        return accidentHibernate.update(accident);
    }

    @Override
    public Set<Accident> findAll() {
        return accidentHibernate.findAll();
    }

    @Override
    public Set<Rule> findRulesByIds(String[] ids) {
        return accidentHibernate.findRulesByIds(ids);
    }

    @Override
    public Set<Rule> findAllRules() {
        return accidentHibernate.findAllRules();
    }

    @Override
    public Optional<Accident> findById(int id) {
        return accidentHibernate.findById(id);
    }

    @Override
    public List<AccidentType> findAllTypes() {
        return accidentHibernate.findAllTypes();
    }

    @Override
    public Optional<Accident> save(Accident accident) {
        return accidentHibernate.save(accident);
    }
}
