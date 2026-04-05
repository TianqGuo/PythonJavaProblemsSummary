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


    # ── Test 10: File I/O ────────────────────────────────────────────────────
    # Demonstrates saving and loading data in CSV, JSON, and simulated log format.
    #
    # CSV:  most common format for tabular data — human-readable, widely supported.
    # JSON: common for API responses and nested data.
    # Log:  plain text with a fixed structure per line — needs manual parsing.
    #
    # index=False when saving CSV prevents pandas from writing the auto-generated
    # row index (0,1,2...) as an extra column in the file.
    def test_case_10(self):
        import os
        import re

        # ── CSV ──────────────────────────────────────────────────────────────
        csv_path = 'sample_output.csv'

        # Save to CSV
        self.df.to_csv(csv_path, index=False)

        # Load from CSV
        # dtype tells pandas to read scenario_id as int (it infers types but
        # being explicit avoids surprises on edge cases like leading zeros)
        df_csv = pd.read_csv(csv_path, dtype={'scenario_id': int})
        print("CSV — shape:", df_csv.shape)
        print(df_csv.head(3))

        # Useful read_csv options:
        #   usecols=['scenario_id', 'realism_score']  → load only specific columns
        #   nrows=100                                  → load only first 100 rows
        #   parse_dates=['run_date']                   → auto-parse date columns
        #   skiprows=1                                 → skip the first row

        # ── JSON ─────────────────────────────────────────────────────────────
        json_path = 'sample_output.json'

        # Save to JSON — orient='records' gives [{col:val, ...}, ...] format,
        # which is the most readable and common for API-style data
        self.df.to_json(json_path, orient='records', date_format='iso')

        # Load from JSON
        df_json = pd.read_json(json_path, orient='records')
        print("\nJSON — shape:", df_json.shape)
        print(df_json.head(3))

        # ── Log file (plain text) ─────────────────────────────────────────────
        # Log files are not tabular — each line is a string with a fixed pattern.
        # Strategy: write sample log lines, then parse each line with regex or
        # str.split() to extract fields into a DataFrame.
        log_path = 'sample.log'

        # Write a sample log file mimicking a real application log
        with open(log_path, 'w') as f:
            for _, row in self.df.head(10).iterrows():
                f.write(
                    f"{row['run_date']} | scenario_id={row['scenario_id']} | "
                    f"type={row['scenario_type']} | score={row['realism_score']:.4f} | "
                    f"model={row['model_version']}\n"
                )

        # Parse the log file — each line looks like:
        # 2026-01-01 00:00:00 | scenario_id=0 | type=highway | score=0.7887 | model=v2
        records = []
        pattern = re.compile(
            r'(?P<run_date>.+?) \| scenario_id=(?P<scenario_id>\d+) \| '
            r'type=(?P<scenario_type>\w+) \| score=(?P<realism_score>[\d.]+) \| '
            r'model=(?P<model_version>\w+)'
        )
        with open(log_path) as f:
            for line in f:
                match = pattern.match(line.strip())
                if match:
                    records.append(match.groupdict())

        df_log = pd.DataFrame(records)
        # Columns parsed from text are all strings — cast numeric ones back
        df_log['scenario_id']   = df_log['scenario_id'].astype(int)
        df_log['realism_score'] = df_log['realism_score'].astype(float)
        df_log['run_date']      = pd.to_datetime(df_log['run_date'])

        print("\nLog — shape:", df_log.shape)
        print(df_log)
        print(df_log.dtypes)

        # Clean up generated files
        for path in [csv_path, json_path, log_path]:
            os.remove(path)


    # ── Test 11: Statistical comparison (scipy) ──────────────────────────────
    # Three tests cover the most common "is this distribution / mean / relationship real?" questions.
    #
    # KS test  — are two distributions the same shape?
    #   Use when: comparing simulated vs real score distributions (no assumption on shape)
    #   p < 0.05 → distributions are significantly different
    #
    # T-test   — are two group means significantly different?
    #   Use when: comparing mean realism score between model v1 vs v2
    #   Assumes roughly normal distributions (fine here given CLT at n>30)
    #   p < 0.05 → means are significantly different
    #
    # Pearson r — is there a linear correlation between two numeric variables?
    #   Use when: checking if a feature predicts realism score
    #   r close to +1/-1 → strong linear relationship; r near 0 → no linear relationship
    #   p < 0.05 → correlation is statistically significant
    def test_case_11(self):
        from scipy.stats import ks_2samp, ttest_ind, pearsonr

        # Drop NaNs first — all three scipy functions require clean arrays
        clean = self.df.dropna(subset=['realism_score'])

        v1_scores = clean[clean['model_version'] == 'v1']['realism_score']
        v2_scores = clean[clean['model_version'] == 'v2']['realism_score']

        # KS test: are v1 and v2 realism score distributions the same shape?
        ks_stat, ks_pval = ks_2samp(v1_scores, v2_scores)
        print(f"KS test  (v1 vs v2 distribution): stat={ks_stat:.4f}, p={ks_pval:.4f}")
        print(f"  → {'Distributions differ significantly' if ks_pval < 0.05 else 'No significant difference in distribution'}")

        # T-test: is the mean realism score significantly different between v1 and v2?
        t_stat, t_pval = ttest_ind(v1_scores, v2_scores)
        print(f"\nT-test   (v1 vs v2 mean):          stat={t_stat:.4f}, p={t_pval:.4f}")
        print(f"  → {'Means differ significantly' if t_pval < 0.05 else 'No significant difference in means'}")

        # Pearson r: does scenario_id (order of run) correlate with realism score?
        # In real usage this would be a meaningful feature like speed or complexity
        r, r_pval = pearsonr(clean['scenario_id'], clean['realism_score'])
        print(f"\nPearson r (scenario_id vs score):  r={r:.4f}, p={r_pval:.4f}")
        print(f"  → {'Significant linear correlation' if r_pval < 0.05 else 'No significant linear correlation'}")

    # ── Test 12: Time series ──────────────────────────────────────────────────
    # The run_date column has hourly timestamps — two common patterns:
    #
    # dt accessor  — extract parts of a timestamp (hour, day, weekday) as new columns.
    #   Useful for feature engineering: "does realism degrade at certain hours?"
    #
    # resample()   — group rows into fixed time buckets (like groupby but for time).
    #   '3D' = 3-day buckets. Common intervals: 'h' (hour), 'D' (day), 'W' (week), 'ME' (month end).
    #   Must set the datetime column as the index first, or pass on='run_date'.
    def test_case_12(self):
        # dt accessor: extract hour and day-of-week from the timestamp
        df_ts = self.df.copy()
        df_ts['hour']       = df_ts['run_date'].dt.hour        # 0–23
        df_ts['dayofweek']  = df_ts['run_date'].dt.dayofweek   # 0=Monday … 6=Sunday
        df_ts['date']       = df_ts['run_date'].dt.date        # calendar date only

        print("dt accessor — new time-derived columns:")
        print(df_ts[['run_date', 'hour', 'dayofweek', 'date']].head(5))

        # Average realism score by hour — quick check for time-of-day drift
        print("\nMean realism_score by hour (first 6 hours):")
        print(df_ts.groupby('hour')['realism_score'].mean().head(6))

        # resample: bucket into 3-day windows and compute mean realism score
        # set_index moves run_date into the index so resample can use it
        df_resampled = (
            df_ts.set_index('run_date')['realism_score']
            .resample('3D')
            .mean()
        )
        print("\nMean realism_score resampled to 3-day buckets (first 5):")
        print(df_resampled.head(5))

    # ── Test 13: Anomaly / outlier detection ─────────────────────────────────
    # Two standard methods for flagging outliers in a numeric column:
    #
    # Z-score method — how many standard deviations from the mean?
    #   Flag rows where |z| > 3 (covers 99.7% of normal distribution).
    #   Best when the data is roughly normally distributed.
    #
    # IQR method — based on the spread of the middle 50% of data.
    #   Flag rows below Q1 - 1.5×IQR or above Q3 + 1.5×IQR.
    #   More robust than z-score when the data has heavy tails or skew.
    #
    # For this role: outlier detection finds simulation artifacts —
    # scores that are suspiciously low/high may indicate a physics glitch
    # or model hallucination rather than a genuine driving scenario.
    def test_case_13(self):
        clean = self.df.dropna(subset=['realism_score']).copy()

        # Z-score method
        mean  = clean['realism_score'].mean()
        std   = clean['realism_score'].std()
        clean['z_score']      = (clean['realism_score'] - mean) / std
        clean['zscore_flag']  = clean['z_score'].abs() > 3

        z_outliers = clean[clean['zscore_flag']]
        print(f"Z-score outliers (|z| > 3): {len(z_outliers)}")
        print(z_outliers[['scenario_id', 'realism_score', 'z_score']].head())

        # IQR method
        Q1  = clean['realism_score'].quantile(0.25)
        Q3  = clean['realism_score'].quantile(0.75)
        IQR = Q3 - Q1
        lower = Q1 - 1.5 * IQR
        upper = Q3 + 1.5 * IQR
        clean['iqr_flag'] = (clean['realism_score'] < lower) | (clean['realism_score'] > upper)

        iqr_outliers = clean[clean['iqr_flag']]
        print(f"\nIQR outliers (outside [{lower:.3f}, {upper:.3f}]): {len(iqr_outliers)}")
        print(iqr_outliers[['scenario_id', 'realism_score']].head())

        # How many outliers are flagged by both methods?
        both = clean[clean['zscore_flag'] & clean['iqr_flag']]
        print(f"\nFlagged by both methods: {len(both)}")


if __name__ == '__main__':
    t = PandasTest()
    for i in range(1, 14):
        print(f"\n{'='*50}")
        print(f"Test case {i}")
        print('='*50)
        getattr(t, f'test_case_{i}')()




