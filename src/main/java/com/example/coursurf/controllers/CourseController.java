package com.example.coursurf.controllers;

import com.example.coursurf.model.Course;
import com.example.coursurf.services.CourseService;

// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;

import java.util.List;

// @Controller
@RestController
@RequestMapping("/api")
public class CourseController {

    // @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    private final CourseService courseService;

    /**
     * Fetch data based on filtering criteria.
     *
     * @param searchQuery (optional) Search keyword to filter by title.
     * @param provider    (optional) Provider name to filter by.
     * @param rating      (optional) Minimum rating to filter by.
     * @param limit       (optional) Maximum number of results to return (default: 10).
     * @return List of objects representing filtered data.
     */
    @GetMapping("/filter")
    public List<Course> filterCourses(
            @RequestParam(required = false) String searchQuery,
            @RequestParam(required = false) String provider,
            @RequestParam(required = false) Integer rating,
            @RequestParam(required = false, defaultValue = "10") int limit) {

        return courseService.filterCourses(searchQuery, provider, rating, limit);
    }

    /**
     * Get trending data based on click counts.
     *
     * @param limit (optional) Maximum number of trending items to return (default: 10).
     * @return List of objects representing trending data.
    //  */
    // @GetMapping("/getTrending")
    // public String getTrendingCourses(Model model, @RequestParam(required = false, defaultValue = "10") int limit) {
    //     List<Course> trendingCourses = courseService.getTrendingCourses(limit);
    //     model.addAttribute("courses", trendingCourses); // Add the list of courses to the model
    //     return "trendingCourses"; // Return the name of the Thymeleaf template
    // }
    @GetMapping("/getTrending")
    public List<Course> getTrendingCourses(@RequestParam(required = false, defaultValue = "10") int limit) {
        List<Course> trendingCourses = courseService.getTrendingCourses(limit);
        return trendingCourses;
    }
    /**
     * Increment the click count for a specific course title.
     *
     * @param title Title of the course to increment clicks for.
     * @return ResponseEntity with success message if click count is updated, otherwise error message.
     */
    @GetMapping("/clicked")
    public ResponseEntity<String> incrementClick(@RequestParam String title) {
        if (courseService.incrementClick(title)) {
            return ResponseEntity.ok("Click count updated for title: " + title);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Title not found: " + title);
        }
    }

}
