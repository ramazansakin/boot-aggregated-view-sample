-- Select
SELECT * FROM public.dummy;
SELECT * FROM public.related_dummy;

SELECT * FROM public.aggregated_dummies_5mins order by bucket;
SELECT * FROM public.related_dummy_aggregate_5min order by bucket;
SELECT * FROM public.aggregated_dummies_20mins order by s_bucket;
SELECT * FROM public.aggregated_dummies_hourly order by h_bucket;


DROP MATERIALIZED VIEW aggregated_dummies_5mins CASCADE;
DROP MATERIALIZED VIEW related_dummy_aggregate_5min CASCADE;
DROP MATERIALIZED VIEW aggregated_dummies_20mins CASCADE;
DROP MATERIALIZED VIEW aggregated_dummies_hourly CASCADE;


SELECT version();
SELECT * FROM pg_extension WHERE extname = 'timescaledb';

SELECT * FROM timescaledb_information.hypertables;

SELECT * FROM timescaledb_information.continuous_aggregates;

SELECT * FROM timescaledb_experimental.policies;

-- Manually call aggregation
CALL refresh_continuous_aggregate('aggregated_dummies_5mins', NULL, localtimestamp - INTERVAL '10 mins');

-- convert dummy table to hypertable
SELECT create_hypertable('dummy', 'time', migrate_data => true);

-- convert related dummy table to hypertable
SELECT create_hypertable('related_dummy', 'time', migrate_data => true);


-- Trail 3 Dummy Continues Aggregation View
CREATE MATERIALIZED VIEW aggregated_dummies_5mins
WITH (timescaledb.continuous) AS
SELECT
    time_bucket(INTERVAL '5 minutes', time) AS bucket,
    team,
    max(testone) AS sum_testone,
    min(testtwo) AS min_testtwo,
    avg(testthree) AS avg_testthree
FROM dummy
GROUP BY bucket, team
    WITH NO DATA;

-- Trail 3 Dummy Continues Aggregation View
CREATE MATERIALIZED VIEW aggregated_dummies_5mins
WITH (timescaledb.continuous) AS
SELECT
    time_bucket(INTERVAL '5 minutes', time) AS bucket,
    team,
    max(testone) AS sum_testone,
    min(testtwo) AS min_testtwo,
    avg(testthree) AS avg_testthree,
    arr_agg(stragg) AS agg_stragg
FROM dummy
GROUP BY bucket, team
    WITH NO DATA;

-- I m thinking about the set data retention period as 3 months
SELECT add_continuous_aggregate_policy(
               'aggregated_dummies_5mins',
               start_offset => INTERVAL '30 mins',
               end_offset => INTERVAL '10 mins',
               schedule_interval => INTERVAL '5 mins');


CREATE MATERIALIZED VIEW related_dummy_aggregate_5min
WITH (timescaledb.continuous) AS
SELECT
    time_bucket('5 minutes', time) AS bucket,
    team,
    SUM(othertestone) AS total_othertestone,
    AVG(othertesttwo) AS avg_othertesttwo
FROM related_dummy
GROUP BY bucket, team
    WITH NO DATA;

SELECT add_continuous_aggregate_policy(
               'related_dummy_aggregate_5min',
               start_offset => INTERVAL '30 mins',
               end_offset => INTERVAL '10 mins',
               schedule_interval => INTERVAL '5mins');


CREATE MATERIALIZED VIEW aggregated_dummies_20mins
WITH (timescaledb.continuous) AS
SELECT
    time_bucket('20 minutes', bucket) AS s_bucket,
    team,
    max(sum_testone) AS s_sum_testone,
    min(min_testtwo) AS s_min_testtwo,
    avg(avg_testthree) AS s_avg_testthree
FROM aggregated_dummies_5mins
GROUP BY s_bucket, team
    WITH NO DATA;


SELECT add_continuous_aggregate_policy(
               'aggregated_dummies_20mins',
               start_offset => INTERVAL '1 month',
               end_offset => INTERVAL '10 mins',
               schedule_interval => INTERVAL '20 mins');


CREATE MATERIALIZED VIEW aggregated_dummies_hourly
WITH (timescaledb.continuous) AS
SELECT
    time_bucket('1 hour', s_bucket) AS h_bucket,
    team,
    max(s_sum_testone) AS h_sum_testone,
    min(s_min_testtwo) AS h_min_testtwo,
    avg(s_avg_testthree) AS h_avg_testthree
FROM aggregated_dummies_20mins
GROUP BY h_bucket, team
    WITH NO DATA;


SELECT add_continuous_aggregate_policy(
               'aggregated_dummies_hourly',
               start_offset => INTERVAL '1 month',
               end_offset => INTERVAL '10 mins',
               schedule_interval => INTERVAL '1 hour');

