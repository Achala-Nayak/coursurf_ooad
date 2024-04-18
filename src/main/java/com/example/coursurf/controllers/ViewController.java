package com.example.coursurf.controllers;

import com.example.coursurf.model.Course;
import com.example.coursurf.services.CourseService;


// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.Model;

import java.util.List;

@Controller
public class ViewController {

    // @Autowired
    private final CourseService courseService;

    // Constructor injection of CourseService
    public ViewController(CourseService courseService) {
        this.courseService = courseService;
    }
    /**
     * Fetch data based on filtering criteria.
     *
     * @param searchQuery (optional) Search keyword to filter by title.
     * @param provider    (optional) Provider name to filter by.
     * @param rating      (optional) Minimum rating to filter by.
     * @param limit       (optional) Maximum number of results to return (default: 10).
     * @return List of objects representing filtered data.
     */
    @GetMapping("/courses")
    public ModelAndView filterCourses(
        @RequestParam(required = false) String searchQuery,
        @RequestParam(required = false) String provider,
        @RequestParam(required = false) Integer rating,
        @RequestParam(required = false, defaultValue = "10") int limit,
        Model model) {

    // Retrieve filtered courses from service
    List<Course> filteredCourses = courseService.filterCourses(searchQuery, provider, rating, limit);

    // Add filtered courses to model
    model.addAttribute("courses", filteredCourses);

    // Return ModelAndView with view name and model
    ModelAndView modelAndView = new ModelAndView("courses");
    return modelAndView;
}

    /**
     * Get trending data based on click counts.
     *
     * @param limit (optional) Maximum number of trending items to return (default: 10).
     * @return List of objects representing trending data.
     */
    @GetMapping("/getTrending")
    public String getTrendingCourses(Model model, @RequestParam(required = false, defaultValue = "10") int limit) {
        List<Course> trendingCourses = courseService.getTrendingCourses(limit);
        model.addAttribute("courses", trendingCourses); // Add the list of courses to the model
        return "trendingCourses"; // Return the name of the Thymeleaf template
    }

}
