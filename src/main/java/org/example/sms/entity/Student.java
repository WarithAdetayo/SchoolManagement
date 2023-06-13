package org.example.sms.entity;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import jakarta.persistence.*;
import org.hibernate.annotations.NaturalId;

import java.util.Date;

@Entity
@Table(name = "student")
public class Student extends User {
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "date_of_birth")
    private Date dateOfBirth;
    @ManyToOne(targetEntity = Teacher.class)
    @JoinColumn(name = "personal_guide")
    private Teacher personalGuide;
    @Transient
    private String userType;

    public Student() {}

    public Student(String matricNumber) {
        super(matricNumber);
    }

    public String getMatricNumber() {
        return super.getUserId();
    }

    public void setMatricNumber(String matricNumber) {
        super.setUserId(matricNumber);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Teacher getPersonalGuide() {
        return personalGuide;
    }

    public void setPersonalGuide(Teacher personalGuide) {
        this.personalGuide = personalGuide;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String toString() {
        return
            MoreObjects.toStringHelper(this.getClass())
                    .add("Matric", this.getUserId())
                    .add("First Name", this.firstName)
                    .add("Last Name", this.lastName)
                    .toString();
    }

//    @Override
//    public boolean equals(Object other) {
//        if (this == other) return true;
//        if (other == null || getClass() != other.getClass()) return false;
//        Student student = (Student) other;
//        return Objects.equal(this.getUserId(), student.getUserId());
//    }
}
