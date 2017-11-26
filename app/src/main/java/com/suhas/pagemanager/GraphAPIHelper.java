package com.suhas.pagemanager;

import android.os.Bundle;
import android.util.Log;
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

    public interface OnPostInsightsFetchListener {
        void onPostInsightsFetchSuccess(Insight insight);
        void onPostInsightsFetchFailure(String message);
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

    public static void fetchPostInsights(String postID, String access_token, final OnPostInsightsFetchListener onPostInsightsFetchListener){
        String accessToken = access_token;
        AccessToken currentToken = AccessToken.getCurrentAccessToken();
        AccessToken page_access_token = new AccessToken(accessToken, currentToken.getApplicationId(),
                currentToken.getUserId(), currentToken.getPermissions(),
                currentToken.getDeclinedPermissions(), currentToken.getSource(),
                currentToken.getExpires(), currentToken.getLastRefresh());
        GraphRequest graphRequest = new GraphRequest(
                page_access_token,
                "/" + postID + "/insights/post_reactions_like_total",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                     /* handle the result */
                        Log.v("response is", response.toString());

                        List<Post> posts = new ArrayList<Post>();
                        JSONObject graphObject = response.getJSONObject();
                        if (response.getError() == null) {
                            try {
                                JSONArray dataObject = graphObject.getJSONArray("data");
                                Log.v("hello", dataObject.toString());
                                //int summary = graphObject.getInt("summary");
                                Insight currentInsight = new Insight();
                                for (int i = 0; i < dataObject.length(); i++) {
                                    JSONObject dayViews = dataObject.getJSONObject(i);
                                    JSONArray jsonArrayValues = dayViews.getJSONArray("values");

                                    if(dataObject.getJSONObject(i).getString("name") == "post_impressions")
                                        currentInsight.setPost_impressions(dataObject.getJSONObject(i).getJSONArray("values").getJSONObject(0).getString("value"));
                                    if(dataObject.getJSONObject(i).getString("name") == "post_consumptions")
                                        currentInsight.setPost_consumptions(dataObject.getJSONObject(i).getJSONArray("values").getJSONObject(0).getString("value"));
                                    if(dataObject.getJSONObject(i).getString("name") == "post_reactions_like_total")
                                        currentInsight.setPost_reactions_like_total(dataObject.getJSONObject(i).getJSONArray("values").getJSONObject(0).getString("value"));

                                }
                                onPostInsightsFetchListener.onPostInsightsFetchSuccess(currentInsight);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            onPostInsightsFetchListener.onPostInsightsFetchFailure(response.getError().getErrorMessage());
                        }
                    }
                });

        graphRequest.executeAsync();
    }
    /**
     * @param page
     * @param onPostsFetchListener
     */
    public static void fetchPublishedPosts(Page page, final OnPostsFetchListener onPostsFetchListener){
        String accessToken = page.getAccess_token();
        AccessToken currentToken = AccessToken.getCurrentAccessToken();
        AccessToken page_access_token = new AccessToken(accessToken, currentToken.getApplicationId(),
                currentToken.getUserId(), currentToken.getPermissions(),
                currentToken.getDeclinedPermissions(), currentToken.getSource(),
                currentToken.getExpires(), currentToken.getLastRefresh());

        GraphRequest graphRequest = new GraphRequest(
                page_access_token,
                "/" + page.getId() + "/feed?include_hidden=true&fields=message,created_time,is_hidden,insights.metric(post_impressions)",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                     /* handle the result */
                        Log.v("response is", response.toString());

                        List<Post> posts = new ArrayList<Post>();
                    JSONObject graphObject = response.getJSONObject();
                    if (response.getError() == null) {
                        try {
                            JSONArray dataObject = graphObject.getJSONArray("data");
                            //int summary = graphObject.getInt("summary");
                            for (int i = 0; i < dataObject.length(); i++) {
                                Post currentPost = new Post();

                                if(dataObject.getJSONObject(i).has("message"))
                                currentPost.setMessage(dataObject.getJSONObject(i).getString("message"));
                                if(dataObject.getJSONObject(i).has("id"))
                                    currentPost.setId(dataObject.getJSONObject(i).getString("id"));
                                if(dataObject.getJSONObject(i).has("created_time"))
                                    currentPost.setDate(dataObject.getJSONObject(i).getString("created_time"));

                                posts.add(currentPost);
                            }
                            onPostsFetchListener.onPostsFetchSuccess(posts);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        onPostsFetchListener.onPostsFetchFailure(response.getError().getErrorMessage());
                    }
                    }
                });

        graphRequest.executeAsync();
    }

    /**
     * @param //pageID
     * @param onPostsFetchListener
     */
    public static void fetchUnPublishedPosts(Page page, final OnPostsFetchListener onPostsFetchListener){
        String accessToken = page.getAccess_token();
        AccessToken currentToken = AccessToken.getCurrentAccessToken();
        AccessToken page_access_token = new AccessToken(accessToken, currentToken.getApplicationId(),
                currentToken.getUserId(), currentToken.getPermissions(),
                currentToken.getDeclinedPermissions(), currentToken.getSource(),
                currentToken.getExpires(), currentToken.getLastRefresh());

        GraphRequest graphRequest = new GraphRequest(
                page_access_token,
                "/" + page.getId() + "/promotable_posts?is_published=false&include_hidden=true&fields=message,created_time,is_hidden,insights.metric(post_impressions)",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                     /* handle the result */
                        List<Post> posts = new ArrayList<Post>();
                        JSONObject graphObject = response.getJSONObject();
                        Log.v("response is", graphObject.toString());
                        if (response.getError() == null) {
                            try {
                                JSONArray dataObject = graphObject.getJSONArray("data");
                                //int summary = graphObject.getInt("summary");
                                for (int i = 0; i < dataObject.length(); i++) {
                                    Post currentPost = new Post();

                                    if(dataObject.getJSONObject(i).has("message"))
                                        currentPost.setMessage(dataObject.getJSONObject(i).getString("message"));
                                    if(dataObject.getJSONObject(i).has("id"))
                                        currentPost.setId(dataObject.getJSONObject(i).getString("id"));
                                    if(dataObject.getJSONObject(i).has("created_time"))
                                        currentPost.setDate(dataObject.getJSONObject(i).getString("created_time"));
                                    posts.add(currentPost);
                                }
                                onPostsFetchListener.onPostsFetchSuccess(posts);

                            } catch (JSONException e) {
                                e.printStackTrace();
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

