package jdbc;

import java.util.List;

import org.apache.hadoop.hive.service.HiveServerException;
import org.apache.hadoop.hive.service.ThriftHive;
import org.apache.hadoop.hive.service.ThriftHive.Client;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;

/**
 * hive --service hiveserver -v -p 10001
 */
public class HiveThriftClient {

	public static void main(String[] args) throws HiveServerException, TException {
		// 创建Socket:链接
		final TSocket transport = new TSocket("192.168.2.7", 10001);
		transport.setTimeout(999999999);
		transport.open();
		// 创建一个协议
		final TProtocol protocol = new TBinaryProtocol(transport);
		// 创建Hive Client
		Client client = new ThriftHive.Client(protocol);
		// 打开Socket
		transport.open();
		// 执行HQL
		client.execute("desc tb1");
		// 处理结果
		List<String> columns = client.fetchAll();
		for (String col : columns) {
			System.out.println(col);
		}
		transport.close();
	}
}
