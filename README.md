# Fast Sorting & Selection Engine

DAA Assignment 1: Divide and Conquer & Asymptotic Notations.
MergeSort, QuickSort and QuickSelect for `int[]` in Java, with a `Metrics` class, a benchmark that writes `results.csv`, JUnit 5 tests, and a report.

**Report:** [REPORT.md](REPORT.md) (asymptotic bounds, recurrences, plots, Θ check, discussion)

## Requirements

* JDK 21 (Microsoft OpenJDK 21.0.12)
* IntelliJ IDEA (or Windows PowerShell)

## Build, test, benchmark

### Option A: Via IntelliJ IDEA
* **JUnit Tests:** Right-click `src/test/java/com/engine/algorithm/AlgorithmTest.java` $\rightarrow$ **Run 'AlgorithmTest'** (`Ctrl + Shift + F10`).
* **Benchmark:** Run `BenchmarkRunner.java` (`Shift + F10`). Generates `results.csv` in the project root.

### Option B: Via PowerShell Terminal
```powershell
# 1. Compile source code
mkdir -Force target/classes
javac -d target/classes (Get-ChildItem -Path src/main/java -Recurse -Filter *.java).FullName

# 2. Run Benchmark
java -cp target/classes com.engine.benchmark.BenchmarkRunner
```

## Project structure

```
src/
  main/java/com/engine/
    algorithm/   MergeSort, QuickSort, QuickSelect, InsertionSort
    metrics/     Metrics            comparisons, max recursion depth, nanoTime timer
    benchmark/   BenchmarkRunner    sizes x input types x 5 runs -> results.csv (median run)
  test/java/com/engine/
    algorithm/   AlgorithmTest      JUnit 5 test suite covering all algorithms & edge cases
plots/           time_vs_n.png, depth_vs_n.png, ratio_vs_n.png
results.csv      benchmark output: algorithm,input,n,time_ms,comparisons,max_depth
REPORT.md        report
README.md        readme
```

## Design decisions

| Requirement | Where / how |
|---|---|
| MergeSort: one reusable buffer | `MergeSort.sort` allocates `int[n]` once and passes it down; `merge` never allocates |
| MergeSort: cutoff 15 | `MergeSort.CUTOFF`; ranges of ≤ 15 elements use `InsertionSort.sort(a, low, high, metrics)` |
| MergeSort: linear merge | `merge` touches each element of `a[low..high]` once |
| QuickSort: random pivot | `ThreadLocalRandom` pivot is moved to `a[low]` before partitioning |
| QuickSort: smaller side first | recursion into the smaller part, `while` loop over the larger part, so depth ≤ log₂ n |
| QuickSort: duplicates | `QuickSort.partition3Way` (< pivot, = pivot, > pivot) |
| QuickSelect: reuse partition, one side | uses `QuickSort.partition3Way`, then continues only in the part that contains k |
| QuickSelect: invalid input | `IllegalArgumentException` with a clear message (empty array or out-of-bound $k$) |
| Metrics | `Metrics` object is passed into every algorithm (no static/global state): `incrementComparisons`, `enterRecursion` / `exitRecursion`, `startTimer` / `stopTimer` |
| Benchmark | 5 runs per case on fresh data, median run written; CSV uses `Locale.ROOT` so decimals use `.` |

Counting convention: every comparison of two array elements is counted once, including both tests (`<` and `>`) of the 3-way partition. Reading or writing to the buffer is not counted.

## Plots & Data Analysis

Visualizations are generated using Excel / Google Sheets from the benchmark output:
* Import `results.csv` into Excel/Sheets.
* Compute `ratio: =comparisons / (n * LOG2(n))` for sorts and `=comparisons / n` for QuickSelect.
* Generate scatter plots with logarithmic X-axis ($n$): `time_vs_n.png`, `depth_vs_n.png`, and `ratio_vs_n.png`.

## Git workflow

* `main`: only working code, release tag `v1.0`
* Feature branches: `feature/metrics`, `feature/mergesort`, `feature/quicksort`, `feature/select`, `docs/report`
* Every branch was merged into `main` with `--no-ff` to keep branch topology visible in `git log --graph`.
* Commit messages follow standard convention: `type: description` (e.g., `feat: add insertion sort cutoff for subarrays of 15 or fewer`).
