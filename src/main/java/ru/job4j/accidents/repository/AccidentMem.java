package ru.job4j.accidents.repository;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
@ThreadSafe
public class AccidentMem {
    private final AtomicInteger count = new AtomicInteger(0);
    private final Map<Integer, Accident> accidents = new ConcurrentHashMap<>();
    private final Map<Integer, AccidentType> types = new ConcurrentHashMap<>(Map.of(
            1, new AccidentType(1, "Две машины"),
            2, new AccidentType(2, "Машина и человек"),
            3, new AccidentType(3, "Машина и велосипед")
    ));
    private final Map<Integer, Rule> rules = new ConcurrentHashMap(Map.of(
            1, new Rule(1, "Статья. 1"),
            2, new Rule(2, "Статья. 2"),
            3, new Rule(3, "Статья. 3")
    ));

    public List<Accident> findAll() {
        return new ArrayList<>(accidents.values());
    }

    public Accident save(Accident accident) {
        accident.setId(count.incrementAndGet());
        accident.setType(types.get(accident.getType().getId()));
        accidents.putIfAbsent(count.get(), accident);
        return accident;
    }

    public Optional<Accident> findById(int id) {
        return Optional.ofNullable(accidents.get(id));
    }

    public void update(Accident accident) {
        accident.setType(types.get(accident.getType().getId()));
        accidents.put(accident.getId(), accident);
    }

    public List<AccidentType> findAllTypes() {
        return new ArrayList<>(types.values());
    }

    public List<Rule> findAllRules() {
        return new ArrayList<>(rules.values());
    }
    public Set<Rule> findRulesByIds(String[] ids) {
        return Arrays.stream(ids)
                .map(index -> rules.get(Integer.parseInt(index)))
                .collect(Collectors.toSet());
    }
}
