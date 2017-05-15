package udf.udaf.demo;

/**
 * 所有evaluators必须继承抽象类org.apache.hadoop.hive.ql.udf.generic.GenericUDAFEvaluator。
 * 子类必须实现它的一些抽象方法，实现UDAF的逻辑。
 * GenericUDAFEvaluator有一个嵌套类Mode,这个类很重要，它表示了udaf在mapreduce的各个阶段，
 * 理解Mode的含义，就可以理解了hive的UDAF的运行流程。
 * Created by noah on 17-5-15.
 *
 * @see org.apache.hadoop.hive.ql.udf.generic.GenericUDAFEvaluator.Mode
 */
public enum Mode {
	/**
	 * PARTIAL1: 这个是mapreduce的map阶段:从原始数据到部分数据聚合
	 * 将会调用iterate()和terminatePartial()
	 */
	PARTIAL1,
	/**
	 * PARTIAL2: 这个是mapreduce的map端的Combiner阶段，负责在map端合并map的数据::从部分数据聚合到部分数据聚合:
	 * 将会调用merge() 和 terminatePartial()
	 */
	PARTIAL2,
	/**
	 * FINAL: mapreduce的reduce阶段:从部分数据的聚合到完全聚合
	 * 将会调用merge()和terminate()
	 */
	FINAL,
	/**
	 * COMPLETE: 如果出现了这个阶段，表示mapreduce只有map，没有reduce，所以map端就直接出结果了:从原始数据直接到完全聚合
	 * 将会调用 iterate()和terminate()
	 */
	COMPLETE
};