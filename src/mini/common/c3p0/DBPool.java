package mini.common.c3p0;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DBPool {
	Log log = LogFactory.getLog(DBPool.class);
	private static DBPool dbPool;
	private ComboPooledDataSource dataSource;

	static {
		dbPool = new DBPool();
	}

	public DBPool() {
		try {
			dataSource = new ComboPooledDataSource();
			dataSource.setUser("pkup");
			dataSource.setPassword("pkup");
			// dataSource
			// .setJdbcUrl("jdbc:mysql://192.168.1.230:3306/pkup?autoReconnect=true&useUnicode=true&characterEncoding=GB2312 ");
			// dataSource.setDriverClass("com.mysql.jdbc.Driver");
			dataSource.setJdbcUrl("jdbc:postgresql://192.168.1.230/pkupdb");
			dataSource.setDriverClass("org.postgresql.Driver");
			dataSource.setInitialPoolSize(5);
			dataSource.setMinPoolSize(1);
			dataSource.setMaxPoolSize(10);
			dataSource.setMaxStatements(50);
			dataSource.setMaxIdleTime(60);
		} catch (Exception e) {
			// throw new RuntimeException(e);
			log.error("数据库异常：", e);
		}
	}

	public final static DBPool getInstance() {
		return dbPool;
	}

	public final Connection getConnection() {
		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException("无法从数据源获取连接 ", e);

		}
	}

}
