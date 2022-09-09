-- \c host_agent;
CREATE TABLE IF NOT EXISTS PUBLIC.host_info
(
    id                  SERIAL NOT NULL,
    hostname            VARCHAR NOT NULL,
    cpu_number          INT NOT NULL,
    cpu_architecture    VARCHAR NOT NULL,
    cpu_model           VARCHAR NOT NULL,
    cpu_mhz             REAL NOT NULL,
    L2_cache            INT NOT NULL,
    total_mem           INT NOT NULL,
    "timestamp"         TIMESTAMP NOT NULL,
    PRIMARY KEY (id),   -- primary key constraint
    UNIQUE (hostname)   -- unique hostname constraint
);

CREATE TABLE IF NOT EXISTS PUBLIC.host_usage
(
    "timestamp"     TIMESTAMP NOT NULL,
    host_id         SERIAL NOT NULL,
    memory_free     INT NOT NULL,
    cpu_idle        INT NOT NULL,
    cpu_kernel      INT NOT NULL,
    disk_io         INT NOT NULL,
    disk_available  INT NOT NULL,
    -- foreign key constraint
    FOREIGN KEY (host_id) REFERENCES PUBLIC.host_info(id)
);