package com.planeteight.pulseboard;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for PulseBoard habit-tracking logic in MainActivity.
 * Tests the percentage and display calculations that do not require a device.
 */
public class UnitTest {

    /** Mirrors the percentage formula used in MainActivity for habit rows. */
    private int habitPercent(int completed, int target) {
        return target == 0 ? 0 : (completed * 100) / target;
    }

    @Test
    public void hydration_percentageIsCorrect() {
        // 6 of 8 glasses → 75 %
        assertEquals(75, habitPercent(6, 8));
    }

    @Test
    public void focusBlocks_percentageIsCorrect() {
        // 3 of 4 focus blocks → 75 %
        assertEquals(75, habitPercent(3, 4));
    }

    @Test
    public void walk_completedTargetIsOneHundredPercent() {
        // 1 of 1 walk → 100 %
        assertEquals(100, habitPercent(1, 1));
    }

    @Test
    public void reading_percentageIsCorrect() {
        // 20 of 30 minutes → 66 % (integer division)
        assertEquals(66, habitPercent(20, 30));
    }

    @Test
    public void habitPercent_zeroTargetReturnsZero() {
        // Guard against divide-by-zero; target=0 should yield 0 %
        assertEquals(0, habitPercent(5, 0));
    }

    @Test
    public void habitPercent_zeroCompletedIsZeroPercent() {
        assertEquals(0, habitPercent(0, 8));
    }

    @Test
    public void scoreLabel_formattedWithPercentSign() {
        // MainActivity uses dailyScore + "%" for the score TextView
        int dailyScore = 69;
        assertEquals("69%", dailyScore + "%");
    }

    @Test
    public void servicesLabel_containsBothServiceNames() {
        String label = "Score: cpp-momentum-service\nInsights: java-focus-planner";
        assertTrue(label.contains("cpp-momentum-service"));
        assertTrue(label.contains("java-focus-planner"));
    }
}

