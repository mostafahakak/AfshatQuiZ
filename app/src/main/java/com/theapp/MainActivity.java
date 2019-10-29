package com.theapp.afshatquiz;

import android.app.ProgressDialog;
import android.content.Intent;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.theapp.afshatquiz.Common.Common;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.theapp.afshatquiz.Model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {


    FirebaseDatabase database;
    DatabaseReference users,userscore;
    CallbackManager callbackManager;
    LoginButton loginButton;
    ProgressDialog mDialog;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        callbackManager = CallbackManager.Factory.create();

        database = FirebaseDatabase.getInstance();
        users = database.getReference("Users");

        userscore = database.getReference("Scores");


        loginButton = (LoginButton) findViewById(R.id.login_button);

        loginButton.setReadPermissions(Arrays.asList("public_profile"));
        loginButton.registerCallback(callbackManager, new FacebookCallback <LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                mDialog = new ProgressDialog(MainActivity.this);
                mDialog.setMessage("Estana Shewaya....");
                mDialog.show();
                mDialog.setCancelable(false);

                AccessToken accessTokens = loginResult.getAccessToken();

                GraphRequest request = GraphRequest.newMeRequest(accessTokens, new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {



                        try {

                            final String userID = (String) object.get("id");
                            final String userName = (String) object.get("name");
                            users.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (!dataSnapshot.child(userName + userID).exists()) {

                                        User newUser = new User();
                                        newUser.setName(userName);
                                        newUser.setEmail("");
                                        newUser.setUserID(userID);
                                        newUser.setImage("https://graph.facebook.com/" + userID + "/picture?type=large");

                                        users.child(userName + userID).setValue(newUser);

                                        Common.CurrentUser = newUser;


                                        userscore.child(Common.CurrentUser.getName()+Common.CurrentUser.getUserID()).child("Name").setValue(Common.CurrentUser.getName());
                                        userscore.child(Common.CurrentUser.getName()+Common.CurrentUser.getUserID()).child("Image").setValue(Common.CurrentUser.getImage());
                                        userscore.child(Common.CurrentUser.getName()+Common.CurrentUser.getUserID()).child("AllTimeQuestions").setValue("0");
                                        userscore.child(Common.CurrentUser.getName()+Common.CurrentUser.getUserID()).child("LastScore").setValue("0");
                                        userscore.child(Common.CurrentUser.getName()+Common.CurrentUser.getUserID()).child("Score").setValue("0");
                                        userscore.child(Common.CurrentUser.getName()+Common.CurrentUser.getUserID()).child("PlayingTimes").setValue("0");
                                        userscore.child(Common.CurrentUser.getName()+Common.CurrentUser.getUserID()).child("WiningRatio").setValue("100%");

                                        Intent start = new Intent(MainActivity.this, Home.class);
                                        startActivity(start);
                                        mDialog.dismiss();
                                        finish();

                                    } else if (dataSnapshot.child(userName + userID).exists()) {
                                        User newUser = dataSnapshot.getValue(User.class);
                                        newUser.setName(userName);
                                        newUser.setEmail("");
                                        newUser.setUserID(userID);
                                        newUser.setImage("https://graph.facebook.com/" + userID + "/picture?type=large");
                                        Intent start = new Intent(MainActivity.this, Home.class);
                                        Common.CurrentUser = newUser;
                                        startActivity(start);
                                        mDialog.dismiss();

                                        finish();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Log.d("response", response.toString());

                        //    getData(object);

                    }
                });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email");
                request.setParameters(parameters);
                request.executeAsync();

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        if (AccessToken.getCurrentAccessToken() != null) {
            final Intent start = new Intent(MainActivity.this, Home.class);




            String name = Profile.getCurrentProfile().getName();
            String id = Profile.getCurrentProfile().getId();


            User newUser = new User();
            newUser.setName(name);
            newUser.setUserID(id);
            newUser.setImage("https://graph.facebook.com/" + id + "/picture?type=large");
            Common.CurrentUser = newUser;


            startActivity(start);
            finish();
        }


    }



}