package com.suhas.pagemanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private TextView info;
    private LoginButton loginButton;

    private CallbackManager callbackManager;
    private String first_name = "";
    private String last_name = "";
    private String email = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email", "public_profile");
        //loginButton.setPublishPermissions("manage_pages", "publish_pages", "publish_actions", "ads_management");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                String userid = loginResult.getAccessToken().getUserId();
                GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        //displayUserInfo(object);
                        try {
                                first_name = object.getString("first_name");
                                last_name = object.getString("last_name");
                                email = object.getString("email");
                            }
                            catch(JSONException e){
                                e.printStackTrace();
                            }
                        Intent intent = new Intent(getApplicationContext(), HomePageActivity.class);
                        intent.putExtra("Name", first_name + " " + last_name);
                        intent.putExtra("Email", email);
                        startActivity(intent);
                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "first_name, last_name, email, id");
                graphRequest.setParameters(parameters);
                graphRequest.executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
        if(AccessToken.getCurrentAccessToken() != null){
            Intent intent = new Intent(this.getApplicationContext(), HomePageActivity.class);
            startActivity(intent);
        }
    }
//    public void displayUserInfo(JSONObject object){
//        String first_name = "", last_name = "", email = "", id = "";
//        try {
//            first_name = object.getString("first_name");
//            last_name = object.getString("last_name");
//            email = object.getString("email");
//            id = object.getString("id");
//        }
//        catch(JSONException e){
//            e.printStackTrace();
//        }
//        TextView tv_name, tv_id, tv_email;
//        tv_name = (TextView) findViewById(R.id.TV_name);
//        tv_id = (TextView) findViewById(R.id.TV_id);
//        tv_email = (TextView) findViewById(R.id.TV_email);
//        tv_name.setText(first_name + " " + last_name);
//        tv_email.setText("Email: " + email);
//        tv_id.setText("ID: " + id);
//        Intent intent = new Intent(this.getApplicationContext(), HomePageActivity.class);
//        startActivity(intent);
//    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
