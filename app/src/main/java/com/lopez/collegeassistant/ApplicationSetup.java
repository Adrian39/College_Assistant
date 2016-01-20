package com.lopez.collegeassistant;

import android.app.Application;
import android.os.Bundle;

import com.facebook.FacebookSdk;
import com.parse.Parse;
import com.parse.ParseFacebookUtils;
import com.parse.ParseInstallation;

/**
 * Created by JacoboAdrian on 1/15/2016.
 */
public class ApplicationSetup extends Application {
    @Override
    public void onCreate(){
        super.onCreate();

        Parse.initialize(this, getString(R.string.parse_application_id), getString(R.string.parse_client_id));
        ParseInstallation.getCurrentInstallation().saveInBackground();
        ParseFacebookUtils.initialize(this);

        FacebookSdk.sdkInitialize(getApplicationContext());


    }
}
