package com.frodo.wargame.controller;

import com.frodo.wargame.domain.Battle;
import com.frodo.wargame.service.BattleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/battles")
public class BattleController {

    private final BattleService battleService;

    public BattleController(BattleService battleService) {
        this.battleService = battleService;
    }

    @GetMapping
    public List<Battle> getAllBattles() {
        return battleService.getAllBattles();
    }
}