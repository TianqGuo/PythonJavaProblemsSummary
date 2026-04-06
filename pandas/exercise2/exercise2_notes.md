# Exercise 2: Are simulated and real-world vehicle speeds similar?

**Source file:** `pandas/exercise2/exercise2_speed_distribution_comparison.py`

DataFrames: `sim_speeds`, `real_speeds` — columns: `[vehicle_id, timestamp, speed_mph, road_type]`

---

## Results Summary

### Step 1 — Descriptive statistics by road_type

| stat | highway real | highway sim | diff |
|------|-------------|-------------|------|
| mean | 65.34 | 67.22 | +1.88 |
| std  | 8.12  | 6.91  | -1.21 |
| p50  | 65.30 | 67.17 | +1.88 |
| p95  | 78.09 | 78.71 | +0.62 |

| stat | urban real | urban sim | diff |
|------|-----------|-----------|------|
| mean | 25.28 | 30.17 | **+4.90** |
| std  | 6.12  | 8.97  | **+2.86** |
| p50  | 25.30 | 30.19 | +4.89 |
| p95  | 35.03 | 45.06 | **+10.03** |

| stat | residential real | residential sim | diff |
|------|-----------------|-----------------|------|
| mean | 20.17 | 19.13 | -1.04 |
| std  | 4.06  | 3.95  | -0.11 |
| p50  | 20.15 | 19.10 | -1.05 |
| p95  | 26.54 | 25.69 | -0.85 |

Key observation: `urban` has both a **mean shift (+4.9 mph)** and a **wider spread (std +2.86)** — the simulation is not just faster but also more erratic. The p95 gap of +10 mph is particularly notable for safety-critical evaluation.

---

### Step 2 — KS test per road_type

| road_type | KS stat | p_value | Cohen's d | mean_diff | significant |
|-----------|---------|---------|-----------|-----------|-------------|
| urban | 0.2980 | 0.0 | 0.638 | +4.90 | Yes |
| highway | 0.1180 | 0.0 | 0.250 | +1.88 | Yes |
| residential | 0.1153 | 0.0 | 0.260 | -1.04 | Yes |

All p-values display as 0.0 due to large n (3000–4000 per group) — see pitfalls below.

---

### Step 3 — Ranked by discrepancy (worst first)

| rank | road_type | KS stat | Cohen's d | verdict |
|------|-----------|---------|-----------|---------|
| 1 | **urban** | 0.298 | 0.638 | Large — significant calibration gap |
| 2 | highway | 0.118 | 0.250 | Small — worth monitoring |
| 3 | residential | 0.115 | 0.260 | Small — worth monitoring |

`urban` is the clear priority for calibration work — both KS stat and Cohen's d are well above the others.

---

### Step 4 — Distribution plots

Saved to `plots/` — one PNG per road_type with two panels each:
- **Left: overlapping histograms** — shows mean shift (offset humps) and spread differences (width of curves)
- **Right: CDF comparison** — the KS statistic is literally the maximum vertical gap between the two CDF lines

Plot observations:
- `urban`: two clearly separated humps, large CDF gap, sim curve is flatter (higher std)
- `highway`: small offset, CDFs nearly overlap
- `residential`: nearly identical, tiny offset in opposite direction

---

## Questions & Pitfalls

### Q1: p=0.0000 again — is it valid? Is it useful?

**Valid: yes. Useful: not on its own at large n.**

The p-value correctly answers *"Is this difference real or random noise?"* — at n=3000+, the answer is always "yes, definitely real." p=0.0000 is not wrong, it just becomes **uninformative** because everything clears the significance bar, including `residential` which only differs by 1 mph.

| Sample size | p-value usefulness | Effect size usefulness |
|-------------|-------------------|----------------------|
| Small n | High — could be noise | Low — unstable |
| Large n | Low — everything is "significant" | High — reliable signal |

**Rule:** at large n, use p-value only as a binary gate ("is it real?"), then use effect size to answer "does it matter?"

---

### Q2: Is there a formula that divides p by Cohen's d?

**No — that is not a standard technique.** But the underlying intuition points to a real mathematical relationship between the t-statistic and Cohen's d:

```
d = t * sqrt(2 / n)       # derive d from t (equal group sizes)
t = d * sqrt(n / 2)       # derive t from d
```

This formula reveals exactly why large n causes p to collapse:
- As n grows, t is scaled up by sqrt(n)
- A larger t → smaller p, even if the true effect size d stays constant
- So p is sensitive to n; d is not

You can recover Cohen's d from a reported t-statistic using:
```python
d = t_stat * np.sqrt(2 / n)
```
This is useful when reading papers that only report t — you can compute the practical effect size yourself.

---

### The right mental model for these three metrics

```
p-value   →  "Is it real?"              (binary yes/no gate)
Cohen's d →  "How big is the mean gap?" (practical magnitude)
KS stat   →  "How different are the full shapes?" (captures tails, spread, shape — not just means)
```

| road_type | Real? (p) | Mean gap size (d) | Shape gap (KS) | Action |
|-----------|-----------|-------------------|----------------|--------|
| urban | Yes | 0.638 — large | 0.298 — large | Fix this first |
| highway | Yes | 0.250 — small | 0.118 — small | Monitor |
| residential | Yes | 0.260 — small | 0.115 — small | Monitor |

**Cohen's d interpretation scale:**
| d value | Interpretation |
|---------|---------------|
| < 0.2 | Small — practically negligible |
| 0.2–0.5 | Medium — worth investigating |
| 0.5–0.8 | Large — significant calibration gap |
| > 0.8 | Very large — critical calibration issue |

---

## Key Pandas Patterns Used

| Pattern | Purpose |
|---------|---------|
| `describe(percentiles=[0.25, 0.5, 0.75, 0.95])` | Get full percentile profile in one call |
| `pd.DataFrame({'real': desc1, 'sim': desc2})` | Side-by-side stat comparison table |
| `summary['diff'] = summary['sim'] - summary['real']` | Compute column-wise difference |
| loop + `ks_2samp` per group | Per-road-type distribution test |
| `sort_values('ks_stat', ascending=False)` | Rank groups by discrepancy |
| `np.sort` + `np.arange / len` | Manual CDF construction for plotting |