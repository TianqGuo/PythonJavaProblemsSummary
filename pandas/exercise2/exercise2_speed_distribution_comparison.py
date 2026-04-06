import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
from scipy.stats import ks_2samp

# ─────────────────────────────────────────────────────────────────────────────
# Exercise 2: Are simulated and real-world vehicle speeds similar?
#
# DataFrames: sim_speeds, real_speeds
# Columns:    vehicle_id, timestamp, speed_mph, road_type
#
# Steps:
#   1. Compare descriptive statistics (mean, std, percentiles) by road_type
#   2. Compute KS test per road_type — is the distribution difference significant?
#   3. Rank road_types by discrepancy size
#   4. Visualize distributions per road_type (saved to exercise2/plots/)
# ─────────────────────────────────────────────────────────────────────────────

# ── Data generation ───────────────────────────────────────────────────────────
# The data is designed so each road_type has a different level of discrepancy:
#   highway:     small mean shift (+2 mph), slightly tighter sim std  → minor gap
#   urban:       large mean shift (+5 mph), wider sim std             → biggest gap
#   residential: very small mean shift (-1 mph), same std             → nearly identical
#
# This gives us a realistic scenario where simulation is well-calibrated on
# some road types but has a notable gap on others (urban is the problem area).

np.random.seed(42)

ROAD_PARAMS = {
    #                real_mean  real_std  sim_mean  sim_std   n_real  n_sim
    'highway':      (65,        8,        67,        7,        3000,   3000),
    'urban':        (25,        6,        30,        9,        4000,   4000),
    'residential':  (20,        4,        19,        4,        3000,   3000),
}

def make_df(version, seed_offset):
    rows = []
    vid = 0
    for road_type, params in ROAD_PARAMS.items():
        real_mean, real_std, sim_mean, sim_std, n_real, n_sim = params
        mean = sim_mean  if version == 'sim'  else real_mean
        std  = sim_std   if version == 'sim'  else real_std
        n    = n_sim     if version == 'sim'  else n_real
        np.random.seed(seed_offset)
        speeds = np.random.normal(mean, std, n).clip(0)   # speeds can't be negative
        timestamps = pd.date_range('2025-01-01', periods=n, freq='min')
        for i in range(n):
            rows.append({
                'vehicle_id': vid + i,
                'timestamp':  timestamps[i],
                'speed_mph':  round(speeds[i], 2),
                'road_type':  road_type,
            })
        vid += n
    return pd.DataFrame(rows)

sim_speeds  = make_df('sim',  seed_offset=42)
real_speeds = make_df('real', seed_offset=99)

road_types = sorted(sim_speeds['road_type'].unique())


# ── Step 1: Descriptive statistics by road_type ───────────────────────────────
# describe() gives count/mean/std/min/percentiles/max in one call.
# We compute it for both DataFrames, then build a side-by-side diff table
# so it's easy to spot where the numbers diverge.
#
# Key stats to compare:
#   mean  — is the average speed offset?
#   std   — is the spread different? (sim may be over- or under-dispersed)
#   p50   — does the median match? (robust to outliers unlike mean)
#   p95   — do the tails match? (important for safety-critical edge cases)
print("=" * 60)
print("STEP 1: Descriptive statistics by road_type")
print("=" * 60)

stats_to_show = ['mean', 'std', '25%', '50%', '75%', '95th_pct']

for road in road_types:
    real_s = real_speeds[real_speeds['road_type'] == road]['speed_mph']
    sim_s  = sim_speeds [sim_speeds ['road_type'] == road]['speed_mph']

    real_desc = real_s.describe(percentiles=[0.25, 0.5, 0.75, 0.95])
    sim_desc  = sim_s .describe(percentiles=[0.25, 0.5, 0.75, 0.95])

    # Build a compact comparison table: one row per stat, real vs sim vs diff
    summary = pd.DataFrame({
        'real': real_desc,
        'sim':  sim_desc,
    })
    summary['diff (sim-real)'] = summary['sim'] - summary['real']
    print(f"\n--- {road} ---")
    print(summary.loc[['mean', 'std', '25%', '50%', '75%', '95%']].round(2))


# ── Step 2: KS test per road_type ────────────────────────────────────────────
# The KS (Kolmogorov-Smirnov) test checks whether two samples come from the
# same underlying distribution — it is sensitive to differences in shape,
# spread, and location, not just means.
#
# KS statistic: the maximum vertical distance between the two CDFs.
#   Large stat → distributions differ a lot at their worst point.
#   p < 0.05   → the difference is unlikely to be random noise.
#
# We also compute Cohen's d here to show effect size alongside p-value,
# since large n can produce tiny p-values for practically irrelevant gaps.
print("\n" + "=" * 60)
print("STEP 2: KS test per road_type")
print("=" * 60)

def cohens_d(a, b):
    # Pooled standard deviation
    pooled_std = np.sqrt((a.std()**2 + b.std()**2) / 2)
    return abs(a.mean() - b.mean()) / pooled_std

ks_results = []
for road in road_types:
    real_s = real_speeds[real_speeds['road_type'] == road]['speed_mph']
    sim_s  = sim_speeds [sim_speeds ['road_type'] == road]['speed_mph']

    stat, pval = ks_2samp(real_s, sim_s)
    d          = cohens_d(real_s, sim_s)
    mean_diff  = sim_s.mean() - real_s.mean()

    ks_results.append({
        'road_type':  road,
        'ks_stat':    round(stat, 4),
        'p_value':    round(pval, 4),
        'cohens_d':   round(d, 3),
        'mean_diff':  round(mean_diff, 2),
        'significant': pval < 0.05,
    })

ks_df = pd.DataFrame(ks_results).set_index('road_type')
print(ks_df)


# ── Step 3: Rank road_types by discrepancy size ───────────────────────────────
# p-value alone does not tell us which road type has the WORST calibration gap.
# We rank by KS statistic (distribution-level discrepancy) and Cohen's d
# (practical effect size) together to identify where to focus calibration work.
#
# High KS stat → large gap somewhere in the distribution (could be tail or center)
# High Cohen's d → large mean shift relative to spread
print("\n" + "=" * 60)
print("STEP 3: Road types ranked by discrepancy (worst first)")
print("=" * 60)

ranked = ks_df.sort_values('ks_stat', ascending=False)
print(ranked[['ks_stat', 'cohens_d', 'mean_diff', 'significant']])

worst = ranked.index[0]
print(f"\nBiggest discrepancy: '{worst}'")
print(f"  KS stat = {ranked.loc[worst, 'ks_stat']} "
      f"| Cohen's d = {ranked.loc[worst, 'cohens_d']} "
      f"| mean diff = {ranked.loc[worst, 'mean_diff']} mph")

d_val = ranked.loc[worst, 'cohens_d']
if d_val < 0.2:
    label = "small — practically negligible"
elif d_val < 0.5:
    label = "medium — worth investigating"
elif d_val < 0.8:
    label = "large — significant calibration gap"
else:
    label = "very large — critical calibration issue"
print(f"  Effect size interpretation: {label}")


# ── Step 4: Visualize distributions per road_type ────────────────────────────
# Side-by-side histograms make it easy to see where distributions diverge:
#   - A mean shift shows as two humps offset left/right
#   - A std difference shows as one curve being wider/narrower
#   - Tail differences show as one histogram extending further
#
# Plots are saved to exercise2/plots/ (one file per road_type) since this
# environment has no display. Open them in any image viewer.
import os
plots_dir = os.path.join(os.path.dirname(__file__), 'plots')
os.makedirs(plots_dir, exist_ok=True)

print("\n" + "=" * 60)
print("STEP 4: Saving distribution plots to exercise2/plots/")
print("=" * 60)

for road in road_types:
    real_s = real_speeds[real_speeds['road_type'] == road]['speed_mph']
    sim_s  = sim_speeds [sim_speeds ['road_type'] == road]['speed_mph']

    ks_stat = ks_df.loc[road, 'ks_stat']
    d       = ks_df.loc[road, 'cohens_d']
    pval    = ks_df.loc[road, 'p_value']

    fig, axes = plt.subplots(1, 2, figsize=(12, 4))
    fig.suptitle(
        f"{road.capitalize()}  |  KS stat={ks_stat}  Cohen's d={d}  p={pval}",
        fontsize=13, fontweight='bold'
    )

    # Left: overlapping histograms — best for spotting mean/spread differences
    bins = np.linspace(
        min(real_s.min(), sim_s.min()),
        max(real_s.max(), sim_s.max()),
        40
    )
    axes[0].hist(real_s, bins=bins, alpha=0.5, label='real', color='steelblue', density=True)
    axes[0].hist(sim_s,  bins=bins, alpha=0.5, label='sim',  color='tomato',    density=True)
    axes[0].axvline(real_s.mean(), color='steelblue', linestyle='--', linewidth=1.5,
                    label=f'real mean={real_s.mean():.1f}')
    axes[0].axvline(sim_s.mean(),  color='tomato',    linestyle='--', linewidth=1.5,
                    label=f'sim mean={sim_s.mean():.1f}')
    axes[0].set_title('Overlapping histograms (density)')
    axes[0].set_xlabel('speed_mph')
    axes[0].legend()

    # Right: CDF comparison — the KS statistic is the max vertical gap between these two lines
    real_sorted = np.sort(real_s)
    sim_sorted  = np.sort(sim_s)
    axes[1].plot(real_sorted, np.arange(1, len(real_sorted)+1) / len(real_sorted),
                 label='real', color='steelblue')
    axes[1].plot(sim_sorted,  np.arange(1, len(sim_sorted)+1)  / len(sim_sorted),
                 label='sim',  color='tomato')
    axes[1].set_title('CDF comparison (KS stat = max vertical gap)')
    axes[1].set_xlabel('speed_mph')
    axes[1].set_ylabel('cumulative proportion')
    axes[1].legend()

    plt.tight_layout()
    out_path = os.path.join(plots_dir, f'{road}_distribution.png')
    plt.savefig(out_path, dpi=120)
    plt.close()
    print(f"  Saved: plots/{road}_distribution.png")

print("\nDone.")