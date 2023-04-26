package ru.job4j.accidents.repository;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

@Repository
@ThreadSafe
public class AccidentMem {
    Map<Integer, Accident> accidents = new ConcurrentHashMap<>();

    public AccidentMem() {
        IntStream.range(1, 6).forEach(this::save);
    }

    private void save(int index) {
        accidents.putIfAbsent(index, Accident.builder()
                .id(index)
                .name("Accident #" + index)
                .text("Some text")
                .address("Address #" + index)
                .build()
        );
    }

    public List<Accident> findAll() {
        return new ArrayList<>(accidents.values());
    }
}
