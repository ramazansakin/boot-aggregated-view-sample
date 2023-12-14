
-- just Device-based aggregation data per 1 hour
CREATE MATERIALIZED VIEW conditions_5min_agg
WITH (timescaledb.continuous) AS
SELECT
    time_bucket(INTERVAL '1 hour', time) AS bucket,
    device,
    max(temperature) AS max_temperature,
    avg(humidity) AS avg_humidity,
    sum(testone) AS sum_testone,
    min(testtwo) AS min_testtwo
FROM conditions
GROUP BY device, bucket;


SELECT add_continuous_aggregate_policy('conditions_5min_agg',
                                       start_offset => INTERVAL '30 days',
                                       end_offset => INTERVAL '5 days',
                                       schedule_interval => INTERVAL '1 hour');

