package com.suhas.pagemanager;

/**
 * Created by Suhas on 11/25/2017.
 */
public class Insight {
    public String getPost_impressions() {
        return post_impressions;
    }

    public void setPost_impressions(String post_impressions) {
        this.post_impressions = post_impressions;
    }

    public String getPost_consumptions() {
        return post_consumptions;
    }

    public void setPost_consumptions(String post_consumptions) {
        this.post_consumptions = post_consumptions;
    }



    public String getPost_reactions_like_total() {
        return post_reactions_like_total;
    }

    public void setPost_reactions_like_total(String post_reactions_like_total) {
        this.post_reactions_like_total = post_reactions_like_total;
    }

    String post_impressions;
    String post_consumptions;
    String post_reactions_like_total;

    public Insight(){

    }
    public Insight(String post_impressions, String post_consumptions, String post_reactions_like_total){
        this.post_impressions = post_impressions;
        this.post_consumptions = post_consumptions;
        this.post_reactions_like_total = post_reactions_like_total;
    }
}
