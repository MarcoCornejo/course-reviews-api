package com.teamtreehouse.core;

import com.teamtreehouse.course.Course;
import com.teamtreehouse.course.CourseRepository;
import com.teamtreehouse.review.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Component
public class DatabaseLoader implements ApplicationRunner {
    private final CourseRepository courses;

    @Autowired
    public DatabaseLoader(CourseRepository courses) {
        this.courses = courses;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Course course = new Course("Java Basics", "https://teamtreehouse.com/library/java-basics");
        course.addReview(new Review(3, "You are a dork!!!"));
        courses.save(course);
        String[] templates = {
                "Up and Running with %s",
                "%s Basics",
                "%s for Beginners",
                "%s Under the Hood"
        };
        String[] buzzwords = {
                "Spring REST Data",
                "Java 11",
                "Scala",
                "Groovy",
                "Hibernate"
        };

        List<Course> bunchOfCourses = new ArrayList<>();
        IntStream.range(0, 100)
                .forEach(i -> {
                    String template = templates[i % templates.length];
                    String buzzword = buzzwords[i % buzzwords.length];
                    String title = String.format(template, buzzword);
                    Course c = new Course(title, "http://www.example.com");
                    c.addReview(new Review(i % 5, String.format("Moar %s please!!!", buzzword)));
                    bunchOfCourses.add(c);
                });
        courses.saveAll(bunchOfCourses);
    }
}
