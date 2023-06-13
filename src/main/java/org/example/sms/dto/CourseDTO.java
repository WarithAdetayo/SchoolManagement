package org.example.sms.dto;

import org.example.sms.util.JsonString;

public class CourseDTO {
    private final String courseShortCode;
    private final String courseName;
    private final String courseDescription;

    public CourseDTO(String courseShortCode, String courseName, String courseDescription) {
        this.courseShortCode = courseShortCode;
        this.courseName = courseName;
        this.courseDescription = courseDescription;
    }

    public String getCourseShortCode() {
        return courseShortCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public String toJsonString() {
        return JsonString.toJsonString(this);
    }

    public static CourseDTO fromJsonString(String jsonStringValue) {
        return JsonString.toObject(jsonStringValue, CourseDTO.class);
    }

    public String toString() {
        return this.toJsonString();
    }
}
