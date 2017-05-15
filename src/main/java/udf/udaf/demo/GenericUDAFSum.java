package udf.udaf.demo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.hive.ql.exec.UDFArgumentTypeException;
import org.apache.hadoop.hive.ql.parse.SemanticException;
import org.apache.hadoop.hive.ql.udf.generic.AbstractGenericUDAFResolver;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDAFEvaluator;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.typeinfo.PrimitiveTypeInfo;
import org.apache.hadoop.hive.serde2.typeinfo.TypeInfo;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDAFSum.GenericUDAFSumLong;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDAFSum.GenericUDAFSumDouble;

/**
 * resolver通常继承org.apache.hadoop.hive.ql.udf.GenericUDAFResolver2，
 * 但是我们更建议继承AbstractGenericUDAFResolver，隔离将来hive接口的变化。
 * GenericUDAFResolver和GenericUDAFResolver2接口的区别是，
 * 后面的允许evaluator实现可以访问更多的信息，例如DISTINCT限定符，通配符FUNCTION(*)。
 * 该类就是UDAF的代码骨架,第一行创建LOG对象，用来写入警告和错误到hive的log
 * GenericUDAFResolver只需要重写一个方法：getEvaluator，
 * 它根据SQL传入的参数类型,返回正确的evaluator。这里最主要是实现操作符的重载。
 * Created by noah on 17-5-15.
 */
public class GenericUDAFSum extends AbstractGenericUDAFResolver {

	static final Log LOG = LogFactory.getLog(GenericUDAFSum.class.getName());

	/**
	 * 这里做了类型检查，如果不是原生类型(即符合类型，array,map此类)，则抛出异常，
	 * 还实现了操作符重载，对于整数类型，使用GenericUDAFSumLong实现UDAF的逻辑，
	 * 对于浮点类型，使用GenericUDAFSumDouble实现UDAF的逻辑。
	 */
	@Override
	public GenericUDAFEvaluator getEvaluator(TypeInfo[] parameters)
			throws SemanticException {
		// Type-checking goes here!
		if (parameters[0].getCategory() != ObjectInspector.Category.PRIMITIVE) {
			throw new UDFArgumentTypeException(0,
					"Only primitive type arguments are accepted but "
							+ parameters[0].getTypeName() + " is passed.");
		}
		switch (((PrimitiveTypeInfo) parameters[0]).getPrimitiveCategory()) {
			case BYTE:
			case SHORT:
			case INT:
			case LONG:
			case TIMESTAMP:
				return new GenericUDAFSumLong();
			case FLOAT:
			case DOUBLE:
			case STRING:
				return new GenericUDAFSumDouble();
			case BOOLEAN:
			default:
				throw new UDFArgumentTypeException(0,
						"Only numeric or string type arguments are accepted but "
								+ parameters[0].getTypeName() + " is passed.");


				//return new GenericUDAFSumLong();
		}
	}

	/*public static class MyGenericUDAFSumLong extends GenericUDAFEvaluator {
		// UDAF logic goes here!
	}*/
}