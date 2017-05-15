package udf.udaf;

import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.parse.SemanticException;
import org.apache.hadoop.hive.ql.udf.generic.AbstractGenericUDAFResolver;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDAFEvaluator;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDAFParameterInfo;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;

//udaf类继承AbstractGenericUDAFResolver
public class UDAFtest extends AbstractGenericUDAFResolver {
	// 重写getEvaluator
	@Override
	public GenericUDAFEvaluator getEvaluator(GenericUDAFParameterInfo info)
			throws SemanticException {
		return super.getEvaluator(info);
	}

	// 定义内部类继承GenericUDAFEvaluator
	public static class genericEvaluate extends GenericUDAFEvaluator {

		// 重写init,初始化
		// Mode用来判断执行到那个步骤,然后使用不同的方法
		@Override
		public ObjectInspector init(Mode m, ObjectInspector[] parameters)
				throws HiveException {
			return super.init(m, parameters);
		}

		// 获取保存缓存通过map/merge等得到的结果
		@Override
		public AggregationBuffer getNewAggregationBuffer() throws HiveException {
			// TODO Auto-generated method stub
			return null;
		}

		// 重置,不需要重新建一个对象,增加内存使用率
		@Override
		public void reset(AggregationBuffer agg) throws HiveException {
			// TODO Auto-generated method stub

		}

		// 迭代获取hive原始数据进行操作
		@Override
		public void iterate(AggregationBuffer agg, Object[] parameters)
				throws HiveException {
			// TODO Auto-generated method stub

		}

		// 返回部分结果
		@Override
		public Object terminatePartial(AggregationBuffer agg)
				throws HiveException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void merge(AggregationBuffer agg, Object partial)
				throws HiveException {
			// TODO Auto-generated method stub

		}

		// 返回所有结果
		@Override
		public Object terminate(AggregationBuffer agg) throws HiveException {
			// TODO Auto-generated method stub
			return null;
		}

	}
}
