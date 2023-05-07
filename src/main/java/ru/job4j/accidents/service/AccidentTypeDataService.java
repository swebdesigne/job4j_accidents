package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.AccidentTypeDataStore;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@ThreadSafe
public class AccidentTypeDataService {
    private final AccidentTypeDataStore store;

    public List<AccidentType> findAll() {
        List<AccidentType> accidentTypes = new ArrayList<>();
        store.findAll().forEach(accidentTypes::add);
        return accidentTypes;
    }
}
