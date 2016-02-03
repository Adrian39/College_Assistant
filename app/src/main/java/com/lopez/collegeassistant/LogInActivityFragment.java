package com.lopez.collegeassistant;

import android.app.ProgressDialog;
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
import android.widget.Button;
import android.widget.EditText;
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
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * A placeholder fragment containing a simple view.
 */
public class LogInActivityFragment extends Fragment {

    private LoginButton loginButton;
    private TextView mTextTest;
    private EditText mUserName;
    private EditText mPassword;
    private Button mLogInButton;

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
        mUserName = (EditText) view.findViewById(R.id.txtUserName);
        mPassword = (EditText) view.findViewById(R.id.txtPassword);
        mLogInButton = (Button) view.findViewById(R.id.btnLogIn);
        mLogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validate(mUserName, mPassword)) {
                    final ProgressDialog progressDialog = new ProgressDialog(getContext());
                    progressDialog.setTitle(getString(R.string.please_wait));
                    progressDialog.setMessage(getString(R.string.signing_in));
                    progressDialog.show();
                    ParseUser.logInInBackground(mUserName.getText().toString(), mPassword.getText().toString(), new LogInCallback() {
                        @Override
                        public void done(ParseUser parseUser, ParseException e) {
                            progressDialog.dismiss();
                            if (e != null) {
                                Toast.makeText(getContext(), getText(R.string.error), Toast.LENGTH_SHORT).show();
                            } else {
                                Intent intent = new Intent(getContext(), MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        }
                    });
                } else {
                    Toast.makeText(getContext(), "I can't let you do that, Star Fox", Toast.LENGTH_LONG);
                }
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        loginButton = (LoginButton) view.findViewById(R.id.login_fb_button);
        loginButton.setReadPermissions("public_profile");
        loginButton.setFragment(this);
        loginButton.registerCallback(mCallbackManager, mCallback);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);
    }

    private boolean isEmpty(EditText editText){
        if (editText.getText().toString().trim().length() > 0){
            return false;
        } else {
            return true;
        }
    }

    private boolean validate(EditText userName, EditText password){

        boolean valid = true;

        if (isEmpty(userName)){
            mUserName.setError("Please enter an username");
            valid = false;
        }
        if (isEmpty(password)){
            mPassword.setError("Please enter a Password");
            valid = false;
        }

        if (valid){
            return true;
        } else {
            return false;
        }
    }

}
