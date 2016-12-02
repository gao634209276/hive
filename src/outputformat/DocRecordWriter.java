package outputformat;

import java.io.IOException;

import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.mapred.RecordWriter;
import org.apache.hadoop.mapred.Reporter;

public class DocRecordWriter implements RecordWriter {

	private FSDataOutputStream out;
	private final String DOC_START = "<DOC>";
	private final String DOC_END = "</DOC>";

	public DocRecordWriter(FSDataOutputStream o) {
		this.out = o;
	}

	@Override
	public void close(Reporter reporter) throws IOException {
		out.flush();
		out.close();

	}

	@Override
	public void write(Object key, Object value) throws IOException {
		write(DOC_START);
		write("\n");
		write(value.toString());
		write("\n");
		write(DOC_END);
		write("\n");

	}

	private void write(String str) throws IOException {
		out.write(str.getBytes(), 0, str.length());
	}
}
