package ru.job4j.accidents.repository;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

@Repository
@ThreadSafe
public class AccidentMem {
    private final AtomicInteger count = new AtomicInteger(0);
    private final Map<Integer, Accident> accidents = new ConcurrentHashMap<>();

    public List<Accident> findAll() {
        return new ArrayList<>(accidents.values());
    }

    public Accident save(Accident accident) {
        accident.setId(count.incrementAndGet());
        accidents.putIfAbsent(count.get(), accident);
        return accident;
    }

    public Accident getById(int id) {
        return accidents.get(id);
    }

    public void update(Accident accident) {
        accidents.put(accident.getId(), accident);
    }
}
