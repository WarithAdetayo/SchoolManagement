package org.example.sms.entity;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import jakarta.persistence.*;
import org.hibernate.annotations.NaturalId;

import java.util.Date;

@Entity
@Table(name = "course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private int courseId;
//    @NaturalId
    @Column(name = "course_short_code")
    private String courseShortCode;
    @Column(name = "course_name")
    private String courseName;
    @Column(name = "course_description")
    private String courseDescription;

    @Column(name = "date_created")
    private Date dateCreated;
    @Column(name = "date_modified")
    private Date dateModified;

    public Course() {}

    public Course(String courseShortCode) {
        this.courseShortCode = courseShortCode;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseShortCode() {
        return courseShortCode;
    }

    public void setCourseShortCode(String courseShortCode) {
        this.courseShortCode = courseShortCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateModified() {
        return dateModified;
    }

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    @Override
    public String toString() {
        return
            MoreObjects.toStringHelper(this.getClass())
                    .add("Course Code", this.courseShortCode)
                    .add("Name", this.courseName)
                    .toString();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.courseShortCode);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || this.getClass() != other.getClass()) return  false;
        Course c = (Course) other;
        return Objects.equal(this.courseShortCode, ((Course) other).courseShortCode);
    }
}
