------- Business Case 1: Group hosts by hardware info -------
SELECT
    cpu_number,
    id AS host_id,
    total_mem
FROM
    host_info
GROUP BY
    cpu_number, id
ORDER BY
    total_mem DESC;

------- Function for Business Cases 2 & 3 -------
CREATE FUNCTION round5(ts timestamp) RETURNS timestamp AS
    $$
BEGIN
    RETURN date_trunc('hour', ts) + date_part('minute', ts):: int / 5 * interval '5 min';
END;
$$
    LANGUAGE PLPGSQL;

------- Business Case 2: Average memory usage -------
SELECT
    host_id,
    hostname,
    round5(host_usage.timestamp) as rounded_time,
    round((((AVG((total_mem/1000) - memory_free))/(total_mem/1000))*100), 2) as avg_mem_usage
FROM host_usage
    JOIN host_info on host_usage.host_id = host_info.id
GROUP BY
    host_id,
    hostname,
    round_time,
    total_mem,
    memory_free
ORDER BY
    round_time ASC;

------- Business Case 3: Detect host failure -------
SELECT
    host_id,
    round5(host_usage.timestamp) AS rounded_time,
    COUNT(*) as num_data_points
FROM
    host_usage
GROUP BY
    rounded_time,
    host_id
HAVING
    COUNT(*) < 3;