# Exercise 1: Did realism improve from model v1 to v2?

**Source file:** `pandas/exercise1_model_realism_comparison.py`

Dataset: 10,000 simulation runs — `[scenario_id, scenario_type, realism_score, model_version, run_date]`

---

## Results Summary

### Step 1 — Aggregate means
| model | mean realism_score |
|-------|--------------------|
| v1    | 0.8005             |
| v2    | 0.8167             |

- KS test: p=0.0000, T-test: p=0.0000
- Aggregate conclusion: **v2 > v1, statistically significant**
- But this is misleading — see Step 3.

### Step 2 — Per scenario_type means
| scenario_type | v1     | v2     | winner |
|---------------|--------|--------|--------|
| highway       | 0.8532 | 0.8392 | **v1** |
| urban         | 0.7966 | 0.7896 | **v1** |
| parking       | 0.7516 | 0.7390 | **v1** |

- KS test significant for all three types — v1 genuinely better in every type.

### Step 3 — Simpson's Paradox
**DETECTED.** v2 wins aggregate but loses all 3 per-type comparisons.

Cause — scenario-type mix imbalance:
| model | highway | urban | parking |
|-------|---------|-------|---------|
| v1    | 33%     | 33%   | 33%     |
| v2    | **70%** | 15%   | 15%     |

v2 ran 70% of its tests on `highway` (the highest-scoring type by nature), inflating its aggregate mean — not genuine model improvement.

### Step 4 — 95% Confidence intervals
CIs do not overlap between v1 and v2 for any scenario type, confirming the per-type differences are real and not noise. Example for highway:
- v1: [0.8499, 0.8565]
- v2: [0.8369, 0.8415]

### Step 5 — Sample size check
All groups well above threshold (n ≥ 30). Results are statistically trustworthy.

---

## Questions & Pitfalls

### Q1: p=0.0000 — is that even possible?

**No, p is never literally zero.** Python displays `0.0000` because the true value (e.g. `1.4e-23`) is too small for 4 decimal places.

**Yes, it is directly driven by large n.** The t-statistic formula shows why:
```
t = (mean_v1 - mean_v2) / (std / sqrt(n))
                                    ↑
                          larger n → larger t → smaller p
```
With n=5,000 per model, even a tiny real difference produces an enormous t-statistic and a near-zero p-value.

**The pitfall:** a p-value alone is misleading at large n. You must also report **effect size**.

| Metric    | What it answers                              |
|-----------|----------------------------------------------|
| p-value   | Is the difference *real* (not random noise)? |
| effect size | Is the difference *meaningful* in practice? |

**Cohen's d** is the standard effect size measure:
```
d = (mean_v1 - mean_v2) / pooled_std
```
| d value  | Interpretation |
|----------|---------------|
| < 0.2    | Small — probably not meaningful |
| ~ 0.5    | Medium |
| > 0.8    | Large |

In this exercise: mean difference ≈ 0.016, std ≈ 0.07 → **d ≈ 0.23 (small)**. Statistically real, but practically tiny.

**Rule of thumb:** always report p-value AND effect size together. p alone is misleading at large n.

---

### Q2: How can all 3 types disagree with the aggregate winner?

This is **Simpson's Paradox** — the aggregate trend reverses when data is broken into subgroups.

Intuition with a school exam analogy:
```
             Easy exam (avg 90%)    Hard exam (avg 60%)    Overall
Student A:   10 tests → 91%         90 tests → 61%         64.0%
Student B:   90 tests → 89%         10 tests → 59%         86.0%
```
- Student A beats Student B on **both** exam types
- But Student B has a **higher overall average** — because they took mostly easy exams

The overall average is shaped by both **(1) per-type performance** and **(2) how many runs each group took per type**. When those two factors point in opposite directions, you get Simpson's Paradox.

**Applied here:**
- v2 ran 70% on `highway` (highest-scoring type) vs. v1's balanced 33%
- That mix pulls v2's aggregate up, masking that v1 is better within every type

**The lesson:** never trust an aggregate comparison alone when group compositions differ. Always break down by subgroup (Step 2) before drawing conclusions.

---

## Key Pandas Patterns Used

| Pattern | Purpose |
|---------|---------|
| `df[df['col'] == val]` | Filter by model version |
| `groupby(['a','b']).mean().unstack()` | Per-type × per-model comparison table |
| `groupby().apply(custom_fn)` | Compute CI per group |
| `idxmax(axis=1)` | Find per-row winner across columns |
| `div(mix.sum(axis=1), axis=0)` | Normalize run counts to proportions |
| loop + `ks_2samp` / `ttest_ind` | Per-type statistical tests |