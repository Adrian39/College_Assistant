package com.lopez.collegeassistant;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.content.pm.PackageManager;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;


/**
 * A placeholder fragment containing a simple view.
 */
public class LogInActivityFragment extends Fragment {

    private LoginButton loginButton;
    private TextView mTextTest;
    private FacebookCallback<LoginResult> mCallback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            AccessToken accessToken = loginResult.getAccessToken();
            Profile profile = Profile.getCurrentProfile();
            if (profile != null){
                mTextTest.setText("Welcome " + profile.getName());
            }
        }

        @Override
        public void onCancel() {

        }

        @Override
        public void onError(FacebookException error) {
            Toast.makeText(getContext(), "Error: " + error.toString(), Toast.LENGTH_LONG).show();
        }
    };
    private CallbackManager mCallbackManager;

    public LogInActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
        mCallbackManager = CallbackManager.Factory.create();

        try {
            PackageInfo info = getContext().getPackageManager().getPackageInfo("com.lopez.collegeassistant", PackageManager.GET_SIGNATURES);
            for (android.content.pm.Signature signature : info.signatures){
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash: ", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }


        } catch (PackageManager.NameNotFoundException e){

        } catch (NoSuchAlgorithmException e){

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_log_in, container, false);
        mTextTest = (TextView) view.findViewById(R.id.txtProfileTest);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        loginButton = (LoginButton) view.findViewById(R.id.login_button);
        loginButton.setReadPermissions("user_friends");
        loginButton.setFragment(this);
        loginButton.registerCallback(mCallbackManager, mCallback);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
