package hive.inputformat;

import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
public class UDInputFormat<K,V> extends SequenceFileInputFormat<K, V> {
}
