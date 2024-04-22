package com.example.coursurf.controllers;

import com.example.coursurf.model.Course;
import com.example.coursurf.services.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RestController
@RequestMapping("/api")
@Scope("singleton")
public class CourseController {

    private final CourseService courseService;
    private static CourseController instance;

    @Autowired
    private CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    public static synchronized CourseController getInstance(CourseService courseService) {
        if (instance == null) {
            instance = new CourseController(courseService);
        }
        return instance;
    }

    @GetMapping("/filter")
    public List<Course> filterCourses(
            @RequestParam(required = false) String searchQuery,
            @RequestParam(required = false) String provider,
            @RequestParam(required = false) Float rating,
            @RequestParam(required = false, defaultValue = "10") int limit) {

        return courseService.filterCourses(searchQuery, provider, rating, limit);
    }

    @GetMapping("/getTrending")
    public List<Course> getTrendingCourses(@RequestParam(required = false, defaultValue = "10") int limit) {
        return courseService.getTrendingCourses(limit);
    }

    @GetMapping("/clicked")
    public ResponseEntity<String> incrementClick(@RequestParam String title) {
        if (courseService.incrementClick(title)) {
            return ResponseEntity.ok("Click count updated for title: " + title);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Title not found: " + title);
        }
    }
}
