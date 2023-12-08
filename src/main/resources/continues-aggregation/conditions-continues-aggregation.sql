SELECT * FROM public.dummy;

SELECT * FROM public.related_dummy;


SELECT * FROM public.aggregated_dummies_5mins order by bucket;

SELECT * FROM public.related_dummy_aggregate_5min order by bucket;


DROP MATERIALIZED VIEW aggregated_dummies_5mins CASCADE;

DROP MATERIALIZED VIEW related_dummy_aggregate_5min CASCADE;


SELECT version();
SELECT * FROM pg_extension WHERE extname = 'timescaledb';

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
               schedule_interval => INTERVAL '5 mins');


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


