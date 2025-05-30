package com.frodo.wargame.controller;

import com.frodo.wargame.domain.HexTile;
import com.frodo.wargame.service.BattleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/start-game")
public class BattleController {

    private final BattleService battleService;

    public BattleController(BattleService battleService) {
        this.battleService = battleService;
    }

    @GetMapping
    public List<HexTile> generateHexMap() {
        List<HexTile> map = new ArrayList<>();
        String[] terrains = {"plains", "forest", "mountain", "river", "desert"};

        for (int q = 0; q < 10; q++) {
            for (int r = 0; r < 10; r++) {
                String terrain = terrains[(q + r) % terrains.length]; // simple pattern
                map.add(new HexTile(q, r, terrain));
            }
        }

        System.out.println(map);
        return map;
    }
}