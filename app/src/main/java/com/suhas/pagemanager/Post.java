package com.suhas.pagemanager;

/**
 * Created by Suhas on 11/23/2017.
 */

public class Post {
    private String name; // name of the post
    //private int imageResourceId; // image of the post
    private String desciption; // description of the page.
    public static final Post[] posts = {
            new Post("first post", "first description"),
            new Post("second post", "second description"),
            new Post("third post", "third description"),
            new Post("fourth post", "fourth description"),
            new Post("fifth post", "fifth description"),
            new Post("sixth post", "sixth description"),
            new Post("seventh post", "seventh description"),
            new Post("eighth post", "eighth description")
    };
    private Post(String name, String desciption){
        this.name = name;
        this.desciption = desciption;
    }
    public String getName(){
        return name;
    }
    public String getDescription(){
        return desciption;
    }
}
