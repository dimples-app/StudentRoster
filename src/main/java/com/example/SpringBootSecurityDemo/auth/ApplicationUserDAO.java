package com.example.SpringBootSecurityDemo.auth;

import java.util.Optional;

public interface ApplicationUserDAO {

    public Optional<ApplicationUser> selectApplicationByUserNAme(String userName);

}
