package com.trnka.trnkadevice.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbQueries {

    public void selectionTest() {
        ConnectionProvider connectionProvider = new ConnectionProvider();
        connectionProvider.runWithConnection(this::query);
    }

    private void query(final Connection connection) throws SQLException {
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT 'Hello World!'");
        //position result to first
        rs.first();
        System.out.println("DB result:" + rs.getString(1)); //result is "Hello World!"
    }

}
