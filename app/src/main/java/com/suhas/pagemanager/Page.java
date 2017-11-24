package com.suhas.pagemanager;

/**
 * Created by Suhas on 11/23/2017.
 */

public class Page {
    private String name;
    private String id;
    private String access_token;
    private String about;

    Page(String name, String id, String access_token, String about){
        this.name = name;
        this.id = id;
        this.access_token = access_token;
        this.about = about;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccess_token() {
        return name;
    }

    public void setAccess_token(String name) {
        this.name = name;
    }

    public String getAbout() {
        return name;
    }

    public void setAbout(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
