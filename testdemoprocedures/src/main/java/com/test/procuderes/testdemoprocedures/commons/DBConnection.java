package com.test.procuderes.testdemoprocedures.commons;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

@Component
public class DBConnection {

    private static String dataSource;
    @Value("${spring.datasource.driverClassName}")
    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    private static String db_url;
    @Value("${spring.datasource.url}")
    public void setDb_url(String db_url) {
        this.db_url = db_url;
    }

    private static String db_username;
    @Value("${spring.datasource.username}")
    public void setDb_username(String db_username) {
        this.db_username = db_username;
    }

    private static String db_password;
    @Value("${spring.datasource.password}")
    public void setDb_password(String db_password) {
        this.db_password = db_password;
    }

    public static Connection getConnection() {
        Connection con = null;
        try {
            // load the Driver Class
            Class.forName(dataSource);

            // create the connection now
            con = DriverManager.getConnection( db_url, db_username, db_password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }
}
