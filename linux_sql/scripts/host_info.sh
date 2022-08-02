#! /bin/sh

psql_host=$1
psql_port=$2
db_name=$3
psql_user=$4
psql_password=$5

if [ $# -ne  5 ]; then
  echo 'To use: ./scripts/host_info.sh psql_host psql_port db_name psql_user psql_password'
  exit 1
fi

#Save machine statistics in MB and current machine hostname to variables
hostname=$(hostname -f)
lscpu_out=`lscpu`
mem_out=`cat /proc/meminfo`

#host hardware information
cpu_number=$(echo "$lscpu_out"  | egrep "^CPU\(s\):" | awk '{print $2}' | xargs)
cpu_architecture=$(echo "$lscpu_out"  | egrep "^Architecture\:" | awk '{print $2}' | xargs)
cpu_model=$(echo "$lscpu_out"  | egrep "^Model name\:" | awk '{print $3,$4,$5,$6,$7}' | xargs)
cpu_mhz=$(echo "$lscpu_out"  | egrep "^CPU MHz\:" | awk '{print $3}' | xargs)
L2_cache=$(echo "$lscpu_out"  | egrep "^L2 cache:" | awk '{print $3}' | xargs | cut -d'K' -f 1)
total_mem=$(echo "$mem_out"  | egrep "^MemTotal\:" | awk '{print $2}' | xargs)

#Current time in `2019-11-26 14:40:19` UTC format
timestamp=$(date '+%Y-%m-%d %T')

#PSQL command: Inserts server usage data into host_info table
insert_stmt="INSERT INTO host_info (hostname, cpu_number, cpu_architecture, cpu_model, cpu_mhz, L2_cache, total_mem, \"timestamp\")
VALUES ('$hostname', $cpu_number, '$cpu_architecture', '$cpu_model', $cpu_mhz, $L2_cache, $total_mem, '$timestamp');"

#set up env var for pql cmd
export PGPASSWORD=$psql_password

#Insert data into a database
psql -h $psql_host -p $psql_port -d $db_name -U $psql_user -c "$insert_stmt"
exit $?