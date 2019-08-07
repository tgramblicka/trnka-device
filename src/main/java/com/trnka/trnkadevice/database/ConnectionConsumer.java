package com.trnka.trnkadevice.database;

import java.sql.SQLException;

@FunctionalInterface
public interface ConnectionConsumer<T> {

    void accept(T t) throws SQLException;

}
