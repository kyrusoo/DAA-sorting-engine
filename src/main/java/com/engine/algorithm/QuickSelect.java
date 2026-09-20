package com.engine.algorithm;

import com.engine.metrics.Metrics;
import java.util.concurrent.ThreadLocalRandom;

public class QuickSelect {

    public static int select(int[] a, int k, Metrics metrics) {
        if (a == null || a.length == 0 || k < 0 || k >= a.length) {
            throw new IllegalArgumentException("Invalid array or k index out of bounds: " + k);
        }

        int low = 0;
        int high = a.length - 1;

        while (low <= high) {
            if (low == high) return a[low];

            int pivotIdx = ThreadLocalRandom.current().nextInt(low, high + 1);
            QuickSort.swap(a, low, pivotIdx);

            int[] bounds = QuickSort.partition3Way(a, low, high, metrics);
            int lt = bounds[0];
            int gt = bounds[1];

            if (k >= lt && k <= gt) {
                return a[k];
            } else if (k < lt) {
                high = lt - 1;
            } else {
                low = gt + 1;
            }
        }
        return a[low];
    }
}