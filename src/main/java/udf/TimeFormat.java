package udf;

import java.sql.Date;
import java.text.SimpleDateFormat;

import org.apache.hadoop.hive.ql.exec.UDF;

public class TimeFormat extends UDF {

	public String evaluate(String num) {
		Date d = new Date(Long.decode(num));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS");
		return sdf.format(d);
	}


	public static void main(String[] args) {
		System.out.println(System.currentTimeMillis());
		java.util.Date d = new java.util.Date();
		System.out.println(d);
		System.out.println(d.getTime());
	}
}
