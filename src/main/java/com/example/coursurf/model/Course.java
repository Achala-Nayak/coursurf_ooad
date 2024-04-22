package com.example.coursurf.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Course {
    private String title;
    private String type;
    private String provider;
    private String taughtBy;
    private String link;
    private String url;
    private String sideCard;
    private String overview;
    private float ratings;
    private int noOfRatings;
    private int clicks;
    private String duration;
    private String language;
    private String freeOrPaid;
    private String beginnerStatus;

    // Constructor (optional, depending on your use case)
    public Course() {
        // Default constructor
    }

    // Getters and Setters for each field

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getTaughtBy() {
        return taughtBy;
    }

    public void setTaughtBy(String taughtBy) {
        this.taughtBy = taughtBy;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSideCard() {
        return sideCard;
    }

    public void setSideCard(String sideCard) {
        this.sideCard = sideCard;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public float getRatings() {
        return ratings;
    }

    public void setRatings(float ratings) {
        this.ratings = ratings;
    }

    public int getNoOfRatings() {
        return noOfRatings;
    }

    public void setNoOfRatings(int noOfRatings) {
        this.noOfRatings = noOfRatings;
    }

    public int getClicks() {
        return clicks;
    }

    public void setClicks(int clicks) {
        this.clicks = clicks;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getFreeOrPaid() {
        return freeOrPaid;
    }

    public void setFreeOrPaid(String freeOrPaid) {
        this.freeOrPaid = freeOrPaid;
    }

    public String getBeginnerStatus() {
        return beginnerStatus;
    }

    public void setBeginnerStatus(String beginnerStatus) {
        this.beginnerStatus = beginnerStatus;
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
            node.put("link", resultSet.getInt("Link"));

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
