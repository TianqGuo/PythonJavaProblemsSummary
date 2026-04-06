# Exercise 3: Clean metric data, identify trends, and flag anomalies

**Source file:** `pandas/exercise3/exercise3_metric_trends_and_anomalies.py`

Dataset: daily evaluation metrics over 6 months (Jan–Jun 2025)
Columns: `[date, metric_name, value]`
Metrics: `realism_score`, `collision_rate`, `comfort_score`, `route_completion`

---

## Results Summary

### Step 1 — Missing data

NaN counts before cleaning:
| metric | NaN count | pattern |
|--------|-----------|---------|
| collision_rate | 15 | random + 5-day contiguous outage (Mar 10–14) |
| comfort_score | 11 | random |
| realism_score | 10 | random |
| route_completion | 4 | random |

Strategy comparison on the 5-day outage (Mar 10–14):
| date | raw | ffill | interpolate |
|------|-----|-------|-------------|
| Mar 09 | 0.04590 | 0.04590 | 0.04590 |
| Mar 10 | NaN | 0.04590 | 0.04354 |
| Mar 11 | NaN | 0.04590 | 0.04119 |
| Mar 12 | NaN | 0.04590 | 0.03883 |
| Mar 13 | NaN | 0.04590 | 0.03647 |
| Mar 14 | NaN | 0.04590 | 0.03412 |
| Mar 15 | 0.03176 | 0.03176 | 0.03176 |

- `ffill` holds the last known value flat — honest but inaccurate for a trending metric
- `interpolate` ramps smoothly between the two known neighbors — more appropriate here
- Strategy chosen: `interpolate` (best for time-series with trends)

---

### Step 2 — 7-day rolling average

- `rolling(7, min_periods=1).mean()` and `.std()` computed per metric via `groupby().transform()`
- `min_periods=1` prevents NaN at the start of the window where fewer than 7 days exist
- Rolling std is needed for Step 3 anomaly detection

---

### Step 3 — Anomaly detection (> 2 rolling std from rolling mean)

Total flagged: **8 anomalies (2 per metric)**

| date | metric | value | rolling_mean | deviation (σ) |
|------|--------|-------|--------------|----------------|
| Feb 15 | collision_rate | 0.1596 | 0.0577 | +2.26 |
| May 11 | collision_rate | 0.1248 | 0.0440 | +2.26 |
| Apr 09 | comfort_score | 0.8491 | 0.7920 | +2.14 |
| May 07 | comfort_score | 0.8422 | 0.7821 | +2.16 |
| Apr 01 | realism_score | 0.6635 | 0.7795 | -2.21 |
| May 31 | realism_score | 0.6894 | 0.8091 | -2.16 |
| Jan 19 | route_completion | 0.9511 | 0.9222 | +2.08 |
| Mar 02 | route_completion | 0.8413 | 0.9080 | -2.12 |

---

### Step 4 — Trend detection per metric

| metric | Pearson r | p_value | direction | significant |
|--------|-----------|---------|-----------|-------------|
| realism_score | +0.781 | 0.0 | upward trend | Yes |
| collision_rate | −0.592 | 0.0 | downward trend | Yes |
| comfort_score | −0.122 | 0.101 | flat | No |
| route_completion | +0.025 | 0.741 | flat | No |

- `realism_score` and `collision_rate` have statistically significant trends
- `comfort_score` and `route_completion` show no significant trend (p > 0.05)
- `collision_rate` downward trend = improving safety (lower collision rate over time)

---

## Questions & Pitfalls

### Q1: Why rolling std instead of z-score or IQR?

All three detect outliers, but z-score and IQR use **global** statistics — they fail on trended data:

```
Global z-score on realism_score (upward trend 0.75 → 0.85):
  global mean ≈ 0.80
  Jan values (~0.75) → z = (0.75 - 0.80) / std → flagged as anomaly
                                                   ← WRONG, just early in trend

Rolling std in Jan window:
  rolling mean ≈ 0.75
  Jan values (~0.75) → deviation ≈ 0 → not flagged ← CORRECT
```

**Rule of thumb:**

| Method | Use when |
|--------|----------|
| Global z-score | Data is stationary (no trend, stable mean over time) |
| Global IQR | Stationary data with heavy tails or skew |
| Rolling std | Time-series with trends or seasonality — anomaly relative to local context |

**Threshold guide:**
- `> 2σ` catches ~5% of points in a normal distribution (broader net)
- `> 3σ` catches ~0.3% (stricter — only extreme outliers)

---

### Q2: When to use Pearson vs other correlation methods?

**Pearson r** is the right default for trend detection because a trend *is* a linear relationship (time index vs value). But it is not always the best choice.

| Method | Measures | Use when | Avoid when |
|--------|----------|----------|------------|
| **Pearson r** | Linear correlation | Trend detection, normally distributed, no outliers | Data has outliers, or relationship is nonlinear |
| **Spearman ρ** | Monotonic rank correlation | Robust to outliers, or trend is nonlinear but always increasing/decreasing | You need to quantify exact linear slope |
| **Kendall τ** | Rank concordance | Small samples, very robust | Large datasets (slower to compute) |
| **Cosine similarity** | Angle between vectors | NLP / text similarity, feature vector comparison | Trend detection — ignores magnitude, only cares about direction |
| **Cross-correlation** | Time-lagged relationship | "Does metric A predict metric B N days later?" | Same-time correlation |
| **DTW** (Dynamic Time Warping) | Shape similarity with time shifts | Comparing time series that may be offset in time | Simple trend detection |

**Key distinction — Pearson vs Cosine:**
- Two metrics `[1, 2, 3]` and `[100, 200, 300]` have **cosine similarity = 1.0** (same direction) but **Pearson r = 1.0** too — however cosine ignores that one is 100× larger
- For trend detection, magnitude matters → use Pearson or Spearman, not cosine

**Decision tree:**
```
Linear trend expected?
  Yes → Pearson r
  No, but monotonic (always up or always down)?
    → Spearman (or Kendall for small n)
Comparing text / feature vectors?
  → Cosine similarity
Time series possibly time-shifted?
  → Cross-correlation or DTW
```

**For this exercise:** Pearson is correct — we ask "does value increase linearly with time index?" If `realism_score` had a curved improvement (fast at first, then leveling off), Spearman would be the better choice since it only requires monotonically increasing, not linearly increasing.

---

## Key Pandas Patterns Used

| Pattern | Purpose |
|---------|---------|
| `groupby().transform(lambda x: x.interpolate())` | Per-group in-place interpolation |
| `groupby().transform(lambda x: x.ffill())` | Per-group forward fill |
| `rolling(7, min_periods=1).mean()` | Smoothed rolling mean |
| `rolling(7, min_periods=1).std()` | Local std for anomaly threshold |
| `(value - rolling_mean).abs() > 2 * rolling_std` | Rolling anomaly flag |
| `groupby('metric_name').apply(trend_stats)` | Per-metric Pearson r + slope |
| `np.polyfit(day_index, values, deg=1)` | Linear regression slope per group |