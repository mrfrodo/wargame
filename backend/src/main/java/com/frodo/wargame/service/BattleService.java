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
        return repository.save(battle);
    }
}