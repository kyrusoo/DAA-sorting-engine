package com.engine.benchmark;

import com.engine.algorithm.MergeSort;
import com.engine.algorithm.QuickSort;
import com.engine.algorithm.QuickSelect;
import com.engine.metrics.Metrics;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;

public class BenchmarkRunner {
    private static final int[] SIZES = {1000, 10000, 100000, 1000000};
    private static final String[] TYPES = {"random", "sorted", "duplicates"};
    private static final int RUNS = 5;

    public static void main(String[] args) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("results.csv"))) {
            writer.println("algorithm,input,n,time_ms,comparisons,max_depth");

            for (String type : TYPES) {
                for (int n : SIZES) {
                    runBenchmark("MergeSort", type, n, writer);
                    runBenchmark("QuickSort", type, n, writer);
                    runBenchmark("QuickSelect", type, n, writer);
                }
            }
            System.out.println("Benchmark finished successfully. Results saved to results.csv.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void runBenchmark(String algo, String type, int n, PrintWriter writer) {
        long[] times = new long[RUNS];
        long lastComparisons = 0;
        int lastMaxDepth = 0;

        Metrics metrics = new Metrics();

        for (int r = 0; r < RUNS; r++) {
            int[] array = generateData(type, n);
            metrics.reset();

            long start = System.nanoTime();
            if (algo.equals("MergeSort")) {
                MergeSort.sort(array, metrics);
            } else if (algo.equals("QuickSort")) {
                QuickSort.sort(array, metrics);
            } else if (algo.equals("QuickSelect")) {
                QuickSelect.select(array, n / 2, metrics);
            }
            long elapsed = System.nanoTime() - start;

            times[r] = elapsed;
            lastComparisons = metrics.getComparisons();
            lastMaxDepth = metrics.getMaxRecursionDepth();
        }

        Arrays.sort(times);
        double medianTimeMs = times[RUNS / 2] / 1_000_000.0;

        writer.printf("%s,%s,%d,%.4f,%d,%d%n", algo, type, n, medianTimeMs, lastComparisons, lastMaxDepth);
        writer.flush();
    }

    private static int[] generateData(String type, int n) {
        int[] arr = new int[n];
        Random rnd = new Random(42);
        switch (type) {
            case "random":
                for (int i = 0; i < n; i++) arr[i] = rnd.nextInt();
                break;
            case "sorted":
                for (int i = 0; i < n; i++) arr[i] = i;
                break;
            case "duplicates":
                for (int i = 0; i < n; i++) arr[i] = rnd.nextInt(10);
                break;
        }
        return arr;
    }
}