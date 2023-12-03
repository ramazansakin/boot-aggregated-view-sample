
-- convert dummy table to hypertable
SELECT create_hypertable('dummy', 'timestamp', migrate_data => true);

-- Select all hypertables to see if it s created properly or not
SELECT * FROM timescaledb_information.hypertables;

-- Team-based aggregation data per 5 mins
CREATE MATERIALIZED VIEW aggregated_dummies_5mins
WITH (timescaledb.continuous) AS
SELECT
    time_bucket(INTERVAL '5 minutes', time) AS bucket,
    team,
    max(testone) AS sum_testone,
    min(testtwo) AS min_testtwo,
    avg(testtwo) AS min_testtwo
FROM dummy
GROUP BY bucket, team;


DROP MATERIALIZED VIEW IF EXISTS conditions_5min_agg;

SELECT create_hypertable(tablename, timecolumn, migrate_data => true);

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



-- CREATE CONTINUOUS AGGREGATE conditions_5min_agg
-- ON conditions
-- WITH POLICY (
--   start_offset = INTERVAL '15 minutes',
--   every = INTERVAL '5 minutes',
--   end_offset = INTERVAL '20 minutes',
--   refresh_immediately = ON
-- )
-- AS (
--   SELECT
--     time_bucket('5 minutes', time) AS time_bucket,
--     location,
--     device,
--     avg(temperature) AS average_temperature,
--     avg(humidity) AS average_humidity,
--     sum(testone) AS sum_testone,
--     sum(testtwo) AS sum_testtwo
--   FROM conditions
--   GROUP BY time_bucket('5 minutes'), location, device
-- );

