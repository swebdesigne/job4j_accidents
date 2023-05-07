package ru.job4j.accidents.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.job4j.accidents.model.Rule;

import java.util.List;

public interface AccidentRuleDataRepository extends CrudRepository<Rule, Integer> {
    @Query("from Rule where id in :ids")
    List<Rule> findByIdIn(List<Integer> ids);
}
