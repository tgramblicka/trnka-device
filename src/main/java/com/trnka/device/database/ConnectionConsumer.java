package com.trnka.device.database;

import java.sql.SQLException;

@FunctionalInterface
public interface ConnectionConsumer<T> {

    void accept(T t) throws SQLException;

}
