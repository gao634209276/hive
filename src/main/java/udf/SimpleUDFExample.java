package udf;

import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

/**
 * 用简单UDF API来构建一个UDF只涉及到编写一个类继承实现一个方法（evaluate），以下是示例：
 */
public class SimpleUDFExample extends UDF {

	/**
	 * 判断t1是否比t2大
	 */
	public boolean evaluate(Text t1, Text t2) {
		if (t1 == null || t2 == null) {
			return false;
		}
		double d1 = Double.parseDouble(t1.toString());
		double d2 = Double.parseDouble(t2.toString());
		if (d1 > d2) {
			return true;
		} else {
			return false;
		}
	}
	
}
