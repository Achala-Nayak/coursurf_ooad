package com.example.coursurf.services;

import com.example.coursurf.model.Course;
import java.util.List;
// import java.sql.*;
// import java.util.ArrayList;


public interface CourseService {
    List<Course> filterCourses(String searchQuery, String provider, Integer rating, int limit);
    List<Course> getTrendingCourses(int limit);
    boolean incrementClick(String title);
}