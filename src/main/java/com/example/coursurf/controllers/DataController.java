package com.example.coursurf.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.coursurf.config.DbProperties;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@RestController
public class DataController {

    @Autowired
    private DbProperties dbProperties;

    @GetMapping("/fetch")
    public List<String> fetchData() {
        List<String> data = new ArrayList<>();

        String jdbcUrl = "jdbc:mysql://" + dbProperties.getHost() + ":" + dbProperties.getPort() + "/" + dbProperties.getDatabase();
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbProperties.getUser(), dbProperties.getPassword());
             Statement statement = connection.createStatement()) {

            String query = "SELECT * FROM Udemy LIMIT 10";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                // Assuming your_table has a 'name' column
                data.add(resultSet.getString("title"));
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception properly in a real application
        }

        return data;
    }
}