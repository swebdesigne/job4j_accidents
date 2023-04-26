package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.AccidentMem;

import java.util.List;

@ThreadSafe
@AllArgsConstructor
@Service
public class AccidentService {
    private final AccidentMem accidentMem;

    public List<Accident> findAll() {
        return accidentMem.findAll();
    }
}
