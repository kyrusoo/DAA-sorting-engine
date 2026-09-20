package com.engine;

import com.engine.algorithm.MergeSort;
import com.engine.algorithm.QuickSort;
import com.engine.algorithm.QuickSelect;
import com.engine.metrics.Metrics;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class AlgorithmTest {

    private Metrics metrics;
    private final Random random = new Random(42);

    @BeforeEach
    void setUp() {
        metrics = new Metrics();
    }

    @Test
    void testMergeSortCorrectnessOn100Arrays() {
        for (int i = 0; i < 100; i++) {
            int[] original = random.ints(1000, -5000, 5000).toArray();
            int[] expected = original.clone();
            Arrays.sort(expected);

            metrics.reset();
            MergeSort.sort(original, metrics);
            assertArrayEquals(expected, original);
        }
    }

    @Test
    void testQuickSortCorrectnessOn100Arrays() {
        for (int i = 0; i < 100; i++) {
            int[] original = random.ints(1000, -5000, 5000).toArray();
            int[] expected = original.clone();
            Arrays.sort(expected);

            metrics.reset();
            QuickSort.sort(original, metrics);
            assertArrayEquals(expected, original);
        }
    }

    @Test
    void testQuickSortDepthCheckSortedInput() {
        int n = 100000;
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = i;

        metrics.reset();
        QuickSort.sort(arr, metrics);

        double maxAllowedDepth = 2 * (Math.log(n) / Math.log(2));
        assertTrue(metrics.getMaxRecursionDepth() <= maxAllowedDepth,
                "Max recursion depth (" + metrics.getMaxRecursionDepth() +
                        ") exceeded bound (" + maxAllowedDepth + ")");
    }

    @Test
    void testQuickSelectCorrectnessOn100Arrays() {
        for (int i = 0; i < 100; i++) {
            int[] arr = random.ints(500, -10000, 10000).toArray();
            int[] sorted = arr.clone();
            Arrays.sort(sorted);

            int k = random.nextInt(arr.length);
            metrics.reset();
            int result = QuickSelect.select(arr, k, metrics);

            assertEquals(sorted[k], result);
        }
    }

    @Test
    void testEdgeCases() {
        int[] empty = new int[0];
        MergeSort.sort(empty, metrics);
        QuickSort.sort(empty, metrics);
        assertThrows(IllegalArgumentException.class, () -> QuickSelect.select(empty, 0, metrics));

        int[] single = {42};
        MergeSort.sort(single, metrics);
        assertEquals(42, single[0]);

        int[] equal = new int[1000];
        Arrays.fill(equal, 7);
        QuickSort.sort(equal, metrics);
        assertArrayEquals(new int[1000], Arrays.stream(equal).map(x -> 7).toArray());
    }
}