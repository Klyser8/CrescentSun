package it.crescentsun.crescentcore.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import it.crescentsun.crescentcore.CrescentCore;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {

    private final HikariDataSource dataSource;
    private final CrescentCore crescentCore = CrescentCore.getInstance();
    private final List<String> tableNames = new ArrayList<>();
    public DatabaseManager(String host, int port, String database, String username, String password, int maxPoolSize) {

        // Configure and initialize the HikariDataSource
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://" + host + ":" + port + "/" + database);
        config.setUsername(username);
        config.setPassword(password);

        config.setMaximumPoolSize(maxPoolSize); // Adjust the pool size as needed

        dataSource = new HikariDataSource(config);
    }

    public Connection getConnection() throws SQLException {
        Connection connection = dataSource.getConnection();
        if (connection == null) {
            throw new SQLException("Could not establish a connection to the database");
        }
        return connection;
    }

    public void disconnect() {
        if (dataSource != null) {
            dataSource.close();
        }
    }
}
