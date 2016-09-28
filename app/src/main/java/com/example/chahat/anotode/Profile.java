package com.example.chahat.anotode;

/**
 * Created by chahat on 9/9/16.
 */
public class Profile
{
    private String FirstName, LastName, CollegeId;

    public Profile() {
    }

    public Profile(String firstName,String lastName)
    {
        this.FirstName=firstName;
        this.LastName=lastName;
    }

    public Profile(String fName ,String lname, String cid) {
        this.FirstName = fName;
        this.LastName = lname;
        this.CollegeId = cid;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String name) {
        this.FirstName = name;
    }

    public String getCollegeId (){
        return CollegeId;
    }

    public void setCollegeId(String cid) {
        this.CollegeId = cid;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lname) {
        this.LastName = lname;
    }
}
