
-- ###  TIME-based Continues Aggregate Sample Usage
-- convert time_long_test table to hypertable
-- Timestamp as Long test model
SELECT * FROM public.time_long_test ORDER BY time DESC;

SELECT * FROM public.aggregated_long_time_5mins ORDER BY bucket DESC;

DROP MATERIALIZED VIEW aggregated_long_time_5mins CASCADE;


SELECT create_hypertable('time_long_test', by_range('time', 10), migrate_data => true);

CREATE FUNCTION current_epoch_milli() RETURNS BIGINT
    LANGUAGE SQL STABLE AS
    $$ SELECT (EXTRACT(EPOCH FROM now()) * 1000)::BIGINT $$;

SELECT set_integer_now_func('time_long_test', 'current_epoch_milli');

CREATE MATERIALIZED VIEW aggregated_long_time_5mins
WITH (timescaledb.continuous) AS
SELECT
    time_bucket('300', time) AS bucket,
    group_name,
    avg(test) AS avg_test,
    arr_agg(sample) AS agg_sample,
    arr_agg(lastseentime) AS agg_lastseentimes
FROM time_long_test
GROUP BY bucket, group_name
    WITH NO DATA;

SELECT add_continuous_aggregate_policy('aggregated_long_time_5mins',
                                       start_offset => 3600000,
                                       end_offset => 300000,
                                       schedule_interval => INTERVAL '5 mins');
