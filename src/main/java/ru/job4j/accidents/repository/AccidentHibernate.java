package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;

import java.util.*;
import java.util.stream.Collectors;

@ThreadSafe
@AllArgsConstructor
@Log4j
@Repository
public class AccidentHibernate implements AccidentRepository {
    private static final String FIND_ALL_ACCIDENTS = "from Accident a join fetch a.type join fetch a.rules";

    private static final String GET_ACCIDENT_BY_ID = "from Accident a join fetch a.type join fetch a.rules where a.id = :ID";

    private static final String FIND_ALL_RULE = "from Rule";

    private static final String FIND_RULE_BY_ID = "from Rule where id = :fID";

    private static final String FIND_ALL_TYPES = "from AccidentType";

    private final CrudRepository crudRepository;

    @Override
    public Set<Accident> findAll() {
        return new HashSet<>(crudRepository.query(FIND_ALL_ACCIDENTS, Accident.class));
    }

    @Override
    public Optional<Accident> save(Accident accident) {
        crudRepository.run(session -> session.save(accident));
        return Optional.of(accident);
    }

    @Override
    public Optional<Accident> findById(int id) {
        return crudRepository.optional(GET_ACCIDENT_BY_ID, Accident.class,
                Map.of("ID", id)
        );
    }

    @Override
    public boolean update(Accident accident) {
        try {
            crudRepository.run(session -> session.merge(accident));
            return true;
        } catch (Exception e) {
            log.error("The error occurred, ", e);
        }
        return false;
    }

    @Override
    public List<AccidentType> findAllTypes() {
        return crudRepository.query(FIND_ALL_TYPES, AccidentType.class);
    }

    @Override
    public Set<Rule> findAllRules() {
        return new HashSet<>(crudRepository.query(FIND_ALL_RULE, Rule.class));
    }

    @Override
    public Set<Rule> findRulesByIds(String[] ids) {
        return Arrays.stream(ids)
                .map(index -> crudRepository.optional(FIND_RULE_BY_ID, Rule.class,
                        Map.of("fID", Integer.parseInt(index))))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
    }
}
