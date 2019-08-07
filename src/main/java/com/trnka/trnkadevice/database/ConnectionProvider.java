package com.trnka.trnkadevice.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionProvider {

    public void runWithConnection(ConnectionConsumer<Connection> consumer) {
        try (Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost/", "root", "raspberry")) {
            consumer.accept(conn);
        } catch (SQLException e) {
            System.out.println("An error occured during the resource connection");
            e.printStackTrace();
        }

    }

}
