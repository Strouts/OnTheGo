package com.example.chahat.anotode;

/**
 * Created by chahat on 4/9/16.
 */
public class Inbox {
    private String Name, Email, DOB;

    public Inbox() {
    }

    public Inbox(String Name ,String Email, String DOB) {
        this.Name = Name;
        this.Email = Email;
        this.DOB = DOB;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }
}
