
DROP MATERIALIZED VIEW IF EXISTS conditions_5min_agg;

-- Location-based aggregation data per 5 mins
CREATE MATERIALIZED VIEW IF NOT EXISTS conditions_5min_agg
WITH (timescaledb.continuous) AS
SELECT
    time_bucket('5 minutes', time) AS bucket,
    device,
    ARRAY_AGG(DISTINCT location) AS related_locations,
    max(temperature) AS average_temperature,
    avg(humidity) AS average_humidity,
    sum(testone) AS sum_testone,
    min(testtwo) AS sum_testtwo
FROM conditions
GROUP BY bucket, device;


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