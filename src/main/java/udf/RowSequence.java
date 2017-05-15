package udf;

import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.hive.ql.udf.UDFType;
import org.apache.hadoop.hive.ql.exec.Description;

/**
 * UDFRowSequence.
 * add jar /home/hadoop/hive_study/hive_udf/hive_udf.jar
 * create temporary function row_sequence as 'RowSequence';
 * select row_sequence(),telephone from xxx;
 */
@Description(name = "row_sequence",
		value = "_FUNC_() - Returns a generated row sequence number starting from 1")
@UDFType(deterministic = false)
public class RowSequence extends UDF {
	private LongWritable result = new LongWritable();

	public RowSequence() {
		result.set(0);
	}

	public LongWritable evaluate() {
		result.set(result.get() + 1);
		return result;
	}
}