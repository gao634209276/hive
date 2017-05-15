hive的用户自定义聚合函数（UDAF）是一个很好的功能，集成了先进的数据处理。
hive有两种UDAF：简单和通用。
	顾名思义，简单的UDAF，写的相当简单的，但因为使用Java反射导致性能损失，而且有些特性不能使用，如可变长度参数列表。
	通用UDAF可以使用​​所有功能，但是UDAF就写的比较复杂，不直观。
本文只介绍通用UDAF。
    UDAF是需要在hive的sql语句和group by联合使用，hive的group by对于每个分组，只能返回一条记录，这点和mysql不一样，切记。


实现 resolver
	resolver通常继承org.apache.hadoop.hive.ql.udf.GenericUDAFResolver2，
	但是我们更建议继承AbstractGenericUDAFResolver，隔离将来hive接口的变化。
	代码 See: GenericUDAFSum.java
实现evaluator
	所有evaluators必须继承抽象类org.apache.hadoop.hive.ql.udf.generic.GenericUDAFEvaluator。
	子类必须实现它的一些抽象方法，实现UDAF的逻辑。
	GenericUDAFEvaluator有一个嵌套类Mode,这个类很重要，它表示了udaf在mapreduce的各个阶段，
	理解Mode的含义，就可以理解了hive的UDAF的运行流程。
	See: Mode.java
	一般情况下，完整的UDAF逻辑是一个mapreduce过程，
	如果有mapper和reducer，就会经历PARTIAL1(mapper)，FINAL(reducer)，
	如果还有combiner，那就会经历PARTIAL1(mapper)，PARTIAL2(combiner)，FINAL(reducer)。
	而有一些情况下的mapreduce，只有mapper，而没有reducer，所以就会只有COMPLETE阶段，这个阶段直接输入原始数据，出结果。

	下面以GenericUDAFSumLong的evaluator实现讲解
	See: GenericUDAGSumLong.java

修改方法注册
	修改 ql/src/java/org/apache/hadoop/hive/ql/exec/FunctionRegistry.java文件，加入编写的UDAF类，并注册名字。
	FunctionRegistry类包含了hive的所有内置自定义函数。想要更好学习hive的UDAF，建议多看看里面的UDAF。

总结
	本文的目的是为初学者入门学习udaf，所以介绍了udaf的概览，尤其是udaf的运行过程，这对初学者是比较大的槛。
	考虑入门，本文简单介绍了sum的UDAF实现，但是如果想要更好理解UDAF的运行过程，建议再看看avg UDAF:org.apache.hadoop.hive.ql.udf.generic.GenericUDAFAverage。avg UDAF对hive的运行流程要控制的更加精细，并判断当前运行的Mode做一定的逻辑处理。

参考:
	https://cwiki.apache.org/Hive/genericudafcasestudy.html
	http://www.cnblogs.com/ggjucheng/archive/2013/02/01/2888051.html