package udf.generic;

import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.exec.UDFArgumentLengthException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.IntObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.StringObjectInspector;
import org.apache.hadoop.io.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * xxx
 * Created by noah on 17-5-15.
 */
@Description(name = "add_months", value = "_FUNC_(start_date, num_months) - Returns the date that is num_months after start_date.", extended = "start_date is a string in the format "
		+ " 'yyyyMMdd'. num_months is a number. The time part of start_date is "
		+ "ignored.\n"
		+ "Example:\n "
		+ " > SELECT _FUNC_('20090831', 1) FROM src LIMIT 1;\n"
		+ " '20090930'\n")
public class MyAddMonthsUDF extends GenericUDF {
	StringObjectInspector strOI;
	IntObjectInspector intOI;
	private final Text output = new Text();
	private final SimpleDateFormat standedFormatter = new SimpleDateFormat("yyyyMMdd");
	private final Calendar calendar = Calendar.getInstance();
	@Override
	public ObjectInspector initialize(ObjectInspector[] arguments) throws UDFArgumentException {
		if (arguments.length != 2) {
			throw new UDFArgumentLengthException("The function COUNTRY(ip, geolocfile) takes exactly 2 arguments.");
		}
		ObjectInspector a = arguments[0];
		ObjectInspector b = arguments[1];
		if (!(a instanceof StringObjectInspector) || !(b instanceof IntObjectInspector)) {
			throw new UDFArgumentException("first argument must be a string, second argument must be a int");
		}
		this.strOI = (StringObjectInspector) a;
		this.intOI = (IntObjectInspector) b;
		return PrimitiveObjectInspectorFactory.writableStringObjectInspector;
	}

	@Override
	public Object evaluate(DeferredObject[] arguments) throws HiveException {
		Object dateObj;
		Object addObj;
		if ((dateObj = arguments[0].get()) == null || (addObj = arguments[1].get()) == null) {
			return null;
		}
		String dateStr = this.strOI.getPrimitiveJavaObject(dateObj);
		int numMonths = this.intOI.get(addObj);
		try {
			Date inputDate = standedFormatter.parse(dateStr);
			calendar.setTime(inputDate);
			calendar.add(Calendar.MONTH, numMonths);
			int maxDd = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
			if (maxDd < calendar.get(Calendar.DAY_OF_MONTH)) {
				calendar.set(Calendar.DAY_OF_MONTH, maxDd);
			}
			output.set(standedFormatter.format(calendar.getTime()));

		} catch (ParseException e) {
			return null;
		}
		return output;
	}

	@Override
	public String getDisplayString(String[] children) {
		StringBuilder sb = new StringBuilder();
		sb.append("add_months");
		sb.append("(");
		if (children.length > 0) {
			sb.append(children[0]);
			for (int i = 1; i < children.length; i++) {
				sb.append(",");
				sb.append(children[i]);
			}
		}
		sb.append(")");
		return sb.toString();
	}
}
