package udf;

import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.hive.ql.udf.UDFType;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Description(name = "str_to_date", value = "_FUNC_(dateText, pattern [, days]) - Convert time string with given pattern "
		+ "to time string with 'yyyy-MM-dd HH:mm:ss' pattern\n", extended = "Example:\n"
		+ "> SELECT _FUNC_('2011/05/01','yyyy/MM/dd') FROM src LIMIT 1;\n"
		+ "2011-05-01 00:00:00\n"
		+ "> SELECT _FUNC_('2011/07/21 12:55:11'.'yyyy/MM/dd HH:mm:ss') FROM src LIMIT 1;\n"
		+ "2011-07-21 12:55:11\n")
@UDFType(deterministic = false)
public class UDFStrToDate extends UDF {
	private final SimpleDateFormat standardFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private final SimpleDateFormat formatter = new SimpleDateFormat();
	private final Calendar calendar = Calendar.getInstance();

	public UDFStrToDate() {
		standardFormatter.setLenient(false);
		formatter.setLenient(false);
	}

	Text result = new Text();
	Text lastPatternText = new Text();

	public Text evaluate(Text dateText, Text patternText) {
		if (dateText == null || patternText == null) {
			return null;
		}
		try {
			if (!patternText.equals(lastPatternText)) {
				formatter.applyPattern(patternText.toString());
				lastPatternText.set(patternText);
			}
		} catch (Exception e) {
			return null;
		}

		java.util.Date date;
		try {
			date = formatter.parse(dateText.toString());
			result.set(standardFormatter.format(date));
			return result;
		} catch (ParseException e) {
			return null;
		}
	}

	Text t = new Text();

	public Text evaluate(Text dateText, Text patternText, IntWritable days) {
		if (dateText == null || patternText == null || days == null) {
			return null;
		}

		t = evaluate(dateText, patternText);
		try {
			calendar.setTime(standardFormatter.parse(t.toString()));
			calendar.add(Calendar.DAY_OF_MONTH, days.get());
			java.util.Date newDate = calendar.getTime();
			result.set(standardFormatter.format(newDate));
			return result;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
}
