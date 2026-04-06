import pandas as pd
import numpy as np
from scipy.stats import ks_2samp, ttest_ind, t as t_dist

# ─────────────────────────────────────────────────────────────────────────────
# Exercise 1: Did realism improve from model v1 to v2?
#
# Dataset: 10,000 simulation runs
# Columns: scenario_id, scenario_type, realism_score, model_version, run_date
#
# Steps:
#   1. Filter by model version, compare aggregate means
#   2. Compare means and distributions per scenario_type
#   3. Check for Simpson's Paradox (aggregate vs. per-type trend)
#   4. Compute 95% confidence intervals per group
#   5. Flag scenario types with sample sizes too small to trust
# ─────────────────────────────────────────────────────────────────────────────

# ── Data generation ───────────────────────────────────────────────────────────
# The data is designed to demonstrate Simpson's Paradox:
#   - v1 is actually BETTER than v2 within every individual scenario_type
#   - BUT v2 appears better in the aggregate because it ran disproportionately
#     more on 'highway' (which naturally produces higher realism scores)
#
# This mirrors a real evaluation pitfall: a new model looks better overall
# simply because it was tested on easier scenarios, not because it improved.

np.random.seed(42)

# Per-type mean scores — v1 is slightly better in every type
SCORE_PARAMS = {
    #              v1 mean   v2 mean   std
    'highway':    (0.85,     0.84,     0.07),
    'urban':      (0.80,     0.79,     0.07),
    'parking':    (0.75,     0.74,     0.07),
}

# v1: balanced across types  |  v2: skewed heavily toward highway
# This skew is what causes Simpson's Paradox in the aggregate
TYPE_COUNTS = {
    #              v1    v2
    'highway':    (1667, 3500),
    'urban':      (1667,  750),
    'parking':    (1666,  750),
}

rows = []
scenario_id = 0
run_date = pd.Timestamp('2025-01-01')

for stype, (v1_n, v2_n) in TYPE_COUNTS.items():
    v1_mean, v2_mean, std = SCORE_PARAMS[stype]
    for version, n, mean in [('v1', v1_n, v1_mean), ('v2', v2_n, v2_mean)]:
        scores = np.random.normal(mean, std, n)
        dates  = pd.date_range(run_date, periods=n, freq='h')
        for i in range(n):
            rows.append({
                'scenario_id':   scenario_id + i,
                'scenario_type': stype,
                'realism_score': scores[i],
                'model_version': version,
                'run_date':      dates[i],
            })
        scenario_id += n

df = pd.DataFrame(rows)

SMALL_SAMPLE_THRESHOLD = 30   # minimum n to trust a statistical comparison


# ── Step 1: Filter by model version, compare aggregate means ─────────────────
# Start with the simplest question: overall, which model scored higher?
# This is the aggregate view — intentionally misleading in this dataset.
print("=" * 60)
print("STEP 1: Aggregate mean realism score per model version")
print("=" * 60)

v1 = df[df['model_version'] == 'v1']['realism_score']
v2 = df[df['model_version'] == 'v2']['realism_score']

agg_means = df.groupby('model_version')['realism_score'].mean()
print(agg_means)

ks_stat, ks_pval = ks_2samp(v1, v2)
t_stat,  t_pval  = ttest_ind(v1, v2)
print(f"\nKS test:  stat={ks_stat:.4f}, p={ks_pval:.4f}")
print(f"T-test:   stat={t_stat:.4f},  p={t_pval:.4f}")
print(f"Aggregate conclusion: v2 {'>' if agg_means['v2'] > agg_means['v1'] else '<='} v1 "
      f"({'significant' if t_pval < 0.05 else 'not significant'})")


# ── Step 2: Compare means and distributions per scenario_type ─────────────────
# Break the aggregate down by scenario_type — this is where the truth lives.
# groupby both keys, then unstack to get a side-by-side table (v1 col | v2 col).
print("\n" + "=" * 60)
print("STEP 2: Mean realism score per scenario_type x model_version")
print("=" * 60)

per_type_means = (
    df.groupby(['scenario_type', 'model_version'])['realism_score']
    .mean()
    .unstack()          # pivot model_version into columns → readable 2D table
)
print(per_type_means)

# Also compare distributions per type using KS test
print("\nKS test per scenario_type (v1 vs v2 distribution):")
for stype in df['scenario_type'].unique():
    a = df[(df['scenario_type'] == stype) & (df['model_version'] == 'v1')]['realism_score']
    b = df[(df['scenario_type'] == stype) & (df['model_version'] == 'v2')]['realism_score']
    stat, pval = ks_2samp(a, b)
    print(f"  {stype:<10}  stat={stat:.4f}, p={pval:.4f}  "
          f"({'significant' if pval < 0.05 else 'not significant'})")


# ── Step 3: Check for Simpson's Paradox ──────────────────────────────────────
# Simpson's Paradox: the aggregate trend reverses when data is split into subgroups.
# Detection logic:
#   - Find the aggregate winner (which model has higher overall mean)
#   - Count how many scenario_types agree vs. disagree with that aggregate result
#   - If the majority of types DISAGREE, Simpson's Paradox is present
#
# Why does it happen here?
#   v2 ran 70% of its runs on 'highway' (highest-scoring type by nature).
#   So its aggregate is pulled up by the mix, not by genuine improvement.
print("\n" + "=" * 60)
print("STEP 3: Simpson's Paradox check")
print("=" * 60)

agg_winner = 'v2' if agg_means['v2'] > agg_means['v1'] else 'v1'
print(f"Aggregate winner: {agg_winner}  (v1={agg_means['v1']:.4f}, v2={agg_means['v2']:.4f})")

# Count how many types agree with the aggregate winner
per_type_winner = per_type_means.idxmax(axis=1)   # which model is better per type
print("\nPer-type winner:")
print(per_type_winner)

agree_count    = (per_type_winner == agg_winner).sum()
disagree_count = (per_type_winner != agg_winner).sum()
print(f"\nTypes agreeing with aggregate:    {agree_count}")
print(f"Types disagreeing with aggregate: {disagree_count}")

if disagree_count > agree_count:
    print("\n*** SIMPSON'S PARADOX DETECTED ***")
    print(f"    '{agg_winner}' looks better overall but is worse in "
          f"{disagree_count}/{len(per_type_winner)} scenario types.")
    print("    The aggregate result is driven by scenario-type mix, not genuine improvement.")

    # Show the mix imbalance that causes it
    print("\nScenario-type distribution per model (shows the mix imbalance):")
    mix = df.groupby(['model_version', 'scenario_type']).size().unstack()
    mix_pct = mix.div(mix.sum(axis=1), axis=0).round(3)
    print(mix_pct)
else:
    print("\nNo Simpson's Paradox detected — per-type results agree with aggregate.")


# ── Step 4: Confidence intervals (95%) ───────────────────────────────────────
# A mean alone is not enough — we need to know how certain we are about it.
# 95% CI: mean +/- t*(std / sqrt(n))
# We use the t-distribution (not z) because group sizes vary and may be small.
# If two CIs don't overlap, the difference is likely real.
print("\n" + "=" * 60)
print("STEP 4: 95% Confidence intervals per scenario_type x model_version")
print("=" * 60)

def confidence_interval_95(series):
    n    = series.count()
    mean = series.mean()
    se   = series.std() / np.sqrt(n)            # standard error
    # t-critical value for 95% CI with n-1 degrees of freedom
    tc   = t_dist.ppf(0.975, df=n - 1)
    return pd.Series({
        'mean':  mean,
        'ci_lower': mean - tc * se,
        'ci_upper': mean + tc * se,
        'n':     n,
    })

ci_table = (
    df.groupby(['scenario_type', 'model_version'])['realism_score']
    .apply(confidence_interval_95)
    .unstack(level='model_version')   # columns: (stat, v1) / (stat, v2)
)
print(ci_table.round(4))


# ── Step 5: Flag scenario types with sample sizes too small ──────────────────
# Statistical tests lose reliability below ~30 samples (CLT threshold).
# Flag any group so the team knows not to act on those results.
print("\n" + "=" * 60)
print(f"STEP 5: Sample size check (flag if n < {SMALL_SAMPLE_THRESHOLD})")
print("=" * 60)

counts = df.groupby(['scenario_type', 'model_version'])['realism_score'].count().unstack()
print(counts)

small_flags = []
for stype in counts.index:
    for version in counts.columns:
        n = counts.loc[stype, version]
        if n < SMALL_SAMPLE_THRESHOLD:
            small_flags.append((stype, version, n))

if small_flags:
    print("\nFLAGGED (too small to trust):")
    for stype, version, n in small_flags:
        print(f"  scenario_type={stype}, model_version={version}, n={n}")
else:
    print(f"\nAll groups have n >= {SMALL_SAMPLE_THRESHOLD}. Sample sizes are sufficient.")


if __name__ == '__main__':
    pass