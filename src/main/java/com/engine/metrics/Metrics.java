package com.engine.metrics;

public class Metrics {
    private long comparisons = 0;
    private int maxRecursionDepth = 0;
    private int currentRecursionDepth = 0;

    public void incrementComparisons() {
        this.comparisons++;
    }

    public void addComparisons(long count) {
        this.comparisons += count;
    }

    public void enterRecursion() {
        this.currentRecursionDepth++;
        if (this.currentRecursionDepth > this.maxRecursionDepth) {
            this.maxRecursionDepth = this.currentRecursionDepth;
        }
    }

    public void exitRecursion() {
        this.currentRecursionDepth--;
    }

    public long getComparisons() {
        return comparisons;
    }

    public int getMaxRecursionDepth() {
        return maxRecursionDepth;
    }

    public void reset() {
        this.comparisons = 0;
        this.maxRecursionDepth = 0;
        this.currentRecursionDepth = 0;
    }
}