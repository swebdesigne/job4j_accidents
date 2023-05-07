package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.AccidentDataStore;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@AllArgsConstructor
@Service
@ThreadSafe
public class AccidentDataService {
    private final AccidentDataStore store;

    public boolean update(Accident accident) {
        store.save(accident);
        return store.existsById(accident.getId());
    }

    public Set<Accident> findAll() {
        Set<Accident> accidents = new HashSet<>();
        store.findAll().forEach(accidents::add);
        return accidents;
    }

    public Optional<Accident> findById(int id) {
        return store.findById(id);
    }

    public Optional<Accident> save(Accident accident) {
        return Optional.of(store.save(accident));
    }
}
