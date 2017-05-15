package udf.generic;

import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorConverters;

public class GenericUDFJSONArray extends GenericUDF {
	private transient ObjectInspectorConverters.Converter converter;


	@Override
	public ObjectInspector initialize(ObjectInspector[] objectInspectors) throws UDFArgumentException {
		if (objectInspectors.length != 1) {
			throw new UDFArgumentException(
					"The function json_array(jsonArray");
		}
		//converter = ObjectInspector.
		return null;
	}

	@Override
	public Object evaluate(DeferredObject[] deferredObjects) throws HiveException {
		return null;
	}

	@Override
	public String getDisplayString(String[] strings) {
		return null;
	}
}