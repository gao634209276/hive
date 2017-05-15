package base;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * date
 * Created by noah on 17-5-15.
 */
public class TestDate {

	@Test
	public void testDate() throws ParseException {
		String strDate = "20170130";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyDDmm");
		Date d = sdf.parse(strDate);
		Calendar c = new GregorianCalendar();
		c.setTime(d);
		int max = c.getActualMaximum(Calendar.DAY_OF_MONTH);
		c.add(Calendar.MONTH,1);
		Date addDate = c.getTime();
		//int max = c.getActualMaximum(Calendar.DAY_OF_MONTH);
		System.out.println(max);

		System.out.println(sdf.format(addDate));

	}
}
