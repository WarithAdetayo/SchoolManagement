package org.example.sms.util;

import org.example.sms.entity.Admin;
import org.example.sms.entity.Student;
import org.example.sms.entity.User;

public class Permission {

    public static boolean userIsSignedIn(User user) {
        return user != null;
    }

    public static boolean userIsNotSignedIn(User user) {
        return !userIsSignedIn(user);
    }

    public static boolean userIsAdmin(User user) {
        if (userIsSignedIn(user))
            return (user instanceof Admin);
        return true;
    }

    public static boolean userIsNotAdmin(User user) {
        return !userIsAdmin(user);
    }

    public static boolean userIsStudent(User user) {
        if (userIsSignedIn(user))
            return (user instanceof Student);
        return true;
    }

    public static boolean userIsNotStudent(User user) {
        return !userIsStudent(user);
    }
}
