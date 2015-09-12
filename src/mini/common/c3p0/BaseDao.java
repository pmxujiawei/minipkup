package mini.common.c3p0;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BaseDao {
	Log log = LogFactory.getLog(BaseDao.class);

	protected Connection getConnection() {
		return DBPool.getInstance().getConnection();
	}

	protected void close(Connection conn, PreparedStatement ps, ResultSet rs) {
		if (null != conn) {
			try {
				conn.close();
			} catch (Exception e) {
				log.error("释放数据库资源异常：", e);
			}
		}
		if (null != ps) {
			try {
				ps.close();
			} catch (Exception e) {
				log.error("释放数据库资源异常：", e);
			}
		}
		if (null != rs) {
			try {
				rs.close();
			} catch (Exception e) {
				log.error("释放数据库资源异常：", e);
			}
		}
	}
}
