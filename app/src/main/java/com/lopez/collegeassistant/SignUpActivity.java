package com.lopez.collegeassistant;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpActivity extends AppCompatActivity {

    private EditText mUserName;
    private EditText mEmail;
    private EditText mPassword;
    private EditText mPassConf;
    private Button mSignUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mUserName = (EditText) findViewById(R.id.txtUserSignUp);
        mEmail = (EditText) findViewById(R.id.txtEmailSignUp);
        mPassword = (EditText) findViewById(R.id.txtPasswordSignUp);
        mPassConf = (EditText) findViewById(R.id.txtConfPassSignUp);


        mSignUpButton = (Button) findViewById(R.id.btnSignUp);
        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Validation
                if (validate(mUserName, mEmail, mPassword, mPassConf)){
                    final ProgressDialog progressDialog = new ProgressDialog(SignUpActivity.this);
                    progressDialog.setTitle(getString(R.string.please_wait));
                    progressDialog.setMessage(getString(R.string.signing_in));
                    progressDialog.show();

                    ParseUser parseUser = new ParseUser();
                    parseUser.setUsername(mUserName.getText().toString());
                    parseUser.setEmail(mEmail.getText().toString());
                    parseUser.setPassword(mPassword.getText().toString());
                    parseUser.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            progressDialog.dismiss();
                            if (e != null){
                                Toast.makeText(SignUpActivity.this, getText(R.string.error), Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Intent intent = new Intent(SignUpActivity.this, LogInActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        }
                    });

                    Toast.makeText(SignUpActivity.this, "Seems legit...", Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(SignUpActivity.this, "I can't let you do that, Star Fox", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private boolean isEmpty(EditText editText){
        if (editText.getText().toString().trim().length() > 0){
            return false;
        } else {
            return true;
        }
    }

    private boolean isMatching(EditText editText, EditText editText2){
        if (editText.getText().toString().equals(editText2.getText().toString())){
            return true;
        } else{
            return false;
        }
    }

    private boolean validate(EditText userName, EditText email, EditText password, EditText passConf){

        boolean valid = true;

        if (isEmpty(userName)){
            mUserName.setError("Please enter an username");
            valid = false;
        }
        if (isEmpty(email)){
            mEmail.setError("Please enter an E-mail address");
            valid = false;
        }
        if (isEmpty(password)){
            mPassword.setError("Please enter a Password");
            valid = false;
        }
        if (isEmpty(passConf)){
            mPassConf.setError("Please confirm Password");
            valid = false;
        }
        if (!isMatching(password, passConf)){
            mPassConf.setError("Password does not match");
            valid = false;
        }
        if (valid){
            return true;
        } else {
            return false;
        }
    }

}
