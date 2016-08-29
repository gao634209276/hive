package com.study.udf;

import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDAFEvaluator;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;

public class GenericUDAFMkListEvaluator extends GenericUDAFEvaluator {
	
	@Override
	public ObjectInspector init(Mode m, ObjectInspector[] parameters)
			throws HiveException {
		// TODO Auto-generated method stub
		return super.init(m, parameters);
	}
	@Override
	public AggregationBuffer getNewAggregationBuffer() throws HiveException {
		
		return null;
	}

	@Override
	public void reset(AggregationBuffer agg) throws HiveException {
		// TODO Auto-generated method stub

	}

	@Override
	public void iterate(AggregationBuffer agg, Object[] parameters)
			throws HiveException {
		// TODO Auto-generated method stub

	}

	@Override
	public Object terminatePartial(AggregationBuffer agg) throws HiveException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void merge(AggregationBuffer agg, Object partial)
			throws HiveException {
		// TODO Auto-generated method stub

	}

	@Override
	public Object terminate(AggregationBuffer agg) throws HiveException {
		// TODO Auto-generated method stub
		return null;
	}

}
