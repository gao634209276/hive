package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class JDBCHive {

	//private static String Driver = "org.apache.hadoop.hive.jdbc.HiveDriver";
	private static String Driver = "org.apache.hive.jdbc.HiveDriver";
	//private static String URL = "jdbc:hive://hadoop:10000/default";
	private static String URL = "jdbc:hive2://hadoop:10000/default";
	private static String name = "";
	private static String password = "";
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		
		Class.forName(Driver);
		Connection conn = DriverManager.getConnection(URL,name,password);
		Statement stat = conn.createStatement();
		String sql = "show tables";
		//String sql = "select * from userinfo";
		ResultSet res = stat.executeQuery(sql);
		while(res.next()){
			 System.out.println(res.getString(1));//从1开始
		}
	}
}
