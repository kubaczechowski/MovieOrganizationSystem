package sample.dal;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.Connection;

public class DBConnector {
    private SQLServerDataSource dataSource;

    /**
     * In the constructor we initialize the SQLServerDataSource
     * and set initial values
     */
    public DBConnector() {
        dataSource = new SQLServerDataSource();
        dataSource.setServerName("10.176.111.31");
        dataSource.setUser("CSe20B_8");
        dataSource.setPassword("potatoe2021");
        dataSource.setDatabaseName("MovieRecommendationProject");
    }

    public Connection getConnection() throws SQLServerException
    {
        return dataSource.getConnection();
    }
}
