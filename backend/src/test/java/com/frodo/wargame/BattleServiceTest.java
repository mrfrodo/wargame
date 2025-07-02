package com.frodo.wargame;

import com.frodo.wargame.domain.Battle;
import com.frodo.wargame.repository.BattleRepository;
import com.frodo.wargame.service.BattleService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BattleServiceTest {

    @Mock
    private BattleRepository battleRepository;

    @InjectMocks
    private BattleService battleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createBattle_validInput_shouldSave() {
        // Arrange
        Battle input = new Battle(null, "Battle of Moria", 1980);
        Battle saved = new Battle(1L, "Battle of Moria", 1980);

        when(battleRepository.findAll()).thenReturn(List.of());
        when(battleRepository.save(input)).thenReturn(saved);

        // Act
        Battle result = battleService.createBattle(input);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.id());
        verify(battleRepository).save(input);
    }

    @Test
    void createBattle_blankName_shouldThrow() {
        // Arrange
        Battle input = new Battle(null, "   ", 2000);

        // Act & Assert
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> battleService.createBattle(input));

        assertEquals("Battle name must not be blank", ex.getMessage());
        verify(battleRepository, never()).save(any());
    }

    @Test
    void createBattle_invalidYear_shouldThrow() {
        // Arrange
        Battle input = new Battle(null, "Battle of Doom", -50);

        // Act & Assert
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> battleService.createBattle(input));

        assertEquals("Battle year must be between 0 and 4000", ex.getMessage());
        verify(battleRepository, never()).save(any());
    }

    @Test
    void createBattle_duplicateName_shouldThrow() {
        // Arrange
        Battle existing = new Battle(1L, "Battle of Isengard", 3018);
        Battle input = new Battle(null, "Battle of Isengard", 3019);

        when(battleRepository.findAll()).thenReturn(List.of(existing));

        // Act & Assert
        IllegalStateException ex = assertThrows(IllegalStateException.class,
                () -> battleService.createBattle(input));

        assertTrue(ex.getMessage().contains("already exists"));
        verify(battleRepository, never()).save(any());
    }
}

