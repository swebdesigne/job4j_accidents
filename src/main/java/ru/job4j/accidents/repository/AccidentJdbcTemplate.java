package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Log4j
@Repository
@AllArgsConstructor
public class AccidentJdbcTemplate implements AccidentRepository {
    private final static String INSERT_INTO_ACCIDENT = "insert into accidents (name, address, text, type_id) "
            + "values (?, ?, ?, ?) RETURNING id";

    private final static String UPDATE_ACCIDENT_WHERE = "update accidents set name = (?), address = (?), "
            + "text = (?), type_id = (?) where id = (?)";

    private final static String SELECT_ACCIDENT = "select accidents.*, "
            + "types.id as typeId , "
            + "types.name  as typeName "
            + "from accidents "
            + "left join types on accidents.type_id = types.id "
            + "order by accidents.id";

    private final static String SELECT_ACCIDENT_WHERE = "select accidents.*, "
            + "types.id as typeId , "
            + "types.name  as typeName "
            + "from accidents "
            + "left join types on accidents.type_id = types.id "
            + "where accidents.id = (?)";

    private static final String FIND_RULES_OF_ACCIDENT = "select rule_id "
            + "from accidents_rules "
            + "where accident_id = (?)";

    private static final String FIND_RULE_BY_ID = "select id, name from rules where id = (?)";

    private static final String FIND_ALL_RULES = "select * from rules";

    private static final String SAVE_RULE = "insert into accidents_rules (accident_id, rule_id) values (?, ?)";

    private static final String FIND_TYPE_BY_ID = "select id, name from types where id = (?)";

    private static final String FIND_ALL_TYPES = "select id, name from types";

    private final JdbcTemplate jdbc;

    @Override
    public Optional<Accident> save(Accident accident) {
        log.info("Save accident");
        try {
            var id = jdbc.queryForObject(INSERT_INTO_ACCIDENT,
                    Integer.class,
                    accident.getName(),
                    accident.getAddress(),
                    accident.getText(),
                    accident.getType().getId()
            );
            accident.setId(Objects.requireNonNull(id));
            saveRule(accident);
            return Optional.of(accident);
        } catch (Exception e) {
            log.error("Save error occurred ", e);
        }
        return Optional.empty();
    }

    @Override
    public boolean update(Accident accident) {
        log.info("Update accident");
        try {
            jdbc.update(UPDATE_ACCIDENT_WHERE,
                    accident.getName(),
                    accident.getAddress(),
                    accident.getText(),
                    accident.getType().getId(),
                    accident.getId()
            );
            saveRule(accident);
            return true;
        } catch (Exception e) {
            log.error("Update error occurred ", e);
        }
        return false;
    }

    @Override
    public List<Accident> findAll() {
        log.info("Find all accidents");
        try {
            return jdbc.query(SELECT_ACCIDENT, this::createAccident);
        } catch (Exception e) {
            log.error("Find all accidents error occurred ", e);
        }
        return new ArrayList<>();
    }

    @Override
    public Optional<Accident> findById(int id) {
        log.info("Find the accident by id");
        try {
            return Optional.ofNullable(
                    jdbc.queryForObject(SELECT_ACCIDENT_WHERE, this::createAccident, id)
            );
        } catch (Exception e) {
            log.error("Find accident by id error occurred ", e);
        }
        return Optional.empty();
    }

    private Accident createAccident(ResultSet rs, int row) throws SQLException {
        Accident accident = new Accident();
        accident.setId(rs.getInt("id"));
        accident.setName(rs.getString("name"));
        accident.setText(rs.getString("text"));
        accident.setAddress(rs.getString("address"));
        accident.setType(new AccidentType(rs.getInt("typeId"), rs.getString("typeName")));
        accident.setRules(findRulesOfAccident(rs.getInt("id")));
        return accident;
    }

    private Set<Rule> findRulesOfAccident(int id) {
        log.info("Find rules of accident");
        try {
            return new HashSet<>(jdbc.query(FIND_RULES_OF_ACCIDENT,
                    (rs, row) -> findRuleById(rs.getInt("rule_id")), id)
            );
        } catch (Exception e) {
            log.error("Find rules of accident error occurred ", e);
        }
        return new HashSet<>();
    }

    private Rule findRuleById(int id) {
        return jdbc.queryForObject(FIND_RULE_BY_ID, (rs, row) -> {
            Rule rule = new Rule();
            rule.setId(rs.getInt("id"));
            rule.setName(rs.getString("name"));
            return rule;
        }, id);
    }

    @Override
    public Set<Rule> findAllRules() {
        log.info("Find all rules");
        try {
            return new HashSet<>(jdbc.query(FIND_ALL_RULES, (rs, row) -> findRuleById(rs.getInt("id"))));
        } catch (Exception e) {
            log.error("Find all rules error occurred ", e);
        }
        return new HashSet<>();
    }

    @Override
    public Set<Rule> findRulesByIds(String[] ids) {
        log.info("Find rules by ids");
        try {
            return Arrays.stream(ids)
                    .map(index -> findRuleById(Integer.parseInt(index)))
                    .collect(Collectors.toSet());
        } catch (Exception e) {
            log.error("Find rules by ids error occurred ", e);
        }
        return new HashSet<>();
    }

    private void saveRule(Accident accident) {
        log.info("Save the rule");
        try {
            jdbc.update("delete from accidents_rules where accident_id = (?)", accident.getId());
            accident.getRules().forEach(rule -> jdbc.update(SAVE_RULE,
                    accident.getId(),
                    rule.getId()
            ));
        } catch (Exception e) {
            log.error("Save the rule error occurred ", e);
        }
    }

    public AccidentType findTypeById(int id) {
        log.info("Find the type by id");
        try {
            return jdbc.queryForObject(FIND_TYPE_BY_ID, this::createType, id);
        } catch (Exception e) {
            log.error("Find the type by id error occurred ", e);
        }
        return new AccidentType();
    }

    public List<AccidentType> findAllTypes() {
        log.info("Find all types");
        try {
            return jdbc.query(FIND_ALL_TYPES, this::createType);
        } catch (Exception e) {
            log.error("Find all types error occurred ", e);
        }
        return new ArrayList<>();
    }

    private AccidentType createType(ResultSet rs, int row) throws SQLException {
        AccidentType type = new AccidentType();
        type.setId(rs.getInt("id"));
        type.setName(rs.getString("name"));
        return type;
    }
}

