package com.suhas.pagemanager;

import android.os.Bundle;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Suhas on 11/23/2017.
 */

public class GraphAPIHelper {

    public static interface OnPagesFetchListener{
        void onPagesFetchSuccess(List<Page> pages);
        void onPagesFetchFailure(String message);
    }

    public static interface OnPostsFetchListener{
        void onPostsFetchSuccess(List<Post> posts);
        void onPostsFetchFailure(String message);
    }

    public static GraphRequest getFeedGraphRequest(String pageId, GraphRequest.Callback callback) {

        return new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/" + pageId + "/feed?include_hidden=true&fields=message,created_time,is_hidden,insights.metric(post_impressions)",
                null,
                HttpMethod.GET,
                callback
        );
    }

    public static GraphRequest getPromotablePostsGraphRequest(String pageId, GraphRequest.Callback callback) {

        return new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/" + pageId + "/promotable_posts?is_published=false&include_hidden=true&fields=message,created_time,is_hidden,insights.metric(post_impressions)",
                null,
                HttpMethod.GET,
                callback
        );
    }

    public static GraphRequest getNewPostGraphRequest(AccessToken accessToken, Bundle params, String pageId, GraphRequest.Callback callback) {

        return new GraphRequest(

                accessToken,
                "/" + pageId + "/feed",
                params,
                HttpMethod.POST,
                callback
        );

    }

    /**
     * @param pageID
     * @param onPostsFetchListener
     */
    public static void fetchPublishedPosts(String pageID, final OnPostsFetchListener onPostsFetchListener){
        GraphRequest graphRequest = new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/" + pageID + "/feed?include_hidden=true&fields=message,created_time,is_hidden,insights.metric(post_impressions)",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                     /* handle the result */
                    List<Post> posts = new ArrayList<Post>();
                    JSONObject graphObject = response.getJSONObject();
                    if (response.getError() == null) {
                        try {
                            JSONArray dataObject = graphObject.getJSONArray("data");
                            //int summary = graphObject.getInt("summary");
                            for (int i = 0; i < dataObject.length(); i++) {
                                String message = dataObject.getJSONObject(i).getString("message");
                                String id = dataObject.getJSONObject(i).getString("id");
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault());
                                String date = dataObject.getJSONObject(i).getString("created_time");
                                //String about = dataObject.getJSONObject(i).getString("about");
                                Post currentPost = new Post(message, id, sdf.parse(date));
                                posts.add(currentPost);
                            }
                            onPostsFetchListener.onPostsFetchSuccess(posts);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch(ParseException p){
                            p.printStackTrace();
                        }
                    } else {
                        onPostsFetchListener.onPostsFetchFailure(response.getError().getErrorMessage());
                    }
                    }
                });

        graphRequest.executeAsync();
    }

    /**
     * @param pageID
     * @param onPostsFetchListener
     */
    public static void fetchUnPublishedPosts(String pageID, final OnPostsFetchListener onPostsFetchListener){
        GraphRequest graphRequest = new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/" + pageID + "/feed?include_hidden=true&fields=message,created_time,is_hidden,insights.metric(post_impressions)",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                     /* handle the result */
                        List<Post> posts = new ArrayList<Post>();
                        JSONObject graphObject = response.getJSONObject();
                        if (response.getError() == null) {
                            try {
                                JSONArray dataObject = graphObject.getJSONArray("data");
                                //int summary = graphObject.getInt("summary");
                                for (int i = 0; i < dataObject.length(); i++) {
                                    String message = dataObject.getJSONObject(i).getString("message");
                                    String id = dataObject.getJSONObject(i).getString("id");
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault());
                                    String date = dataObject.getJSONObject(i).getString("created_time");
                                    //String about = dataObject.getJSONObject(i).getString("about");
                                    Post currentPost = new Post(message, id, sdf.parse(date));
                                    posts.add(currentPost);
                                }
                                onPostsFetchListener.onPostsFetchSuccess(posts);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch(ParseException p){
                                p.printStackTrace();
                            }
                        } else {
                            onPostsFetchListener.onPostsFetchFailure(response.getError().getErrorMessage());
                        }
                    }
                });

        graphRequest.executeAsync();
    }
    /**
     * Retrieves list of pages from the graph api endpoint,
     * parses the result and callsback the parameter callback with list of pages,
     * @param onPagesFetchListener Callback called after parsing
     */
    public static void fetchPages(final OnPagesFetchListener onPagesFetchListener) {
        GraphRequest graphRequest = new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/me/accounts",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                     /* handle the result */
                        //parsePageFetchResponse(response);
                    List<Page> pages = new ArrayList<Page>();
                    JSONObject graphObject = response.getJSONObject();
                    if (response.getError() == null) {
                        try {
                            JSONArray dataObject = graphObject.getJSONArray("data");
                            //int summary = graphObject.getInt("summary");
                            for (int i = 0; i < dataObject.length(); i++) {
                                String name = dataObject.getJSONObject(i).getString("name");
                                String id = dataObject.getJSONObject(i).getString("id");
                                String access_token = dataObject.getJSONObject(i).getString("access_token");
                                //String about = dataObject.getJSONObject(i).getString("about");
                                Page currentPage = new Page(name, id, access_token, "");
                                pages.add(currentPage);
                            }
                            onPagesFetchListener.onPagesFetchSuccess(pages);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        onPagesFetchListener.onPagesFetchFailure(response.getError().getErrorMessage());
                    }
                }
            });

            graphRequest.executeAsync();
    }
}

