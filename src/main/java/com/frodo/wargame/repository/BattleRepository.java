package com.frodo.wargame.repository;

import com.frodo.wargame.domain.Battle;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BattleRepository extends CrudRepository<Battle, Long> {
    @Override
    List<Battle> findAll();
}
