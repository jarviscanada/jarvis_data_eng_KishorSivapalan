#! /bin/sh

psql_host=$1
psql_port=$2
db_name=$3
psql_user=$4
psql_password=$5

if [ $# -ne  5 ]; then
  echo 'To use: ./scripts/host_usage.sh psql_host psql_port db_name psql_user psql_password'
  exit 1
fi

#Save machine statistics in MB and current machine hostname to variables
vmstat_mb=$(vmstat --unit M)
hostname=$(hostname -f)
mem_out=`cat /proc/meminfo`

#host hardware usage information
memory_free=$(echo "${vmstat_mb}"  | awk '{print $4}' | xargs | awk '{print $NF}')
cpu_idle=$(echo "${vmstat_mb}"  | awk '{print $15}' | xargs | awk '{print $NF}')
cpu_kernel=$(echo "${vmstat_mb}"  | awk '{print $14}' | xargs | awk '{print $NF}')
disk_io=$(vmstat -d  | awk '{print $10}' | xargs | awk '{print $NF}')
disk_available=$(df -BM /  | awk '{print $4}' | xargs | cut -d"M" -f 1 | awk '{print $NF}')

#Current time in `2019-11-26 14:40:19` UTC format
timestamp=$(date '+%Y-%m-%d %T')

#Get the matching id in host_info table first
host_id="(SELECT id FROM host_info WHERE hostname='$hostname')";

#PSQL command: Inserts server usage data into host_usage table
insert_stmt="INSERT INTO host_usage(\"timestamp\", host_id, memory_free, cpu_idle, cpu_kernel, disk_io, disk_available)
VALUES('$timestamp', $host_id, $memory_free, $cpu_idle, $cpu_kernel, $disk_io, $disk_available);"

#set up env var for pql cmd
export PGPASSWORD=$psql_password

#Insert data into a database
psql -h $psql_host -p $psql_port -d $db_name -U $psql_user -c "$insert_stmt"
exit $?