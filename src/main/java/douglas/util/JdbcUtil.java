package douglas.util;

import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Author:  wgz
 * Date:    16/2/19
 * Time:    下午5:51
 * Desc:
 */
public final class JdbcUtil {
    private static final Logger LOG = LoggerFactory.getLogger(JdbcUtil.class);
    private QueryRunner queryRunner = new QueryRunner();

    // 拒绝new一个实例
    private JdbcUtil() {}

    public List<Object[]> query(DataSource dataSource, String sql) {
        List<Object[]> result = new ArrayList<>();
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            List<Object[]> requestList = queryRunner.query(connection, sql,
                    new ArrayListHandler(new BasicRowProcessor() {
                        @Override
                        public <Object> List<Object> toBeanList(ResultSet rs,
                                                                Class<Object> type) throws SQLException {
                            return super.toBeanList(rs, type);
                        }
                    }));
            for (Object[] objects : requestList) {
                result.add(objects);
            }
        } catch (SQLException e) {
            LOG.warn("exception: [{}]", e);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                LOG.warn("exception: [{}]", e);
            }
        }
        return result;
    }

    @SuppressWarnings("unused")
    public void update(DataSource dataSource, String sql, Object... params) {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            queryRunner.update(connection, sql, params);
            connection.close();
        } catch (SQLException e) {
            LOG.warn("exception: [{}]", e);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                LOG.warn("exception: [{}]", e);
            }
        }
    }
}

