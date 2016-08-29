package com.study.udf;

import org.apache.hadoop.hive.ql.exec.UDFArgumentTypeException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.parse.SemanticException;
import org.apache.hadoop.hive.ql.udf.generic.AbstractGenericUDAFResolver;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDAFEvaluator;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDAFParameterInfo;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.PrimitiveObjectInspector;
import org.apache.hadoop.io.LongWritable;

public class CountBigThan extends AbstractGenericUDAFResolver {

	@SuppressWarnings("deprecation")
	@Override
	public GenericUDAFEvaluator getEvaluator(GenericUDAFParameterInfo info)
			throws SemanticException {
		if (info.getParameters().length != 2) {
			throw new UDFArgumentTypeException(info.getParameters().length - 1,
					"Exactly two argument is expected.");
		}
		return super.getEvaluator(info);
	}

	public static class genericEvaluate extends GenericUDAFEvaluator {
		private LongWritable result;
		private PrimitiveObjectInspector inputOI1;
		private PrimitiveObjectInspector inputOI2;

		@Override
		public ObjectInspector init(Mode m, ObjectInspector[] parameters)
				throws HiveException {
			// 最终结果
			result = new LongWritable(0);
			inputOI1 = (PrimitiveObjectInspector) parameters[0];
			return super.init(m, parameters);
		}

		@Override
		public AggregationBuffer getNewAggregationBuffer() throws HiveException {
			return null;
		}

		@Override
		public void iterate(AggregationBuffer arg0, Object[] arg1)
				throws HiveException {

		}

		@Override
		public void merge(AggregationBuffer arg0, Object arg1)
				throws HiveException {

		}

		@Override
		public void reset(AggregationBuffer arg0) throws HiveException {

		}

		@Override
		public Object terminate(AggregationBuffer arg0) throws HiveException {
			return null;
		}

		@Override
		public Object terminatePartial(AggregationBuffer arg0)
				throws HiveException {
			return null;
		}

	}
}
