package com.example.chahat.anotode;

/**
 * Created by chahat on 10/9/16.
 */
public class Permission
{
    private String catogary, reciever,status,template,sender;

    public Permission() {
    }

    public Permission(String catogary,String reciever,String status,String template)
    {
        this.catogary=catogary;
        this.reciever=reciever;
        this.status=status;
        this.template=template;

    }

    public Permission(String catogary,String reciever,String status,String template,String sender)
    {
        this.catogary=catogary;
        this.reciever=reciever;
        this.status=status;
        this.template=template;
        this.sender=sender;

    }


    public String getCatogary() {
        return catogary;
    }

    public void setReciever(String name) {
        this.reciever = name;
    }

    public String getStatus (){
        return status;
    }

    public void setStatus(String cid) {
        this.status = cid;
    }

    public String getReciever() {
        return reciever;
    }

    public void setTemplate(String lname) {
        this.template = lname;
    }

    public String getTemplate() {
        return template;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
