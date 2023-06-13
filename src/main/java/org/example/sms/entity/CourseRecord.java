package org.example.sms.entity;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "course_record")
public class CourseRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_record_id")
    private long courseRecordId;
    @ManyToOne(targetEntity = Course.class)
    @JoinColumn(name = "course")
    private Course course;
    @ManyToOne(targetEntity = Student.class)
    @JoinColumn(name = "student")
    private Student student;            // Student Taking the course

    @ManyToOne(targetEntity = Teacher.class)
    @JoinColumn(name = "teacher")
    private Teacher teacher;            // Teacher Taking the course
    @Column(name = "first_ca_score")
    private int firstCAScore;
    @Column(name = "second_ca_score")
    private int secondCAScore;
    @Column(name = "exam_score")
    private int examScore;

    @Column(name = "date_created")
    private Date dateCreated;
    @Column(name = "date_modified")
    private Date dateModified;

    public CourseRecord() {}

    public long getCourseRecordId() {
        return courseRecordId;
    }

    public void setCourseRecordId(long courseRecordId) {
        this.courseRecordId = courseRecordId;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public int getFirstCAScore() {
        return firstCAScore;
    }

    public void setFirstCAScore(int firstCAScore) {
        this.firstCAScore = firstCAScore;
    }

    public int getSecondCAScore() {
        return secondCAScore;
    }

    public void setSecondCAScore(int secondCAScore) {
        this.secondCAScore = secondCAScore;
    }

    public int getExamScore() {
        return examScore;
    }

    public void setExamScore(int examScore) {
        this.examScore = examScore;
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
                    .add("Student", this.student)
                    .add("Course", this.course)
                    .add("First CA", this.firstCAScore)
                    .add("Second CA", this.secondCAScore)
                    .add("Exam", this.examScore)
                    .toString();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.courseRecordId);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        CourseRecord cr = (CourseRecord) other;
        return Objects.equal(this.courseRecordId, cr.courseRecordId);
    }
}
