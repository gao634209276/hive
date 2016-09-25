package serde;

import java.util.Properties;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hive.serde2.SerDe;
import org.apache.hadoop.hive.serde2.SerDeException;
import org.apache.hadoop.hive.serde2.SerDeStats;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.io.Writable;

/**
 * 自定义Serde实现SerDe接口,对方法重写即可 See:RegexSerDe,Hive自带正则表达式实现类
 * 
 * @author hadoop
 * 
 */
@SuppressWarnings("deprecation")
public class UDSerde implements SerDe {

	@Override
	public Object deserialize(Writable arg0) throws SerDeException {
		return null;
	}

	@Override
	public ObjectInspector getObjectInspector() throws SerDeException {
		return null;
	}

	@Override
	public SerDeStats getSerDeStats() {
		return null;
	}

	@Override
	public void initialize(Configuration arg0, Properties arg1)
			throws SerDeException {

	}

	@Override
	public Class<? extends Writable> getSerializedClass() {
		return null;
	}

	@Override
	public Writable serialize(Object arg0, ObjectInspector arg1)
			throws SerDeException {
		return null;
	}

}
