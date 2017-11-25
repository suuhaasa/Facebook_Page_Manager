package com.suhas.pagemanager;

import java.util.Date;

/**
 * Created by Suhas on 11/23/2017.
 */

public class Post {
    public void setId(String id) {
        this.id = id;
    }

    private String id; // id of the post

    public void setMessage(String message) {
        this.message = message;
    }

    //private int imageResourceId; // image of the post
    private String message; // description of the page.

    public void setDate(String date) {
        this.date = date;
    }

    private String date; // post created date

    public static final Post[] posts = {
            new Post("first post", "first description", new Date(123124).toString()),
            new Post("second post", "second description", new Date(123124).toString()),
            new Post("third post", "third description", new Date(123124).toString()),
            new Post("fourth post", "fourth description", new Date(123124).toString()),
            new Post("fifth post", "fifth description", new Date(123124).toString()),
            new Post("sixth post", "sixth description", new Date(123124).toString()),
            new Post("seventh post", "seventh description", new Date(123124).toString()),
            new Post("eighth post", "eighth description", new Date(123124).toString())
    };
    public Post(String name, String message, String date){
        this.id = name;
        this.message = message;
        this.date = date;
    }
    public Post(){
        this.message = "this post doesnt contain a message";
    }
    public String getId(){
        return id;
    }
    public String getMessage(){
        return message;
    }
    public String getDate(){return date;}

}
