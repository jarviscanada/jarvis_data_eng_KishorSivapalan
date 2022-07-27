# Linux Cluster Monitoring Agent

# Introduction
The Jarvis Linux Cluster Administration

# Quick Start
The following is a sequence of steps to set up the program on your device. If anything is unclear, please scroll down to the [Scripts](#Scripts) section for explanations and examples.

 1. Initialize a database using the `psql_docker.sh` script:
 
	`./scripts/psql_docker.sh create [db_username] [db_password]`

 2. Start the database:
 
	`./scripts/psql_docker.sh start`

 3. Initialize the tables in the database using the `ddl.sql` script:

	 `psql -h localhost -U postgres -d host_agent -f sql/ddl.sql`
 
 4. Run the `host_info.sh` script to load in the hardware information of the current node:
 
	 `./scripts/host_info.sh localhost 5432 host_agent postgres password`

 5. Run the `host_usage.sh` script to load in the hardware usage of the current node:

	`./scripts/host_usage.sh localhost 5432 host_agent postgres password`

 6. Automate the `host_usage.sh` script to refresh every minute by using `crontab`:

	```shell
	#Enter the script directory
	cd scripts/
	
	#Copy the output of the pwd command (i.e. the absolute path of the script directory)
	pwd

	#Edit the crontab jobs
	crontab -e

	#Add this line to the crontab file; replace "[INSERT_PATH]" with the path that you copied from "pwd"
	* * * * * bash [INSERT_PATH]/host_usage.sh localhost 5432 host_agent postgres password > /tmp/host_usage.log

	#List the crontab jobs
	crontab -l
	```

	```sql
	#Login to database and check if the information is being updated every minute
	#Note: The password to the database is "password"
	psql -h localhost -U postgres -W
	SELECT * FROM host_usage;
	```
	

7. To view the tables, login to the database and do the following commands:
	```sql
	#To view information from host_info table
	SELECT * FROM host_info;

	#To view information from host_usage table
	SELECT * FROM host_usage;
	```

# Implementation
This project was implemented using 

## Architecture
TBD
## Scripts
Here is a general overview of the scripts created in this project:

### `psql_docker.sh`

This script allows a user to create, start, or stop a PostgreSQL instance in a docker container on the system.

Usage: `./scripts/psql_docker.sh start|stop|create [db_username] [db_password]`

> `db_username` and `db_password` refer to the username and the password of the database. Only one option between `start`, `stop`, and `create` can be used at a time.

E.g. 1 - Creating a psql_docker container:  `./scripts/psql_docker.sh create jrvs-psql password`

E.g. 2 - Starting the psql_docker container:  `./scripts/psql_docker.sh start`

E.g. 3 - Stopping the psql_docker container:  `./scripts/psql_docker.sh stop`

### ` host_info.sh`

This script is used to gather the hardware information of the node and insert that information into a local database. 

**IMPORTANT:** This script must be run before `host_usage.sh` since the usage script relies on the `host_id` (in the database) created by this script.

Usage: `./scripts/host_info.sh psql_host psql_port psql_db_name psql_username psql_password`

> `psql_host`, `psql_port `, `psql_db_name `, `psql_username `, `psql_password` refer to the PostgresSQL hostname, port, database name, username, and password.

E.g. - `./scripts/host_info.sh localhost 5432 host_agent postgres password`

### ` host_usage.sh`

This script is used to gather the hardware usage information of the node and insert that information into a local database. 

Usage: `./scripts/host_info.sh psql_host psql_port psql_db_name psql_username psql_password`

> `psql_host`, `psql_port `, `psql_db_name `, `psql_username `, `psql_password` refer to the PostgresSQL hostname, port, database name, username, and password.

E.g. - 	`./scripts/host_usage.sh localhost 5432 host_agent postgres password`

### ` crontab`


### `sql_queries.sql`

This script contains queries to solve the following business cases:

 1. Group hosts by CPU number and sort by their memory size in descending order
 2. Average used memory in percentage over 5 mins interval for each host
 3. Detecting host failure

Usage: Copy and paste the relevant query into the database console. **Note:** Remember to copy the `round5` function since business cases 2 and 3 use it in their queries.

## Database Modeling
This project requires two tables to record data: one for hardware information (i.e. `host_info`) and another for hardware resource usage (i.e. `host_usage`).

The schema for `host_info`:
|Variable|Type|Description|
|--|--|--|
|id|SERIAL|The primary key; it is a unique identifier for each node|
|hostname|VARCHAR|The unique full name of the node|
|cpu_number|INT|The number of CPUs in the node|
|cpu_architecture|VARCHAR|The CPU architecture in the node|
|cpu_model|VARCHAR|The CPU model in the node|
|cpu_mhz|REAL|The CPU speed on the node in MHz|
|L2_cache|INT|The size of the L2 cache on the node in KB|
|total_mem|INT|The total memory on the node in KB|
|timestamp|TIMESTAMP|The timestamp of the node at instantiation|

The schema for `host_usage`:
|Variable|Type|Description|
|--|--|--|
|timestamp|TIMESTAMP|The timestamp of the node at instantiation|
|host_id|VARCHAR|The foreign key; it is a reference to the `id` in `host_info`|
|memory_free|INT|The amount of free memory on the node in MB|
|cpu_idle|INT|The percentage of the CPU in idle mode|
|cpu_kernel|INT|The percentage of the CPU in kernel mode|
|disk_io|INT|The number of disk I/O|
|disk_available|INT|The available disk space from the root directory in MB|

# Test

# Deployment

# Improvements

 - Documentation (README.md) can be improved
 - SQL queries can be simplified
 - Schema data types can be more specific
 - Can make a script to install all scripts rather than the user having to do so many things
