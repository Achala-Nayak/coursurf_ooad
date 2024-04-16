package com.example.coursurf.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class myUtils {
        /**
     * Build SQL query for retrieving trending data.
     *
     * @param limit Maximum number of rows to return.
     * @return SQL query string for retrieving trending data.
     */
    public static String buildTrendingQuery(int limit) {
        return "SELECT * FROM Courses ORDER BY clicks DESC LIMIT " + limit;
    }

    /**
     * Build SQL query based on filtering parameters.
     *
     * @param searchQuery (optional) Search keyword to filter by title.
     * @param provider    (optional) Provider name to filter by.
     * @param rating      (optional) Minimum rating to filter by.
     * @param limit       Maximum number of rows to return.
     * @return SQL query string based on provided filtering parameters.
     */
    public static String buildQuery(String searchQuery, String provider, Integer rating, int limit) {
        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM Courses WHERE 1=1");

        if (searchQuery != null && !searchQuery.isEmpty()) {
            queryBuilder.append(" AND title LIKE '%" + searchQuery + "%'");
        }

        if (provider != null && !provider.isEmpty()) {
            queryBuilder.append(" AND Provider LIKE '%" + provider + "%'");
        }

        if (rating != null && rating > 0) {
            queryBuilder.append(" AND ratings > " + rating);
        }

        queryBuilder.append(" LIMIT " + limit);

        return queryBuilder.toString();
    }

    /**
     * Convert ResultSet row to JSON object.
     *
     * @param resultSet ResultSet containing row data.
     * @return JSON object representing the ResultSet row.
     * @throws SQLException If an SQL exception occurs.
     */
    public static Object resultSetToJson(ResultSet resultSet) throws SQLException {
        ObjectMapper objectMapper = new ObjectMapper();
        int columnCount = resultSet.getMetaData().getColumnCount();
        ObjectNode node = objectMapper.createObjectNode();

        for (int i = 1; i <= columnCount; i++) {
            String columnName = resultSet.getMetaData().getColumnName(i);
            Object columnValue = resultSet.getObject(i);
            node.put(columnName, columnValue.toString());
        }

        return objectMapper.convertValue(node, Object.class);
    }
    
}
