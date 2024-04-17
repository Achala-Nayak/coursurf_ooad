package com.example.coursurf.services;

import com.example.coursurf.model.Course;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;


import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;


import com.example.coursurf.config.DbProperties;
import com.example.coursurf.utils.myUtils;

@Service
public class CourseServiceImplement implements CourseService{

    @Autowired
    private DbProperties dbProperties;
    

    @Override
    public List<Course> filterCourses(String searchQuery, String provider, Integer rating, int limit) {
        // Implement database access logic here using DataSource and PreparedStatement
        List<Course> data = new ArrayList<>();

        String jdbcUrl = "jdbc:mysql://" + dbProperties.getHost() + ":" + dbProperties.getPort() + "/" + dbProperties.getDatabase();
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbProperties.getUser(), dbProperties.getPassword())) {
            String query = myUtils.buildQuery(searchQuery, provider, rating, limit);

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    Course course = new Course();
                    course.setTitle(resultSet.getString("title"));
                    course.setProvider(resultSet.getString("provider"));
                    course.setRatings(resultSet.getInt("ratings"));
                    course.setClicks(resultSet.getInt("clicks"));
                    data.add(course);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception properly in a real application
        }

        return data;
    }

    
    public List<Course> getTrendingCourses(int limit) {
        // Implement database access logic here using DataSource and PreparedStatement
        List<Course> trendingData = new ArrayList<>();

        String jdbcUrl = "jdbc:mysql://" + dbProperties.getHost() + ":" + dbProperties.getPort() + "/" + dbProperties.getDatabase();
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbProperties.getUser(), dbProperties.getPassword())) {
            String query = myUtils.buildTrendingQuery(limit);

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    Course course = new Course();
                    course.setTitle(resultSet.getString("title"));
                    course.setProvider(resultSet.getString("Provider"));
                    course.setRatings(resultSet.getInt("ratings"));
                    course.setClicks(resultSet.getInt("clicks"));
                    trendingData.add(course);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception properly in a real application
        }

        return trendingData;
    }

    
    
    public boolean incrementClick(String title) {
        String jdbcUrl = "jdbc:mysql://" + dbProperties.getHost() + ":" + dbProperties.getPort() + "/" + dbProperties.getDatabase();
    
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbProperties.getUser(), dbProperties.getPassword())) {
            String updateQuery = "UPDATE Courses SET clicks = clicks + 1 WHERE title = ?";
    
            try (PreparedStatement statement = connection.prepareStatement(updateQuery)) {
                statement.setString(1, title);
                int rowsUpdated = statement.executeUpdate();
    
                // Check if rows were updated
                return rowsUpdated > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception properly in a real application
            return false; // Return false if an error occurs
        }
    }

}
