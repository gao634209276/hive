package udf;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDF;

@Description(name = "zodiac", value = "_FUNC_(date) - from the input date string"
		+ "or separate month and day arguments, returns the sign of the Zodiac.", extended = "Example:\n"
		+ "> SELECT _FUNC_(date_string) FROM src;\n"
		+ "> SELECT _FUNC_(month,dat) FROM src;")
public class UDFZodiacSign extends UDF {
	private SimpleDateFormat df;

	public UDFZodiacSign() {
		df = new SimpleDateFormat("MM-dd-yyyy");
	}

	@SuppressWarnings({ "deprecation" })
	public String evaluate(Date bday) {
		return this.evaluate(bday.getMonth() + 1, bday.getDate());
	}

	@SuppressWarnings({ "deprecation" })
	public String evaluate(String bday) {
		Date date = null;
		try {
			date = df.parse(bday);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return this.evaluate(date.getMonth() + 1, date.getDate());

	}

	public String evaluate(int month, int day) {
		if (month == 1) {
			if (day < 20) {
				return "Capricorn";
			} else {
				return "Aquarius";
			}
		}
		if (month == 2) {
			if (day < 10) {
				return "Aquarius";
			} else {
				return "Pisces";
			}
		}
		return null;
	}
}
