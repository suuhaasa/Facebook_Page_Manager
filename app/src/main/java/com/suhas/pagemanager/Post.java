package com.suhas.pagemanager;

import java.util.Date;

/**
 * Created by Suhas on 11/23/2017.
 */

public class Post {
    private String id; // id of the post
    //private int imageResourceId; // image of the post
    private String message; // description of the page.
    private Date date; // post created date
    public enum POST_TYPE {PUBLISHED, UNPUBLISHED;}

    public static final Post[] posts = {
            new Post("first post", "first description", new Date(123124)),
            new Post("second post", "second description", new Date(123124)),
            new Post("third post", "third description", new Date(123124)),
            new Post("fourth post", "fourth description", new Date(123124)),
            new Post("fifth post", "fifth description", new Date(123124)),
            new Post("sixth post", "sixth description", new Date(123124)),
            new Post("seventh post", "seventh description", new Date(123124)),
            new Post("eighth post", "eighth description", new Date(123124))
    };
    public Post(String name, String desciption, Date date){
        this.id = name;
        this.message = desciption;
        this.date = date;
    }
    public String getName(){
        return id;
    }
    public String getDescription(){
        return message;
    }
    public Date getDate(){return date;}
}
