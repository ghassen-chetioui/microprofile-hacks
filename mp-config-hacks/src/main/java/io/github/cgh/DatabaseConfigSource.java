package io.github.cgh;

import org.eclipse.microprofile.config.spi.ConfigSource;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class DatabaseConfigSource implements ConfigSource {

    private DataSource dataSource;

    public DatabaseConfigSource() {
        try {
            dataSource = (DataSource) new InitialContext().lookup("java:jboss/datasources/ExampleDS");
        } catch (NamingException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Map<String, String> getProperties() {
        return exec("SELECT NAME, VALUE FROM CONFIG", Collections.emptyList(), configs -> {
            Map<String, String> properties = new HashMap<>();
            try {
                while (configs.next()) {
                    properties.put(configs.getString(0), configs.getString(1));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return properties;
        });

    }

    @Override
    public String getValue(String propertyName) {
        return exec("SELECT VALUE FROM CONFIG WHERE NAME = ?", Collections.singletonList(propertyName),
                value -> {
                    try {
                        if (value.next()) {
                            return value.getString(1);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return null;
                });
    }

    @Override
    public String getName() {
        return DatabaseConfigSource.class.getSimpleName();
    }

    private <T> T exec(String sql, List<String> params, Function<ResultSet, T> func) {
        Connection connection = null;
        PreparedStatement query = null;
        ResultSet rs = null;
        try {
            connection = dataSource.getConnection();
            query = connection.prepareStatement(sql);
            for (int i = 0; i < params.size(); i++) {
                query.setString(i+1, params.get(i));
            }
            rs = query.executeQuery();
            return func.apply(rs);
        } catch (final SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (query != null)
                    query.close();
                if (connection != null)
                    connection.close();
            } catch (Exception e) {
                // Getting tired from those exceptions
            }
        }
        return null;
    }

}