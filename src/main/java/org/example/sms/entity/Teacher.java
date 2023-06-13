package org.example.sms.entity;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import jakarta.persistence.*;
import org.hibernate.annotations.NaturalId;

@Entity
@Table(name = "teacher")
public class Teacher extends User {
    @Column(name = "name")
    private String name;
    @Transient
    private String userType;

    public Teacher() {}

    public Teacher(String employmentId) {
        super(employmentId);
    }

    public String getEmploymentId() {
        return super.getUserId();
    }

    public void setEmploymentId(String employmentId) {
        super.setUserId(employmentId);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return
            MoreObjects.toStringHelper(this.getClass())
                    .add("Employment ID", super.getUserId())
                    .add("Name", this.name)
                    .toString();
    }

//    @Override
//    public boolean equals(Object other) {
//        if (this == other) return true;
//        if (other == null || getClass() != other.getClass()) return false;
//        Teacher teacher = (Teacher) other;
//        return Objects.equal(this.getEmploymentId(), teacher.getEmploymentId());
//    }
}
