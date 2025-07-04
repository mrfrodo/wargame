package com.frodo.wargame.dbloader;

import com.frodo.wargame.domain.Battle;
import jakarta.annotation.PostConstruct;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class StartupCache {

    private final ConcurrentHashMap<String, Object> dataMap = new ConcurrentHashMap<>();

    private final JdbcTemplate jdbcTemplate;

    public StartupCache(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    public void init() {
        //insertBattle("Battle of Five Armies", 2941);
        loadFromDatabase();

    }

    private void loadFromDatabase() {
        String sql = "SELECT \"id\", \"name\", \"battleyear\" FROM \"battles\"";
        List<Battle> battles = jdbcTemplate.query(sql, (rs, rowNum) ->
                new Battle(rs.getLong("id"), rs.getString("name"), rs.getInt("battleyear"))
        );

        // Put into map (key: battle name, value: BattleRecord or just year)
        for (Battle battle : battles) {
            dataMap.put(battle.name(), battle);
        }
    }

    private void insertBattle(String name, int year) {
        String sql = "INSERT INTO \"battles\" (\"name\", \"battleyear\") VALUES (?, ?)";
        jdbcTemplate.update(sql, name, year);
        System.out.println("Sample battles inserted into the database.");
    }
}
