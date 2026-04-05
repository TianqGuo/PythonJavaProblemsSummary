import pandas as pd
import numpy as np


class PandasTest:
    def __init__(self):
        self.df = pd.DataFrame({
            'scenario_id': range(1000),
            'scenario_type': np.random.choice(['highway', 'urban', 'parking'], 1000),
            'realism_score': np.random.normal(0.82, 0.08, 1000),
            'model_version': np.random.choice(['v1', 'v2'], 1000),
            'run_date': pd.date_range('2026-01-01', periods=1000, freq='h')
        })

        # A second DataFrame used for merge/join test (test_case_5).
        # It contains extra metadata keyed on scenario_id.
        self.df2 = pd.DataFrame({
            'scenario_id': range(500),          # only 500 rows — intentional gap to show LEFT join
            'weather': np.random.choice(['sunny', 'rainy', 'foggy'], 500),
        })

        # Introduce some NaN values so missing-data operations have something to act on.
        nan_indices = np.random.choice(self.df.index, size=50, replace=False)
        self.df.loc[nan_indices, 'realism_score'] = np.nan

    # ── Test 1: Filtering ────────────────────────────────────────────────────
    # df[<boolean mask>] keeps only the rows where the condition is True.
    # Here we keep rows whose scenario_type column equals 'highway'.
    def test_case_1(self):
        cur = self.df[self.df['scenario_type'] == 'highway']
        print(f"Rows with scenario_type='highway': {len(cur)}")
        print(cur.head())

    # ── Test 2: Groupby + agg ────────────────────────────────────────────────
    # groupby() splits the DataFrame into groups by the given column.
    # agg() then applies multiple aggregation functions at once to the
    # selected column, returning one row per group.
    def test_case_2(self):
        cur = self.df.groupby('scenario_type')['realism_score'].agg(['mean', 'std', 'count'])
        print("Mean / std / count of realism_score per scenario_type:")
        print(cur)

    # ── Test 3: Comparing groups (groupby + unstack) ─────────────────────────
    # Grouping by two keys creates a MultiIndex Series.
    # unstack() pivots the inner index level (model_version) into columns,
    # giving a table where rows = scenario_type and columns = model_version.
    def test_case_3(self):
        cur = self.df.groupby(['scenario_type', 'model_version'])['realism_score'].mean().unstack()
        print("Mean realism_score by scenario_type (rows) × model_version (cols):")
        print(cur)

    # ── Test 4: Missing data ─────────────────────────────────────────────────
    # isnull().sum() counts NaN values per column — quick data-quality check.
    # dropna() returns a copy with every row that has at least one NaN removed.
    # ffill() (forward-fill) replaces each NaN with the last valid value above it.
    def test_case_4(self):
        print("NaN counts per column:")
        print(self.df.isnull().sum())

        dropped = self.df.dropna()
        print(f"\nRows after dropna: {len(dropped)}  (original: {len(self.df)})")

        filled = self.df.ffill()
        print(f"NaN counts after ffill:\n{filled.isnull().sum()}")

    # ── Test 5: Merge / join ─────────────────────────────────────────────────
    # pd.merge() works like a SQL JOIN.
    # how='left' keeps ALL rows from df (left) and matches rows from df2 where
    # scenario_id aligns; unmatched right-side values become NaN.
    def test_case_5(self):
        merged = pd.merge(self.df, self.df2, on='scenario_id', how='left')
        print(f"Merged shape: {merged.shape}  (left had {len(self.df)} rows)")
        print(f"NaN in 'weather' (scenario_id >= 500): {merged['weather'].isnull().sum()}")
        print(merged.head())

    # ── Test 6: Value counts ─────────────────────────────────────────────────
    # value_counts() tallies occurrences of each unique value.
    # normalize=True converts raw counts to proportions (sum == 1.0),
    # useful for quickly seeing the distribution of a categorical column.
    def test_case_6(self):
        proportions = self.df['scenario_type'].value_counts(normalize=True)
        print("Proportion of each scenario_type:")
        print(proportions)

    # ── Test 7: Percentiles ───────────────────────────────────────────────────
    # quantile() returns the value below which a given fraction of data falls.
    # Passing a list gives multiple percentiles in one call — handy for
    # understanding the spread and tails of a distribution.
    def test_case_7(self):
        percentiles = self.df['realism_score'].quantile([0.05, 0.25, 0.5, 0.75, 0.95])
        print("Percentiles of realism_score (5th / 25th / 50th / 75th / 95th):")
        print(percentiles)

    # ── Test 8: Boolean masks (compound filter) ───────────────────────────────
    # Multiple conditions are combined with & (AND) or | (OR).
    # Each condition must be wrapped in parentheses because of Python's
    # operator precedence rules.  This returns rows that satisfy BOTH conditions.
    def test_case_8(self):
        mask = (self.df['model_version'] == 'v2') & (self.df['realism_score'] < 0.7)
        cur = self.df[mask]
        print(f"v2 rows with realism_score < 0.7: {len(cur)}")
        print(cur.head())

    # ── Test 9: Apply (per-group custom function) ─────────────────────────────
    # apply() lets you pass any callable to a GroupBy object.
    # Here we compute the 5th-percentile realism_score within each
    # scenario_type group, which is more flexible than the built-in agg names.
    def test_case_9(self):
        p5_per_group = self.df.groupby('scenario_type')['realism_score'].apply(
            lambda x: x.quantile(0.05)
        )
        print("5th-percentile realism_score per scenario_type:")
        print(p5_per_group)


if __name__ == '__main__':
    t = PandasTest()
    for i in range(1, 10):
        print(f"\n{'='*50}")
        print(f"Test case {i}")
        print('='*50)
        getattr(t, f'test_case_{i}')()




