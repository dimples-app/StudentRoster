package com.example.SpringBootSecurityDemo.jwt;

public class UsernameAndPasswordAuthenticationRequest {

    private String userName;
    private String passWord;

    /**
     * Empty constructor
     */
    public UsernameAndPasswordAuthenticationRequest() {

    }

    /**
     * Generate getters and setters
     */

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
