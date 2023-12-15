
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

-- ## 5 mins aggregation buckets
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



-- ## 1 hour aggregation buckets
CREATE MATERIALIZED VIEW aggregated_long_time_hourly
WITH (timescaledb.continuous) AS
SELECT
    time_bucket('3600000', bucket) AS bucket_h,
    group_name,
    avg(test) AS test,
    string_agg(sample::text, ',') AS sample,
    string_agg(lastseentime::text, ',') AS lastseentime
FROM aggregated_long_time_5mins
GROUP BY group_name, bucket_h
    WITH NO DATA;

SELECT add_continuous_aggregate_policy('aggregated_long_time_hourly',
                                       start_offset => 3600000000,
                                       end_offset => 3600000,
                                       schedule_interval => INTERVAL '1 hour');


-- ## 1 day aggregation buckets
CREATE MATERIALIZED VIEW aggregated_long_time_daily
WITH (timescaledb.continuous) AS
SELECT
    time_bucket('86400000', bucket) AS bucket_d,
    group_name,
    avg(test) AS test,
    string_agg(sample::text, ',') AS sample,
    string_agg(lastseentime::text, ',') AS lastseentime
FROM aggregated_long_time_5mins
GROUP BY group_name, bucket_d
    WITH NO DATA;

SELECT add_continuous_aggregate_policy('aggregated_long_time_daily',
                                       start_offset => 3600000000,
                                       end_offset => 3600000,
                                       schedule_interval => INTERVAL '1 hour');

