package com.frodo.wargame;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CombatUtilsTest {

    @Test
    public void should_return_damage_within_expected_range_when_variance_is_applied() {
        int attackPower = 50;
        int defense = 30;
        double variancePercent = 20.0;

        // Expected base damage
        int baseDamage = Math.max(1, attackPower - defense); // = 20
        double variance = (variancePercent / 100.0) * baseDamage; // = 4.0
        int minExpected = (int) Math.floor(baseDamage - variance); // = 16
        int maxExpected = (int) Math.ceil(baseDamage + variance);  // = 24

        int damage = CombatUtils.calculateDamage(attackPower, defense, variancePercent);

        assertTrue(
                damage >= minExpected && damage <= maxExpected,
                "Damage should be between " + minExpected + " and " + maxExpected + ", but was: " + damage
        );
    }
}
