package org.macbeth.jdbc.demo;

import com.google.common.collect.Lists;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.macbeth.jdbc.demo.model.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class MySqlJdbcDemo {

    private final static String JDB_URL = "jdbc:mysql://127.0.0.1:3306/Customer_";
    private final static String JDB_USER = "root";
    private final static String JDB_PWD = "";
    private static DataSource dataSource;

    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(JDB_URL);
        config.setUsername(JDB_USER);
        config.setPassword(JDB_PWD);
        config.addDataSourceProperty("connectionTimeout", "1000");
        config.addDataSourceProperty("idleTimeout", "60000");
        config.addDataSourceProperty("maximumPoolSize", "10");
        dataSource = new HikariDataSource(config);
    }

    /**
     * insert sql - normal
     * @param user
     * @return
     * @throws SQLException
     */
    private boolean addUser(User user) throws SQLException {
        if (null == user) {
            return false;
        }
        String insertSql = String.format("insert into User (userId,loginName,loginNameType) values (%s,'%s',%s)",
                user.getUserId(), user.getLoginName(), user.getLoginNameType());
        try (Connection conn = DriverManager.getConnection(JDB_URL, JDB_USER, JDB_PWD)) {
            try (Statement stmt = conn.createStatement()) {
                return stmt.executeUpdate(insertSql) > 0;
            }
        }
    }

    /**
     * add batch
     * @param userList
     * @throws SQLException
     */
    private void addBatchUser(List<User> userList) throws SQLException {
        if (null != userList && userList.size() > 0) {
            try (Connection conn = DriverManager.getConnection(JDB_URL, JDB_USER, JDB_PWD)) {
                try (PreparedStatement ps = conn.prepareStatement("insert into User (userId,loginName,loginNameType) values (?, ?, ?)")) {
                    for (User user : userList) {
                        ps.setObject(1, user.getLoginName());
                        ps.setObject(2, user.getLoginNameType());
                        ps.setObject(3, user.getUserId());
                        ps.addBatch();
                    }
                    ps.executeBatch();
                }
            }
        }
    }

    /**
     * insert sql - with transaction
     * @param userList
     * @throws SQLException
     */
    private void addUserWithTransaction(List<User> userList) throws SQLException {
        if (null != userList && userList.size() > 0) {
            try (Connection conn = DriverManager.getConnection(JDB_URL, JDB_USER, JDB_PWD)) {
                try {
                    conn.setAutoCommit(false);
                    for (User user : userList) {
                        PreparedStatement ps = conn.prepareStatement("insert into User (userId,loginName,loginNameType) values (?, ?, ?)");
                        ps.setObject(1, user.getLoginName());
                        ps.setObject(2, user.getLoginNameType());
                        ps.setObject(3, user.getUserId());
                        ps.execute();
                    }
                    conn.commit();
                } catch (SQLException e) {
                    conn.rollback();
                } finally {
                    conn.setAutoCommit(true);
                }
            }
        }
    }


    /**
     * update sql - with prep stmt
     * @param user
     * @return
     * @throws SQLException
     */
    private boolean updateUser(User user) throws SQLException {
        if (null == user) {
            return false;
        }
        try (Connection conn = DriverManager.getConnection(JDB_URL, JDB_USER, JDB_PWD)) {
            try (PreparedStatement ps = conn.prepareStatement("update User set loginName=?, loginNameType=? where usrId=?")) {
                ps.setObject(1, user.getLoginName());
                ps.setObject(2, user.getLoginNameType());
                ps.setObject(3, user.getUserId());
                return ps.execute();
            }
        }
    }

    /**
     * query sql - with hikari
     * @return
     * @throws SQLException
     */
    private List<User> queryUser() throws SQLException {
        List<User> userList = Lists.newArrayList();
        try (Connection conn = MySqlJdbcDemo.dataSource.getConnection()) {
            try (Statement stmt = conn.createStatement()) {
                try (ResultSet ret = stmt.executeQuery("select * from User")) {
                    while (ret.next()) {
                        long userId = ret.getLong(1);
                        String loginName = ret.getString(2);
                        int loginNameType = ret.getInt(3);
                        System.out.println(String.format("userId %s login name %s type %s", userId, loginName, loginNameType));
                        userList.add(new User(userId, loginName, loginNameType));
                    }
                }
            }
        }
        return userList;
    }


    public static void main(String[] args) throws SQLException {
        //TODO
    }
}
