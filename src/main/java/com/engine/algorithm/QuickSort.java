package com.engine.algorithm;

import com.engine.metrics.Metrics;
import java.util.concurrent.ThreadLocalRandom;

public class QuickSort {

    public static void sort(int[] a, Metrics metrics) {
        if (a == null || a.length <= 1) return;
        metrics.enterRecursion();
        sortRecursive(a, 0, a.length - 1, metrics);
        metrics.exitRecursion();
    }

    private static void sortRecursive(int[] a, int low, int high, Metrics metrics) {
        while (low < high) {
            int pivotIdx = ThreadLocalRandom.current().nextInt(low, high + 1);
            swap(a, low, pivotIdx);

            int[] bounds = partition3Way(a, low, high, metrics);
            int lt = bounds[0];
            int gt = bounds[1];

            if ((lt - low) < (high - gt)) {
                metrics.enterRecursion();
                sortRecursive(a, low, lt - 1, metrics);
                metrics.exitRecursion();
                low = gt + 1;
            } else {
                metrics.enterRecursion();
                sortRecursive(a, gt + 1, high, metrics);
                metrics.exitRecursion();
                high = lt - 1;
            }
        }
    }

    public static int[] partition3Way(int[] a, int low, int high, Metrics metrics) {
        int pivot = a[low];
        int lt = low;
        int gt = high;
        int i = low + 1;

        while (i <= gt) {
            metrics.incrementComparisons();
            if (a[i] < pivot) {
                swap(a, lt++, i++);
            } else if (a[i] > pivot) {
                swap(a, i, gt--);
            } else {
                i++;
            }
        }
        return new int[]{lt, gt};
    }

    public static void swap(int[] a, int i, int j) {
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }
}