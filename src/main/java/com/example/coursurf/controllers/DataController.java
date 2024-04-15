package com.example.coursurf.controllers;
import org.springframework.beans.factory.annotation.Autowired;
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

    private String buildTrendingQuery(int limit) {
        // Build query to retrieve rows with highest clicks, limited by 'limit'
        return "SELECT * FROM Udemy ORDER BY clicks DESC LIMIT " + limit;
    }


    private String buildQuery(String searchQuery, String provider, Integer rating, int limit) {
        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM Udemy WHERE 1=1");

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
}