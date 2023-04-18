package com.example.model;

public class Acc {
    private String username;
    private String email;
    private String pw;

    public Acc() {
    }

    public Acc(String email, String pw) {
        this.email = email;
        this.pw = pw;
    }

    public Acc(String username, String email, String pw) {
        this.email = email;
        this.username = username;
        this.pw = pw;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    @Override
    public String toString() {
        return "Acc{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", pw='" + pw + '\'' +
                '}';
    }
}
