package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.AccidentRuleDataRepository;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@ThreadSafe
@Service
@AllArgsConstructor
public class AccidentRuleDataService {
    private final AccidentRuleDataRepository store;

    public Set<Rule> findByIdIn(String[] ids) {
        return new HashSet<>(
                store.findByIdIn(
                        Arrays.stream(ids)
                                .map(Integer::parseInt)
                                .collect(Collectors.toList())
                )
        );
    }

    public Set<Rule> findAll() {
        Set<Rule> rules = new HashSet<>();
        store.findAll().forEach(rules::add);
        return rules;
    }

}
