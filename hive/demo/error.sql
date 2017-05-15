-- noinspection SqlNoDataSourceInspectionForFile
set mapreduce.job.reduces = 100;
hive -e "set mapreduce.job.reduces = 37;
insert overwrite local directory '/app/sinova/var/ftp/report_prodetail_day_index/wap'
row format delimited fields terminated by '\t'
select province_id,c8 from spark_t_tmp_query_log
where application = 'wap'
distribute by province_id sort by c8;"

hive -e "
create table if not exists spark_wap (c8 string)partitioned by(province_id string)
row format delimited fields terminated by '\t'
location 'file:///app/sinova/var/ftp/report_prodetail_day_index/wap';"

hive -e "
set hive.exec.dynamic.partition=true;
set hive.exec.dynamic.partition.mode=nonstrict;
set mapreduce.job.reduces = 100;
insert overwrite table spark_wap partition(province_id)
select c8,province_id from spark_t_tmp_query_log
where application = 'wap';
"

set hive.exec.dynamic.partition=true;
set hive.exec.dynamic.partition.mode=nonstrict;
set hive.exec.dynamic.partitions.pernode=50000;
set hive.exec.dynamic.partitions.partitions=50000;
set hive.exec.max.created.files=500000;
set mapred.reduce.tasks =20000;
set hive.merge.mapfiles=true;
CREATE TABLE `spark_query_log` if not exists(
  `c1` string,
  `c2` string,
  `c3` string,
  `c4` string,
  `c5` string,
  `c6` string,
  `c7` string,
  `c8` string,
  `c9` string,
  `c10` string,
  `c11` string,
  `c12` string)
PARTITIONED BY (`application` string, `province_id` string)
row format delimited fields terminated by '\t';

