package jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class HiveJDBCemo {
	public static void main(String[] args) {
		Connection conn = null;
		Statement st = null;
		String sql = "select * from t1";
		ResultSet rs = null;
		try {
			// 获取链接
			conn = JDBCUtils.getConnetction();
			// 创建运行环境
			st = conn.createStatement();
			// 运行HQL
			rs = st.executeQuery(sql);
			// 处理数据
			while (rs.next()) {
				// 取出员工的姓名和薪水
				String name = rs.getString(2);
				int id = rs.getInt(1);
				System.out.println(name + "\t" + id);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.release(conn, st, rs);
		}
	}
}
