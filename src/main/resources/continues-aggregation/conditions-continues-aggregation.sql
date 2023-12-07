
-- Shows PostgreSQL version details
SELECT version();

SELECT * FROM pg_extension WHERE extname = 'timescaledb';

-- Show continues aggregates
SELECT * FROM timescaledb_information.continuous_aggregates;

-- Show continues aggregates' policies
SELECT * FROM timescaledb_experimental.policies;


-- Select all hypertables to see if it s created properly or not
SELECT * FROM timescaledb_information.hypertables;

-- convert dummy table to hypertable
SELECT create_hypertable('dummy', 'timestamp', migrate_data => true);

CREATE MATERIALIZED VIEW aggregated_dummies_5mins
WITH (timescaledb.continuous) AS
SELECT
    time_bucket(INTERVAL '5 minutes', timestamp) AS bucket,
    team,
    max(testone) AS sum_testone,
    min(testtwo) AS min_testtwo,
    avg(testthree) AS avg_testthree
FROM dummy
GROUP BY bucket, team
    WITH NO DATA;

CALL refresh_continuous_aggregate('aggregated_dummies_5mins', NULL, localtimestamp - INTERVAL '5 mins');

SELECT add_continuous_aggregate_policy(
               'aggregated_dummies_5mins',
               start_offset => INTERVAL '20 mins',
               end_offset => INTERVAL '5 mins',
               schedule_interval => INTERVAL '5 mins');


--  Drop materialized view
DROP MATERIALIZED VIEW IF EXISTS conditions_5min_agg;


-- Location-based aggregation data per 5 mins
CREATE MATERIALIZED VIEW conditions_5min_agg
WITH (timescaledb.continuous) AS
SELECT
    time_bucket(INTERVAL '5 minutes', time) AS bucket,
    device,
    max(temperature) AS max_temperature,
    avg(humidity) AS avg_humidity,
    sum(testone) AS sum_testone,
    min(testtwo) AS min_testtwo
FROM conditions
GROUP BY device, bucket;


SELECT add_continuous_aggregate_policy('conditions_5min_agg',
                                       end_offset => INTERVAL '20 minutes',
                                       schedule_interval => INTERVAL '5 minutes');




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


SELECT add_continuous_aggregate_policy(
               'aggregated_dummies_5mins',
               start_offset => INTERVAL '30 mins',
               end_offset => INTERVAL '10 mins',
               schedule_interval => INTERVAL '5 mins');



CREATE MATERIALIZED VIEW related_dummy_aggregate_5min AS
WITH (timescaledb.continuous)
SELECT
    time_bucket('5 minute', dummy.time) AS timestamp_bucket,
    dummy_id,
    SUM(othertestone) AS total_othertestone,
    AVG(othertesttwo) AS avg_othertesttwo
FROM related_dummy
GROUP BY timestamp_bucket, dummy_id;

SELECT add_continuous_aggregate_policy(
               'related_dummy_aggregate_5min',
               start_offset => INTERVAL '30 mins',
               end_offset => INTERVAL '10 mins',
               schedule_interval => INTERVAL '5 mins');
