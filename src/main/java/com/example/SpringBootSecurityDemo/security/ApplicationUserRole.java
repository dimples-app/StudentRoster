package com.example.SpringBootSecurityDemo.security;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Enum with application user role
 */

public enum ApplicationUserRole {
    STUDENT(Sets.newHashSet()),
    ADMIN(Sets.newHashSet(ApplicationUserPermission.COURSE_READ,
            ApplicationUserPermission.COURSE_WRITE,
            ApplicationUserPermission.STUDENT_READ,
            ApplicationUserPermission.STUDENT_WRITE)),
    ADMINTRAINEE(Sets.newHashSet(ApplicationUserPermission.COURSE_READ,
          ApplicationUserPermission.STUDENT_READ));

    private final Set<ApplicationUserPermission> permissions;

    /**
     *  Set Application user permission
     * @param permissions
     */
    ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }

    /**
     * Get Application user permission
     * @return
     */
    public Set<ApplicationUserPermission> getPermissions() {
        return permissions;
    }

    /**
     * 
     * @return
     */
    
    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority>  permissions = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());

        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissions;
    }

   
}
