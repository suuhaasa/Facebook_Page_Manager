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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Suhas on 11/23/2017.
 */

public class GraphAPIHelper {

    public static interface OnPagesFetchListener{
        void onSuccess(List<Page> pages);
        void onFailure(String message);
    }

    private OnPagesFetchListener onPagesFetchListener;

    public void attachPagesFetchListener(OnPagesFetchListener onPagesFetchListener){
        this.onPagesFetchListener = onPagesFetchListener;
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

    public static GraphRequest getMeAccoountsGraphRequest(GraphRequest.Callback callback) {
        return new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/me/accounts",
                null,
                HttpMethod.GET,
                callback
        );
    }
    public static void fetchPages(final OnPagesFetchListener e) {
        GraphRequest graphRequest = GraphAPIHelper.getMeAccoountsGraphRequest(new GraphRequest.Callback() {
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
                        e.onSuccess(pages);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    e.onFailure(response.getError().getErrorMessage());
                }
            }
        });

        graphRequest.executeAsync();
    }
}

