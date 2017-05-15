package udf.generic;

/**
 * Created by noah on 17-5-15.
 * <p>
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p>
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorConverters;
import org.apache.hadoop.hive.serde2.objectinspector.PrimitiveObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.io.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * GenericUDFAddMonths.
 *
 * Add a number of months to the date. The time part of the string will be
 * ignored.
 *
 */
@Description(name = "add_months", value = "_FUNC_(start_date, num_months) - Returns the date that is num_months after start_date.", extended = "start_date is a string in the format "
		+ " 'yyyyMMdd'. num_months is a number. The time part of start_date is "
		+ "ignored.\n"
		+ "Example:\n "
		+ " > SELECT _FUNC_('20090831', 1) FROM src LIMIT 1;\n"
		+ " '20090930'\n")
public class UDFAddMonths extends GenericUDF {
	private final Calendar calendar = Calendar.getInstance();
	private final Text output = new Text();
	SimpleDateFormat standedFormatter = new SimpleDateFormat("yyyyMMdd");
	private Date inputDate = null;
	private transient ObjectInspectorConverters.Converter[] converters = new ObjectInspectorConverters.Converter[2];

	@Override
	public ObjectInspector initialize(ObjectInspector[] arguments)
			throws UDFArgumentException {
		PrimitiveObjectInspector inOi = (PrimitiveObjectInspector) arguments[1];
		ObjectInspector outOi = PrimitiveObjectInspectorFactory.writableStringObjectInspector;
		converters[1] = ObjectInspectorConverters.getConverter(inOi, outOi);
		inOi = (PrimitiveObjectInspector) arguments[0];
		outOi = PrimitiveObjectInspectorFactory.writableIntObjectInspector;
		converters[0] = ObjectInspectorConverters.getConverter(inOi, outOi);
		ObjectInspector outputOI = PrimitiveObjectInspectorFactory.writableStringObjectInspector;
		return outputOI;
	}

	public static void main(String[] args) {
	}

	@Override
	public Object evaluate(DeferredObject[] arguments) throws HiveException {
		int numMonths;
		try {
			numMonths = getIntValue(arguments, 1, converters);
			inputDate = getDateValue(arguments, 0, converters);
			output.set(standedFormatter.format(addMonth(inputDate, numMonths)
					.getTime()));
		} catch (Exception e) {
			e.printStackTrace();
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

	protected Calendar addMonth(Date d, int numMonths) {
		calendar.setTime(d);

		boolean lastDatOfMonth = isLastDayOfMonth(calendar);

		calendar.add(Calendar.MONTH, numMonths);

		if (lastDatOfMonth) {
			int maxDd = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
			calendar.set(Calendar.DAY_OF_MONTH, maxDd);
		}
		return calendar;
	}

	protected boolean isLastDayOfMonth(Calendar cal) {
		int maxDd = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		int dd = cal.get(Calendar.DAY_OF_MONTH);
		return dd == maxDd;
	}

	protected Integer getIntValue(DeferredObject[] arguments, int i,
	                              ObjectInspectorConverters.Converter[] converters) throws HiveException {
		Object obj;
		if ((obj = arguments[i].get()) == null) {
			return null;
		}
		Object writableValue = converters[i].convert(obj);
		int v = Integer.valueOf(((Text) writableValue).toString());
		return v;
	}

	protected Date getDateValue(DeferredObject[] arguments, int i,
	                            ObjectInspectorConverters.Converter[] converters) throws HiveException {
		Object obj;
		if ((obj = arguments[i].get()) == null) {
			return null;
		}
		Date date;
		try {
			date = standedFormatter
					.parse(converters[i].convert(obj).toString());
		} catch (ParseException e) {
			return null;
		}
		return date;
	}
}
