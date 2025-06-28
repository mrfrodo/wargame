package com.frodo.wargame;

import com.frodo.wargame.domain.Battle;
import com.frodo.wargame.repository.BattleRepository;
import com.frodo.wargame.service.BattleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = {
        "spring.config.location=classpath:/application-test.properties"
})
public class BattleServiceIntegrationTest {

    @Autowired
    private BattleService battleService;

    @Autowired
    private BattleRepository battleRepository;
    
    @Test
    void testGetAllAllBattlesANDwritetoconsole() {
        List<Battle> battles = battleService.getAllBattles();
        battles.forEach(System.out::println);
    }

    @Test
    void testGetAllAllBattlesANDReturnsData() {
        List<Battle> battles = battleService.getAllBattles();
        assertThat(battles.size()).isEqualTo(4);
        System.out.println("battle size is 4");
    }

    @Test
    void testGetAllBattlesANDReturnsSomeData() {
        List<Battle> battles = battleService.getAllBattles();
        assertThat(battles)
                .isNotNull()
                .isNotEmpty()
                .anyMatch(b -> b.name().equals("TEST Battle of Five Armies"));
        System.out.println("battles include TEST Battle of Five Armies");
    }
}