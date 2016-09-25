package inputformat;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileRecordReader;

@InterfaceAudience.Public
@InterfaceStability.Stable
public class UDRecordReader<K, V> extends SequenceFileRecordReader<K, V> {

}
