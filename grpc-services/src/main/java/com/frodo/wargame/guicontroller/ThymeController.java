package com.frodo.wargame.guicontroller;

import com.frodo.wargame.service.BattleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ThymeController {

    private final BattleService battleService;

    public ThymeController(BattleService battleService) {
        this.battleService = battleService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Wargame");
        model.addAttribute("heroName", "Aragorn");
        model.addAttribute("battles", battleService.getAllBattles()); // 👈 this line is key
        return "home"; // maps to home.html
    }

}
