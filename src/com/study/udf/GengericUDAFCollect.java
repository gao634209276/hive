package com.study.udf;

import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDFArgumentTypeException;
import org.apache.hadoop.hive.ql.parse.SemanticException;
import org.apache.hadoop.hive.ql.udf.generic.AbstractGenericUDAFResolver;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDAFEvaluator;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDAFMkCollectionEvaluator;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.typeinfo.TypeInfo;

@Description(name = "collect", value = "_FUNC_(x) - Returns a list of objects. "
		+ "CAUTION will easily OOM on large data sets")
public class GengericUDAFCollect extends AbstractGenericUDAFResolver {

	public GengericUDAFCollect() {

	}

	@Override
	public GenericUDAFEvaluator getEvaluator(TypeInfo[] info)
			throws SemanticException {
		if (info.length != 1) {
			throw new UDFArgumentTypeException(info.length - 1,
					"Exactly one argument is expected.");

		}
		if (info[0].getCategory() != ObjectInspector.Category.PRIMITIVE) {
			throw new UDFArgumentTypeException(0,
					"Only primitive type arguments are accepted but "
							+ info[0].getTypeName()
							+ "was passed as parameter 1.");
		}
		return new GenericUDAFMkCollectionEvaluator();
	}
}
