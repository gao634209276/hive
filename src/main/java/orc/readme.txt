http://blog.csdn.net/liuzhoulong/article/details/52048105
创建表，在 t_test_orc中添加3行数据。
CREATE  TABLE `t_test_orc`(  
  `siteid` string,   
  `name` string,   
  `mobile` string)  
 stored as orc  
 
 CREATE TABLE `t_test_orc_new`(  
  `name` string,   
  `mobile` string)  
ROW FORMAT SERDE   
  'org.apache.hadoop.hive.ql.io.orc.OrcSerde'   
STORED AS INPUTFORMAT   
  'org.apache.hadoop.hive.ql.io.orc.OrcInputFormat'   
OUTPUTFORMAT   
  'org.apache.hadoop.hive.ql.io.orc.OrcOutputFormat'  
LOCATION  
  'hdfs://namenode:9000/user/testorc3'  
打包运行
Hadoop jar MRTest-1.0-jar-with-dependencies.jar hive.ORCSample /hive/warehouse/mytest.db/t_test_orc /user/testorc3
完成后可以用hive --orcfiledump -d 查看执行结果
并且进入hive 查询orc格式的 t_test_orc表也可以看到数据

更多信息可以参考 https://orc.apache.org/