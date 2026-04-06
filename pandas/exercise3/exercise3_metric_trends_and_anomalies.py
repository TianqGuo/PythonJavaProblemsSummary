import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
import os
from scipy.stats import pearsonr

# ─────────────────────────────────────────────────────────────────────────────
# Exercise 3: Clean metric data, identify trends, and flag anomalies
#
# Dataset: daily evaluation metrics over 6 months with missing values
# Columns: date, metric_name, value
#
# Steps:
#   1. Handle missing data — compare drop vs ffill vs interpolate strategies
#   2. Compute 7-day rolling average per metric — smooth out noise
#   3. Detect anomalies — flag values > 2 std from the rolling mean
#   4. Groupby metric_name — identify which metrics show a trend over time
# ─────────────────────────────────────────────────────────────────────────────

# ── Data generation ───────────────────────────────────────────────────────────
# Four metrics with different designed behaviors:
#   realism_score    — gradual upward trend (model improving over time)
#   collision_rate   — gradual downward trend (safety improving over time)
#   comfort_score    — flat / no trend (stable)
#   route_completion — flat with a sudden dip mid-period (anomaly cluster)
#
# Missing data patterns:
#   - Random NaNs scattered throughout (simulates sensor dropouts)
#   - A contiguous 5-day gap in collision_rate (simulates a logging outage)
#   - This lets us show that different strategies suit different gap types

np.random.seed(42)

dates = pd.date_range('2025-01-01', '2025-06-30', freq='D')
n     = len(dates)                          # 181 days

def add_noise(base, std, n):
    return base + np.random.normal(0, std, n)

# Trend lines
realism_trend    = np.linspace(0.75, 0.85, n)    # rising
collision_trend  = np.linspace(0.05, 0.02, n)    # falling
comfort_base     = np.full(n, 0.80)              # flat
route_base       = np.full(n, 0.92)              # flat

metrics_raw = {
    'realism_score':    add_noise(realism_trend,   0.02, n),
    'collision_rate':   add_noise(collision_trend, 0.004, n).clip(0),
    'comfort_score':    add_noise(comfort_base,    0.025, n),
    'route_completion': add_noise(route_base,      0.015, n).clip(0, 1),
}

# Inject anomalies — sudden spikes/drops on specific days
anomaly_days = {
    'realism_score':    [30, 90, 150],      # random drops
    'collision_rate':   [45, 130],          # sudden spikes (bad days)
    'route_completion': [60, 61, 62, 63],   # 4-day dip cluster (system outage)
}
for metric, idxs in anomaly_days.items():
    for i in idxs:
        direction = -1 if metric != 'collision_rate' else 1
        metrics_raw[metric][i] += direction * np.random.uniform(0.08, 0.15)

# Build long-format DataFrame (one row per date×metric)
rows = []
for metric, values in metrics_raw.items():
    for i, (d, v) in enumerate(zip(dates, values)):
        rows.append({'date': d, 'metric_name': metric, 'value': round(v, 5)})

df = pd.DataFrame(rows)

# Inject missing values
# 1. Random NaNs (~5% of all rows)
nan_idx = np.random.choice(df.index, size=int(0.05 * len(df)), replace=False)
df.loc[nan_idx, 'value'] = np.nan

# 2. Contiguous 5-day gap in collision_rate (simulates a logging outage)
outage_mask = (df['metric_name'] == 'collision_rate') & \
              (df['date'].between('2025-03-10', '2025-03-14'))
df.loc[outage_mask, 'value'] = np.nan


# ── Step 1: Handle missing data ───────────────────────────────────────────────
# Three strategies — each suits a different scenario:
#
# dropna()     — safe when NaNs are rare and random; loses data
# ffill()      — good for slowly-changing signals; carries last known value forward
# interpolate()— best for smooth trends; fills by linear interpolation between neighbors
#                Particularly appropriate for the 5-day logging outage in collision_rate
#
# We compare all three on the contiguous gap to make the difference concrete.
print("=" * 60)
print("STEP 1: Missing data handling")
print("=" * 60)

print("\nNaN count per metric before cleaning:")
print(df.groupby('metric_name')['value'].apply(lambda x: x.isnull().sum()))

# Show the 5-day outage gap for collision_rate
gap = df[(df['metric_name'] == 'collision_rate') &
         (df['date'].between('2025-03-08', '2025-03-16'))][['date', 'value']]
print(f"\ncollision_rate around the 5-day outage (raw):")
print(gap.to_string(index=False))

# Apply interpolation as the main cleaning strategy (best for time-series trends)
# We sort by date within each metric first — interpolate works row-by-row in order
df_clean = (
    df.sort_values(['metric_name', 'date'])
      .copy()
)
df_clean['value'] = (
    df_clean.groupby('metric_name')['value']
            .transform(lambda x: x.interpolate(method='linear'))
)

# Compare the three strategies on the collision_rate gap
collision_raw   = df[df['metric_name'] == 'collision_rate'].set_index('date')['value']
filled_ffill    = collision_raw.ffill()
filled_interp   = collision_raw.interpolate(method='linear')

gap_comparison = pd.DataFrame({
    'raw':         collision_raw,
    'ffill':       filled_ffill,
    'interpolate': filled_interp,
}).loc['2025-03-08':'2025-03-16'].round(5)
print(f"\nStrategy comparison on the 5-day outage:")
print(gap_comparison)
print("\nNote: interpolate produces a smooth ramp; ffill holds the last value flat.")

print("\nNaN count per metric after interpolation:")
print(df_clean.groupby('metric_name')['value'].apply(lambda x: x.isnull().sum()))


# ── Step 2: Rolling averages ──────────────────────────────────────────────────
# Rolling average smooths out day-to-day noise so the underlying trend is visible.
# Window=7 means each point is the mean of itself + the 6 preceding days.
# min_periods=1 prevents NaN at the start where fewer than 7 days are available.
#
# We compute rolling mean and rolling std per metric — the std is needed for
# anomaly detection in Step 3.
print("\n" + "=" * 60)
print("STEP 2: 7-day rolling average per metric")
print("=" * 60)

ROLLING_WINDOW = 7

df_clean = df_clean.sort_values(['metric_name', 'date'])

df_clean['rolling_mean'] = (
    df_clean.groupby('metric_name')['value']
            .transform(lambda x: x.rolling(ROLLING_WINDOW, min_periods=1).mean())
)
df_clean['rolling_std'] = (
    df_clean.groupby('metric_name')['value']
            .transform(lambda x: x.rolling(ROLLING_WINDOW, min_periods=1).std())
)

print("\nSample rolling stats for realism_score (first 10 days):")
sample = df_clean[df_clean['metric_name'] == 'realism_score'][
    ['date', 'value', 'rolling_mean', 'rolling_std']
].head(10)
print(sample.to_string(index=False))


# ── Step 3: Anomaly detection (>2 std from rolling mean) ─────────────────────
# For each data point we ask: is it more than 2 rolling-std away from the
# rolling mean computed on the surrounding window?
#
# Using rolling statistics (not global mean/std) is important for trended data:
# a global mean would flag all early points in an upward trend as "low anomalies"
# even though they are normal for that point in time.
#
# Threshold: 2 std catches ~5% of points in a normal distribution.
# Use 3 std for a stricter threshold (catches ~0.3%).
print("\n" + "=" * 60)
print("STEP 3: Anomaly detection (> 2 rolling std from rolling mean)")
print("=" * 60)

ANOMALY_STD_THRESHOLD = 2

df_clean['anomaly'] = (
    (df_clean['value'] - df_clean['rolling_mean']).abs()
    > ANOMALY_STD_THRESHOLD * df_clean['rolling_std']
)

anomalies = df_clean[df_clean['anomaly']]
print(f"\nTotal anomalies flagged: {len(anomalies)}")
print("\nAnomalies per metric:")
print(anomalies.groupby('metric_name').size())

print("\nFlagged anomaly details:")
print(
    anomalies[['date', 'metric_name', 'value', 'rolling_mean', 'rolling_std']]
    .assign(deviation=lambda d: (d['value'] - d['rolling_mean']) / d['rolling_std'])
    .round(4)
    .sort_values(['metric_name', 'date'])
    .to_string(index=False)
)


# ── Step 4: Trend detection per metric ───────────────────────────────────────
# We quantify the trend in two ways:
#
# 1. Pearson correlation (r) between day-index and value:
#    r > 0 → upward trend, r < 0 → downward, r ≈ 0 → flat
#    p < 0.05 → trend is statistically significant
#
# 2. Linear regression slope (mph/day via np.polyfit):
#    Gives the practical rate of change per day
#
# Using groupby + apply lets us run both computations once per metric cleanly.
print("\n" + "=" * 60)
print("STEP 4: Trend detection per metric (groupby + apply)")
print("=" * 60)

def trend_stats(group):
    group = group.sort_values('date').dropna(subset=['value'])  # drop any residual NaNs
    day_index = np.arange(len(group))          # 0, 1, 2, ... 180
    values    = group['value'].values

    r, pval  = pearsonr(day_index, values)
    slope, _ = np.polyfit(day_index, values, deg=1)   # linear fit, slope per day

    if abs(r) < 0.3 or pval >= 0.05:
        direction = 'flat / no significant trend'
    elif r > 0:
        direction = 'upward trend'
    else:
        direction = 'downward trend'

    return pd.Series({
        'pearson_r':   round(r, 3),
        'p_value':     round(pval, 6),
        'slope_per_day': round(slope, 6),
        'total_change':  round(slope * len(group), 4),
        'significant': pval < 0.05,
        'direction':   direction,
    })

trend_table = df_clean.groupby('metric_name').apply(trend_stats)
print(trend_table)


# ── Step 5: Visualize — one plot per metric ───────────────────────────────────
# Each plot shows: raw values, 7-day rolling mean, and anomaly markers.
# The rolling mean line makes the trend visible through the noise.
# Red dots mark flagged anomalies so calibration teams can investigate them.
plots_dir = os.path.join(os.path.dirname(__file__), 'plots')
os.makedirs(plots_dir, exist_ok=True)

print("\n" + "=" * 60)
print("STEP 5: Saving plots to exercise3/plots/")
print("=" * 60)

for metric, group in df_clean.groupby('metric_name'):
    group = group.sort_values('date')
    anomaly_pts = group[group['anomaly']]

    trend_row  = trend_table.loc[metric]
    r_val      = trend_row['pearson_r']
    direction  = trend_row['direction']

    fig, ax = plt.subplots(figsize=(13, 4))

    ax.plot(group['date'], group['value'],
            color='steelblue', alpha=0.4, linewidth=0.8, label='daily value')
    ax.plot(group['date'], group['rolling_mean'],
            color='steelblue', linewidth=2, label=f'{ROLLING_WINDOW}-day rolling mean')
    ax.fill_between(
        group['date'],
        group['rolling_mean'] - ANOMALY_STD_THRESHOLD * group['rolling_std'],
        group['rolling_mean'] + ANOMALY_STD_THRESHOLD * group['rolling_std'],
        alpha=0.15, color='steelblue', label=f'±{ANOMALY_STD_THRESHOLD} rolling std band'
    )
    ax.scatter(anomaly_pts['date'], anomaly_pts['value'],
               color='red', zorder=5, s=60, label=f'anomaly ({len(anomaly_pts)})')

    ax.set_title(
        f"{metric}  |  r={r_val}  |  {direction}",
        fontsize=12, fontweight='bold'
    )
    ax.set_xlabel('date')
    ax.set_ylabel('value')
    ax.legend(fontsize=8)
    plt.tight_layout()

    out_path = os.path.join(plots_dir, f'{metric}_trend.png')
    plt.savefig(out_path, dpi=120)
    plt.close()
    print(f"  Saved: plots/{metric}_trend.png")

print("\nDone.")