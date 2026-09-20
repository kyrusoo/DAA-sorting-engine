package com.engine.algorithm;

import com.engine.metrics.Metrics;

public class MergeSort {

    public static void sort(int[] a, Metrics metrics) {
        if (a == null || a.length <= 1) return;
        int[] buffer = new int[a.length];
        metrics.enterRecursion();
        sortRecursive(a, buffer, 0, a.length - 1, metrics);
        metrics.exitRecursion();
    }

    private static void sortRecursive(int[] a, int[] buffer, int low, int high, Metrics metrics) {
        if (low >= high) return;

        int mid = low + (high - low) / 2;

        metrics.enterRecursion();
        sortRecursive(a, buffer, low, mid, metrics);
        metrics.exitRecursion();

        metrics.enterRecursion();
        sortRecursive(a, buffer, mid + 1, high, metrics);
        metrics.exitRecursion();

        merge(a, buffer, low, mid, high, metrics);
    }

    private static void merge(int[] a, int[] buffer, int low, int mid, int high, Metrics metrics) {
        System.arraycopy(a, low, buffer, low, high - low + 1);

        int i = low, j = mid + 1;
        for (int k = low; k <= high; k++) {
            if (i > mid) {
                a[k] = buffer[j++];
            } else if (j > high) {
                a[k] = buffer[i++];
            } else {
                metrics.incrementComparisons();
                if (buffer[j] < buffer[i]) {
                    a[k] = buffer[j++];
                } else {
                    a[k] = buffer[i++];
                }
            }
        }
    }
}