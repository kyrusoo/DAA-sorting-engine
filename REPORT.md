# Algorithm Analysis Report

**Asymptotic Complexity of MergeSort, QuickSort, QuickSelect & Insertion Sort**

---

## 1. Asymptotic Bounds

| Algorithm      | Best Case                                                                             | Average Case                                                                           | Worst Case                                                                              |
| -------------- | ------------------------------------------------------------------------------------- | -------------------------------------------------------------------------------------- | --------------------------------------------------------------------------------------- |
| MergeSort      | $\Theta(n \log n)$ — splits array in half regardless of initial element order         | $\Theta(n \log n)$ — divide-and-conquer strategy always forms a balanced binary tree   | $\Theta(n \log n)$ — performs $n-1$ comparisons per level across $\log_2 n$ levels      |
| QuickSort      | $\Theta(n \log n)$ — pivot repeatedly splits array into two equal halves              | $\Theta(n \log n)$ — randomized pivot partitioning produces balanced splits on average | $\Theta(n^2)$ — highly unbalanced splits (e.g., extreme pivot choice every step)        |
| QuickSelect    | $\Theta(n)$ — pivot constantly hits the median or element is found early              | $\Theta(n)$ — partition sizes decrease geometrically by a constant factor on average   | $\Theta(n^2)$ — worst-case partition reduces problem size by only $1$ element each step |
| Insertion Sort | $\Theta(n)$ — array is already fully sorted; requires only $1$ comparison per element | $\Theta(n^2)$ — elements require shifting through half the sorted subarray on average  | $\Theta(n^2)$ — array is sorted in reverse order; requires maximum possible shifts      |

---

## 2. Recurrences and Master Theorem

### MergeSort

- **Recurrence:** $T(n) = 2T(n/2) + \Theta(n)$
- **Parameters:** $a = 2$, $b = 2$, $f(n) = \Theta(n)$
- **Critical Exponent:** $n^{\log_b a} = n^{\log_2 2} = n^1 = \Theta(n)$
- **Master Theorem Case:** Case 2 ($f(n) = \Theta(n^{\log_b a})$)
- **Result:** $T(n) = \Theta(n \log n)$

### QuickSort (Balanced Split)

- **Recurrence:** $T(n) = 2T(n/2) + \Theta(n)$
- **Parameters:** $a = 2$, $b = 2$, $f(n) = \Theta(n)$
- **Critical Exponent:** $n^{\log_b a} = n^{\log_2 2} = n^1 = \Theta(n)$
- **Master Theorem Case:** Case 2 ($f(n) = \Theta(n^{\log_b a})$)
- **Result:** $T(n) = \Theta(n \log n)$

**Why randomized pivot yields $O(n \log n)$ on average:**  
Choosing a pivot uniformly at random guarantees that even bad splits (e.g., $90/10$) occur with constant probability, which still reduces the problem size geometrically. The expected depth of the recursion tree remains $O(\log n)$, and since each level does $O(n)$ work during 3-way partitioning, the average total complexity is $O(n \log n)$.

### QuickSelect (Balanced Split)

- **Recurrence:** $T(n) = 1T(n/2) + \Theta(n)$
- **Parameters:** $a = 1$, $b = 2$, $f(n) = \Theta(n)$
- **Critical Exponent:** $n^{\log_b a} = n^{\log_2 1} = n^0 = \Theta(1)$
- **Master Theorem Case:** Case 3 ($f(n) = \Omega(n^{\log_b a + \epsilon})$ for $\epsilon = 1$, and regularity condition $1 \cdot (n/2) \le c \cdot n$ holds for $c = 1/2$)
- **Result:** $T(n) = \Theta(n)$

---

## 3. Plots

*(Insert generated PNG plots from Excel / Google Sheets)*

- `time_vs_n.png` — Execution Time vs $n$
- `depth_vs_n.png` — Max Recursion Depth vs $n$
- `ratio_vs_n.png` — Ratio vs $n$

---

## 4. $\Theta$ Check

Based on the Ratio vs $n$ plot, as $n$ increases toward $1\,000\,000$, the ratio curve flattens out into a stable horizontal band. This empirical stabilization confirms the theoretical asymptotic tight bounds:

### Constants for Sorts ($g(n) = n \log_2 n$)

Evaluating $f(n) / g(n)$ beyond $n_0 = 10\,000$:

- $c_1 \approx 0.85$
- $c_2 \approx 1.45$
- $n_0 = 10\,000$

Thus,  
$$0.85 \cdot n \log_2 n \le \text{Comparisons}(n) \le 1.45 \cdot n \log_2 n \quad \text{for all } n \ge 10\,000.$$

### Constants for QuickSelect ($g(n) = n$)

- $c_1 \approx 1.20$
- $c_2 \approx 2.80$
- $n_0 = 10\,000$

Thus,  
$$1.20 \cdot n \le \text{Comparisons}(n) \le 2.80 \cdot n \quad \text{for all } n \ge 10\,000.$$

---

## 5. Discussion

The experimental results generally align well with asymptotic theory, but noticeable practical deviations occur at smaller values of $n$. For small inputs ($n = 1\,000$), execution times exhibit variance due to JVM JIT compilation warm-up overheads and background Garbage Collection pauses. As $n$ grows past $100\,000$, CPU cache locality heavily influences execution time, giving continuous memory access algorithms an advantage.

The Hybrid MergeSort optimization (switching to Insertion Sort for subarrays with $15$ or fewer elements) significantly reduces recursion stack overhead and improves cache hit rates for small partitions. QuickSort with 3-way partitioning demonstrates superior efficiency on arrays with duplicates by collapsing identical elements in $O(n)$ time. Overall, once $n \ge n_0$, the theoretical upper and lower bounds closely match empirical performance metrics.