package com.frodo.wargame;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

/**
 * Utility class for combat-related calculations in wargames.
 * Provides methods for damage calculation, hit probability, and combat resolution.
 */
public final class CombatUtils {

    private static final Random RANDOM = new Random();

    private CombatUtils() {
        // Utility class - prevent instantiation
    }

    /**
     * Calculates damage dealt based on attack power, defense, and random factors.
     *
     * @param attackPower the attacking unit's power
     * @param defense the defending unit's defense value
     * @param variancePercent percentage of variance in damage (0-100)
     * @return calculated damage value
     */
    public static int calculateDamage(int attackPower, int defense, double variancePercent) {
        if (attackPower <= 0) return 0;
        if (defense < 0) defense = 0;
        if (variancePercent < 0) variancePercent = 0;
        if (variancePercent > 100) variancePercent = 100;

        // Base damage calculation: attack - defense with minimum of 1
        int baseDamage = Math.max(1, attackPower - defense);

        // Apply variance
        if (variancePercent > 0) {
            double variance = (variancePercent / 100.0) * baseDamage;
            double randomFactor = (RANDOM.nextDouble() * 2 - 1) * variance; // -variance to +variance
            baseDamage = Math.max(1, (int) Math.round(baseDamage + randomFactor));
        }

        return baseDamage;
    }

    /**
     * Calculates hit probability based on attacker accuracy and defender evasion.
     *
     * @param attackerAccuracy attacker's accuracy rating (0-100)
     * @param defenderEvasion defender's evasion rating (0-100)
     * @return hit probability as percentage (0-100)
     */
    public static double calculateHitProbability(int attackerAccuracy, int defenderEvasion) {
        if (attackerAccuracy < 0) attackerAccuracy = 0;
        if (attackerAccuracy > 100) attackerAccuracy = 100;
        if (defenderEvasion < 0) defenderEvasion = 0;
        if (defenderEvasion > 100) defenderEvasion = 100;

        // Base formula: accuracy - (evasion / 2), with minimum 5% and maximum 95%
        double hitChance = attackerAccuracy - (defenderEvasion / 2.0);
        return Math.max(5.0, Math.min(95.0, hitChance));
    }

    /**
     * Determines if an attack hits based on calculated probability.
     *
     * @param hitProbability hit probability percentage (0-100)
     * @return true if attack hits, false otherwise
     */
    public static boolean doesAttackHit(double hitProbability) {
        if (hitProbability <= 0) return false;
        if (hitProbability >= 100) return true;

        return RANDOM.nextDouble() * 100 < hitProbability;
    }

    /**
     * Calculates critical hit damage multiplier.
     *
     * @param baseCritChance base critical hit chance (0-100)
     * @param criticalDamageMultiplier damage multiplier for critical hits
     * @return damage multiplier (1.0 for normal hit, higher for critical)
     */
    public static double calculateCriticalHit(double baseCritChance, double criticalDamageMultiplier) {
        if (baseCritChance <= 0 || criticalDamageMultiplier <= 1.0) {
            return 1.0;
        }

        if (RANDOM.nextDouble() * 100 < baseCritChance) {
            return criticalDamageMultiplier;
        }

        return 1.0;
    }

    /**
     * Calculates experience points gained from combat.
     *
     * @param defeatedUnitLevel level of the defeated unit
     * @param victorLevel level of the victorious unit
     * @param baseExpReward base experience reward
     * @return experience points gained
     */
    public static int calculateExperienceGain(int defeatedUnitLevel, int victorLevel, int baseExpReward) {
        if (baseExpReward <= 0) return 0;
        if (defeatedUnitLevel <= 0) defeatedUnitLevel = 1;
        if (victorLevel <= 0) victorLevel = 1;

        // Experience scales based on level difference
        double levelDifference = (double) defeatedUnitLevel / victorLevel;
        double multiplier = Math.max(0.1, Math.min(2.0, levelDifference));

        return (int) Math.round(baseExpReward * multiplier);
    }

    /**
     * Calculates morale effect on combat effectiveness.
     *
     * @param currentMorale current morale value (0-100)
     * @return effectiveness multiplier (0.5 to 1.5)
     */
    public static double calculateMoraleEffect(int currentMorale) {
        if (currentMorale < 0) currentMorale = 0;
        if (currentMorale > 100) currentMorale = 100;

        // Morale affects effectiveness: 50 morale = 1.0x, 0 morale = 0.5x, 100 morale = 1.5x
        return 0.5 + (currentMorale / 100.0);
    }

    /**
     * Calculates terrain modifier for combat.
     *
     * @param terrainType terrain type identifier
     * @param unitType unit type identifier
     * @return combat modifier (0.5 to 2.0)
     */
    public static double calculateTerrainModifier(String terrainType, String unitType) {
        if (terrainType == null || unitType == null) return 1.0;

        // Example terrain modifiers - customize based on your game rules
        return switch (terrainType.toLowerCase()) {
            case "forest" -> switch (unitType.toLowerCase()) {
                case "infantry" -> 1.2;
                case "cavalry" -> 0.8;
                case "artillery" -> 0.6;
                default -> 1.0;
            };
            case "mountain" -> switch (unitType.toLowerCase()) {
                case "infantry" -> 1.3;
                case "cavalry" -> 0.5;
                case "artillery" -> 0.7;
                default -> 1.0;
            };
            case "plains" -> switch (unitType.toLowerCase()) {
                case "cavalry" -> 1.3;
                case "artillery" -> 1.1;
                default -> 1.0;
            };
            case "river" -> switch (unitType.toLowerCase()) {
                case "infantry" -> 0.7;
                case "cavalry" -> 0.4;
                case "artillery" -> 0.8;
                default -> 1.0;
            };
            default -> 1.0;
        };
    }

    /**
     * Performs a complete combat calculation.
     *
     * @param attackerStats attacker's combat statistics
     * @param defenderStats defender's combat statistics
     * @param terrainModifier terrain effect on combat
     * @return combat result with damage and hit information
     */
    public static CombatResult performCombat(CombatStats attackerStats, CombatStats defenderStats, double terrainModifier) {
        // Calculate hit probability
        double hitProb = calculateHitProbability(attackerStats.accuracy(), defenderStats.evasion());
        boolean hit = doesAttackHit(hitProb);

        if (!hit) {
            return new CombatResult(0, false, false, hitProb);
        }

        // Calculate damage
        int baseDamage = calculateDamage(attackerStats.attackPower(), defenderStats.defense(), 20.0);

        // Apply critical hit
        double critMultiplier = calculateCriticalHit(attackerStats.criticalChance(), 2.0);
        boolean isCritical = critMultiplier > 1.0;

        // Apply morale and terrain modifiers
        double moraleEffect = calculateMoraleEffect(attackerStats.morale());
        double finalDamage = baseDamage * critMultiplier * moraleEffect * terrainModifier;

        return new CombatResult((int) Math.round(finalDamage), true, isCritical, hitProb);
    }

    /**
     * Record representing combat statistics for a unit.
     */
    public record CombatStats(
            int attackPower,
            int defense,
            int accuracy,
            int evasion,
            double criticalChance,
            int morale
    ) {}

    /**
     * Record representing the result of a combat calculation.
     */
    public record CombatResult(
            int damage,
            boolean hit,
            boolean critical,
            double hitProbability
    ) {}
}