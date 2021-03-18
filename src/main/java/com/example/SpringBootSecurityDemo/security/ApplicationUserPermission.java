package com.example.SpringBootSecurityDemo.security;

/**
 * Enum for different permissions
 */
public enum ApplicationUserPermission {
    STUDENT_READ("student:read"),
    STUDENT_WRITE("student:write"),
    COURSE_READ("course:read"),
    COURSE_WRITE("course:write");

    private final String permission;

    /**
     * Application user permission as per enum
     * @param permission
     */

    ApplicationUserPermission(String permission) {
        this.permission = permission;
    }

    /**
     * Retrieve user permission
     * @return
     */
    public String getPermission() {
        return permission;
    }
}
