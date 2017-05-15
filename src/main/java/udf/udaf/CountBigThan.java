package udf.udaf;

import org.apache.hadoop.hive.ql.exec.UDFArgumentTypeException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.parse.SemanticException;
import org.apache.hadoop.hive.ql.udf.generic.AbstractGenericUDAFResolver;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDAFEvaluator;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDAFParameterInfo;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.PrimitiveObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorUtils;
import org.apache.hadoop.io.LongWritable;

/**
 * 在bigThan函数结果同时做一个count
 */
@SuppressWarnings("deprecation")
public class CountBigThan extends AbstractGenericUDAFResolver {

	@Override
	public GenericUDAFEvaluator getEvaluator(GenericUDAFParameterInfo info)
			throws SemanticException {
		if (info.getParameters().length != 2) {// 如果参数个数不是2,抛出异常
			throw new UDFArgumentTypeException(info.getParameters().length - 1,
					"Exactly two argument is expected.");
		}// 也可以对参数类型进行判断
		return super.getEvaluator(info);// 返回
	}

	// genericEvaluate用于处理逻辑
	public static class genericEvaluate extends GenericUDAFEvaluator {
		private LongWritable result;
		private PrimitiveObjectInspector inputOI1;// 描述类型
		private PrimitiveObjectInspector inputOI2;

		/*
		 * map和reduce阶段都执行,初始化1次(non-Javadoc) map阶段:parameters长度与udaf输入参数个数有关
		 * reduce阶段:parameters长度为1
		 */
		@Override
		public ObjectInspector init(Mode m, ObjectInspector[] parameters)
				throws HiveException {
			super.init(m, parameters);
			// 最终结果
			result = new LongWritable(0);// LongWritable可序列化

			inputOI1 = (PrimitiveObjectInspector) parameters[0];
			if (parameters.length > 1) {
				inputOI2 = (PrimitiveObjectInspector) parameters[1];// reduce阶段参数只有1个
			}
			// 返回一个long类型
			return PrimitiveObjectInspectorFactory.writableLongObjectInspector;
		}

		/**
		 * 该方法只会在map阶段的操作, reduce阶段不会执行; Object[] par 传入执行的参数 ;
		 * AggregationBuffer agg 保存iterator执行玩的结果
		 */
		@Override
		public void iterate(AggregationBuffer agg, Object[] par)
				throws HiveException {
			assert (par.length == 2);// 断言,如果不是2个,异常
			if (par == null || par[0] == null || par[1] == null) {
				return;
			}
			// getDouble(par[0],inputOI1);通用方法,将其他类型转化为double类型
			// 其中par[0]为传递的数值,inputOI1指定元数据的类型
			double base = PrimitiveObjectInspectorUtils.getDouble(par[0],
					inputOI1);
			double tmp = PrimitiveObjectInspectorUtils.getDouble(par[1],
					inputOI2);
			if (base > tmp) {// 如果base比tmp,则将agg中保存的值加1
				((CountAgg) agg).count++;
			}
		}

		// 自定义一个内部类,CountAgg保存一个long类型的值,该值为某个操作的临时结果
		public static class CountAgg implements AggregationBuffer {
			long count;
		}

		// 返回一个聚合对象,对象中保存(缓冲)了临时的值:如map/merge的结果等
		@Override
		public AggregationBuffer getNewAggregationBuffer() throws HiveException {
			// 在执行之前,new该对象,不同需求,对象保存的值不同,所以对象定义不同
			// 这里自定义一个CountAgg类
			CountAgg agg = new CountAgg();

			// 重置,重复利用
			// 如每个map执行1次getNewAggregationBuffer方法,对象执行完就没用了
			// 为了重复利用agg,使用reset进行重置
			// 其他类似如:jvm重利用
			reset(agg);
			return agg;
		}

		// 重置CountAgg
		@Override
		public void reset(AggregationBuffer countAgg) throws HiveException {
			CountAgg agg = (CountAgg) countAgg;// 强制转化
			agg.count = 0;// 重置将count清空为0
		}

		// 将AggregationBuffer中的值作为部分结果返回
		@Override
		public Object terminatePartial(AggregationBuffer agg)
				throws HiveException {
			result.set(((CountAgg) agg).count);
			return result;
		}

		/**
		 * merge对map产生的partial(部分)与当前map产生的值agg进行合并操作, AggregationBuffer
		 * 聚合操作缓存对象,保存操作中得到结果, Object partial 为terminatePartial方法返回的对象
		 */
		@Override
		public void merge(AggregationBuffer agg, Object partial)
				throws HiveException {
			if (partial != null) {// 使用工具类PrimitiveObjectInspectorUtils讲partial转换为long类型
				long p = PrimitiveObjectInspectorUtils.getLong(partial,
						inputOI1);
				((CountAgg) agg).count += p;// 讲p与count相加后保存在count中
			}
		}

		@Override
		public Object terminate(AggregationBuffer agg) throws HiveException {
			// 将agg中的count拿出来复制给result返回
			result.set(((CountAgg) agg).count);
			return result;
		}

	}
}
