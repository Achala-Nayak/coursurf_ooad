package com.example.coursurf.services;

import com.example.coursurf.model.Course;
import java.sql.*;
import java.util.List;

import javax.sql.DataSource;

import java.util.ArrayList;


import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;


import com.example.coursurf.config.DbProperties;
import com.example.coursurf.utils.myUtils;

@Service
public class CourseServiceImplement implements CourseService{

    // @Autowired
    // private DbProperties dbProperties;

    @Autowired
    private DataSource dataSource;

    @Override
    public Course getCourseInfo(String name) {
        Course course = null;

        // String jdbcUrl = "jdbc:mysql://" + dbProperties.getHost() + ":" + dbProperties.getPort() + "/" + dbProperties.getDatabase();
        try (Connection connection = dataSource.getConnection()) {
            String query = "SELECT * FROM courses WHERE name = ?"; // Modify this query based on your database schema

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, name); // Set the name parameter in the query

                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    course = new Course();
                    course.setTitle(resultSet.getString("title"));
                    course.setType(resultSet.getString("type"));
                    course.setProvider(resultSet.getString("provider"));
                    course.setTaughtBy(resultSet.getString("taughtBy"));
                    course.setLink(resultSet.getString("link"));
                    course.setUrl(resultSet.getString("url"));
                    course.setSideCard(resultSet.getString("sideCard"));
                    course.setOverview(resultSet.getString("overview"));
                    course.setRatings(resultSet.getFloat("ratings"));
                    course.setNoOfRatings(resultSet.getInt("noOfRatings"));
                    course.setClicks(resultSet.getInt("clicks"));
                    course.setDuration(resultSet.getString("duration"));
                    course.setLanguage(resultSet.getString("language"));
                    course.setFreeOrPaid(resultSet.getString("freeOrPaid"));
                    course.setBeginnerStatus(resultSet.getString("beginnerStatus"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception properly in a real application
        }

        return course;
    }

    @Override
    public List<Course> filterCourses(String searchQuery, String provider, Float rating, int limit) {
        // Implement database access logic here using DataSource and PreparedStatement
        List<Course> data = new ArrayList<>();

        // String jdbcUrl = "jdbc:mysql://" + dbProperties.getHost() + ":" + dbProperties.getPort() + "/" + dbProperties.getDatabase();
        try (Connection connection = dataSource.getConnection()) {
            String query = myUtils.buildQuery(searchQuery, provider, rating, limit);

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    Course course = new Course();
                    course.setTitle(resultSet.getString("title"));
                    course.setType(resultSet.getString("type"));
                    course.setProvider(resultSet.getString("provider"));
                    course.setTaughtBy(resultSet.getString("taught_by"));
                    course.setLink(resultSet.getString("Link"));
                    course.setUrl(resultSet.getString("url"));
                    course.setSideCard(resultSet.getString("side_card"));
                    // course.setOverview(resultSet.getString("overview"));
                    course.setRatings(resultSet.getFloat("ratings"));
                    course.setNoOfRatings(resultSet.getInt("no_of_ratings"));
                    course.setClicks(resultSet.getInt("clicks"));
                    course.setDuration(resultSet.getString("duration"));
                    course.setLanguage(resultSet.getString("language"));
                    course.setFreeOrPaid(resultSet.getString("free_or_Paid"));
                    course.setBeginnerStatus(resultSet.getString("beginner_status"));
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

        // String jdbcUrl = "jdbc:mysql://" + dbProperties.getHost() + ":" + dbProperties.getPort() + "/" + dbProperties.getDatabase();
        try (Connection connection = dataSource.getConnection()) {
            String query = myUtils.buildTrendingQuery(limit);

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    Course course = new Course();
                    course.setTitle(resultSet.getString("title"));
                    course.setProvider(resultSet.getString("Provider"));
                    course.setRatings(resultSet.getInt("ratings"));
                    course.setClicks(resultSet.getInt("clicks"));
                    course.setLink(resultSet.getString("Link"));
                    course.setUrl(resultSet.getString("url"));
                    trendingData.add(course);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception properly in a real application
        }

        return trendingData;
    }

    
    
    public boolean incrementClick(String title) {
        // String jdbcUrl = "jdbc:mysql://" + dbProperties.getHost() + ":" + dbProperties.getPort() + "/" + dbProperties.getDatabase();
    
        try (Connection connection = dataSource.getConnection()) {
            String updateQuery = "UPDATE ProcessedCourses SET clicks = clicks + 1 WHERE title = ?";
    
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
