-- Business Case 1: Group hosts by hardware info
SELECT
    cpu_number,
    id AS host_id,
    total_mem
FROM
    PUBLIC.host_info
GROUP BY
    cpu_number, id
ORDER BY
    total_mem DESC;

-- Business Case 2: Average memory usage
CREATE FUNCTION round5(ts timestamp) RETURNS timestamp AS
$$
BEGIN
    RETURN date_trunc('hour', ts) + date_part('minute', ts):: int / 5 * interval '5 min';
END;
$$
    LANGUAGE PLPGSQL;

-- Business Case 3: Detect host failure