package com.Y_LAB.homework.util.db;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

class ConnectionToDatabaseTest {

    @Test
    void getConnection() throws SQLException {
        Connection connection = ConnectionToDatabase.getConnection();

        assertThat(connection).isNotNull();
        assertThat(connection.isClosed()).isEqualTo(false);
        assertThat(connection.isReadOnly()).isEqualTo(false);
    }
}