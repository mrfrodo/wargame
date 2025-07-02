package com.frodo.wargame.service;

import com.frodo.wargame.domain.Battle;
import com.frodo.wargame.repository.BattleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BattleService {

    private final BattleRepository repository;

    public BattleService(BattleRepository repository) {
        this.repository = repository;
    }

    public List<Battle> getAllBattles() {
        return repository.findAll();
    }

    public Battle createBattle(Battle battle) {
        validateBattle(battle);
        checkDuplicateName(battle.name());
        return repository.save(battle);
    }

    private void validateBattle(Battle battle) {
        if (battle.name() == null || battle.name().isBlank()) {
            throw new IllegalArgumentException("Battle name must not be blank");
        }

        if (battle.battleyear() == null || battle.battleyear() < 0 || battle.battleyear() > 4000) {
            throw new IllegalArgumentException("Battle year must be between 0 and 4000");
        }
    }

    private void checkDuplicateName(String name) {
        boolean exists = repository.findAll().stream()
                .anyMatch(existing -> existing.name().equalsIgnoreCase(name));
        if (exists) {
            throw new IllegalStateException("A battle with the name '" + name + "' already exists");
        }
    }
}
