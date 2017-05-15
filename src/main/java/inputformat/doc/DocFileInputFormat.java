package inputformat.doc;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileSplit;
import org.apache.hadoop.mapred.InputSplit;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.JobConfigurable;
import org.apache.hadoop.mapred.RecordReader;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapred.TextInputFormat;

/**
 * Hive的InputFormat来源于Hadoop中的对应的部分。需要注意的是，其采用了mapred的老接口
 */
public class DocFileInputFormat extends TextInputFormat implements JobConfigurable{

	@Override
	public RecordReader<LongWritable, Text> getRecordReader(
			InputSplit genericSplit, JobConf job, Reporter reporter)
			throws IOException {
		reporter.setStatus(genericSplit.toString());
		//return super.getRecordReader(genericSplit, job, reporter);
		return new DocRecordReader(job, (FileSplit) genericSplit);
	}
}
