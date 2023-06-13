package org.example.sms.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "admin")
public class Admin extends User{
    @Column(name = "username", unique = true, nullable = false)
    private String username;
    @Transient
    private String userType;

    public Admin() {}

    public Admin(String username) {
        super(username);
    }

    public String getUsername() {
        return super.getUserId();
    }

    public void setUsername(String username) {
        super.setUserId(username);
    }
}
