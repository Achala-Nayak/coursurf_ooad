package com.example.coursurf.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Course {
    private String title;
    private String provider;
    private int ratings;
    private int clicks;

    // Default constructor
    public Course() {
    }

    // Constructor with all fields
    public Course(String title, String provider, int ratings, int clicks) {
        this.title = title;
        this.provider = provider;
        this.ratings = ratings;
        this.clicks = clicks;
    }

    // Getter and setter methods for all fields
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public int getRatings() {
        return ratings;
    }

    public void setRatings(int ratings) {
        this.ratings = ratings;
    }

    public int getClicks() {
        return clicks;
    }

    public void setClicks(int clicks) {
        this.clicks = clicks;
    }
    
    public static void add(List<Course> data, ResultSet resultSet) throws SQLException {
        ObjectMapper objectMapper = new ObjectMapper();

        while (resultSet.next()) {
            // Convert current row of ResultSet to JSON object
            ObjectNode node = objectMapper.createObjectNode();
            node.put("title", resultSet.getString("title"));
            node.put("provider", resultSet.getString("provider"));
            node.put("ratings", resultSet.getInt("ratings"));
            node.put("clicks", resultSet.getInt("clicks"));

            // Create Course object from JSON representation
            Course course = convertJsonToCourse(node);

            // Add Course object to the list
            data.add(course);
        }
    }

    // Helper method to convert JSON object to Course object
    private static Course convertJsonToCourse(ObjectNode node) {
        Course course = new Course();
        course.setTitle(node.get("title").asText());
        course.setProvider(node.get("provider").asText());
        course.setRatings(node.get("ratings").asInt());
        course.setClicks(node.get("clicks").asInt());
        return course;
    }
    // toString method for representing Course object as a string
    @Override
    public String toString() {
        return "Course{" +
                "title='" + title + '\'' +
                ", provider='" + provider + '\'' +
                ", ratings=" + ratings +
                ", clicks=" + clicks +
                '}';
    }
}