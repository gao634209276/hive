package hive.outputformat;

import java.io.IOException;
import java.util.Properties;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hive.ql.io.HiveOutputFormat;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.RecordWriter;
import org.apache.hadoop.mapred.TextOutputFormat;
import org.apache.hadoop.util.Progressable;

public class DocFileOutputFormat<K extends WritableComparable, V extends Writable>
		extends TextOutputFormat<K, V>
		implements HiveOutputFormat<K, V> {

	@Override
	public RecordWriter<K, V> getRecordWriter(FileSystem ignored, JobConf job,
			String name, Progressable progress) throws IOException {
		return null;
	}

	@Override
	public void checkOutputSpecs(FileSystem ignored, JobConf job)
			throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public org.apache.hadoop.hive.ql.exec.FileSinkOperator.RecordWriter getHiveRecordWriter(
			JobConf arg0, Path arg1, Class<? extends Writable> arg2,
			boolean arg3, Properties arg4, Progressable arg5)
			throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

}
