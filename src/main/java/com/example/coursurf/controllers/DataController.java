package com.example.coursurf.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.coursurf.config.DbProperties;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@RestController
public class DataController {

    @Autowired
    private DbProperties dbProperties;

    /**
     * Fetch data based on filtering criteria.
     *
     * @param searchQuery (optional) Search keyword to filter by title.
     * @param provider    (optional) Provider name to filter by.
     * @param rating      (optional) Minimum rating to filter by.
     * @param limit       (optional) Maximum number of results to return (default: 10).
     * @return List of objects representing filtered data.
     */
    @GetMapping("/api/filter")
    public List<Object> fetchData(
            @RequestParam(required = false) String searchQuery,
            @RequestParam(required = false) String provider,
            @RequestParam(required = false) Integer rating,
            @RequestParam(required = false, defaultValue = "10") int limit) {

        List<Object> data = new ArrayList<>();

        String jdbcUrl = "jdbc:mysql://" + dbProperties.getHost() + ":" + dbProperties.getPort() + "/" + dbProperties.getDatabase();
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbProperties.getUser(), dbProperties.getPassword())) {
            String query = buildQuery(searchQuery, provider, rating, limit);

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    data.add(resultSetToJson(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception properly in a real application
        }

        return data;
    }

    /**
     * Get trending data based on click counts.
     *
     * @param limit (optional) Maximum number of trending items to return (default: 10).
     * @return List of objects representing trending data.
     */
    @GetMapping("/api/getTrending")
    public List<Object> getTrendingData(@RequestParam(required = false, defaultValue = "10") int limit) {
        List<Object> trendingData = new ArrayList<>();

        String jdbcUrl = "jdbc:mysql://" + dbProperties.getHost() + ":" + dbProperties.getPort() + "/" + dbProperties.getDatabase();
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbProperties.getUser(), dbProperties.getPassword())) {
            String query = buildTrendingQuery(limit);

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    trendingData.add(resultSetToJson(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception properly in a real application
        }

        return trendingData;
    }

    /**
     * Increment the click count for a specific course title.
     *
     * @param title Title of the course to increment clicks for.
     * @return ResponseEntity with success message if click count is updated, otherwise error message.
     */
    @GetMapping("/api/clicked")
    public ResponseEntity<String> incrementClick(@RequestParam String title) {
        String jdbcUrl = "jdbc:mysql://" + dbProperties.getHost() + ":" + dbProperties.getPort() + "/" + dbProperties.getDatabase();

        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbProperties.getUser(), dbProperties.getPassword())) {
            String updateQuery = "UPDATE Courses SET clicks = clicks + 1 WHERE title = ?";

            try (PreparedStatement statement = connection.prepareStatement(updateQuery)) {
                statement.setString(1, title);
                int rowsUpdated = statement.executeUpdate();

                if (rowsUpdated > 0) {
                    return ResponseEntity.ok("Click count updated for title: " + title);
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Title not found: " + title);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception properly in a real application
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update click count for title: " + title);
        }
    }

    /**
     * Build SQL query for retrieving trending data.
     *
     * @param limit Maximum number of rows to return.
     * @return SQL query string for retrieving trending data.
     */
    private String buildTrendingQuery(int limit) {
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
    private String buildQuery(String searchQuery, String provider, Integer rating, int limit) {
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
    private Object resultSetToJson(ResultSet resultSet) throws SQLException {
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

    public void setDbProperties(DbProperties dbProperties2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setDbProperties'");
    }
}
