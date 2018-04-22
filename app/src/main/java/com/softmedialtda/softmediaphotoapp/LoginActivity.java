package com.softmedialtda.softmediaphotoapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.softmedialtda.softmediaphotoapp.models.User;
import org.json.JSONException;
import org.json.JSONObject;
import static com.softmedialtda.softmediaphotoapp.util.Connection.*;
import static com.softmedialtda.softmediaphotoapp.util.Common.*;
import static com.softmedialtda.softmediaphotoapp.util.Constants.*;

public class LoginActivity extends Activity implements OnClickListener {

    String url = DOMAIN+"login";
    String username, password;
    EditText usernameEditText,passwordEditText;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ImageView logo = (ImageView) findViewById(R.id.logoSoftmedia);
        logo.setImageResource(R.drawable.softmedialogo);
        usernameEditText = (EditText) findViewById(R.id.usernameEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
    }

    public void showMessage(String msg){
        Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public void userLoggedIn(JSONObject response){
        User user = convertJsonObjectToUserObject(response);
        if (user.getId() == 0){
            showMessage(getResources().getString(R.string.errorUserObjectIsNull));
            progressDialog.hide();
        }else{
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("user",user);
            startActivity(intent);
        }

    }

    private class LoginAsyncTask extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... params) {
            JSONObject paramaters = new JSONObject();
            try {
                paramaters.accumulate("username", username);
                paramaters.accumulate("password", password);
            }catch (JSONException e) {
                e.printStackTrace();
            }
            return sendPost(url,paramaters);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(LoginActivity.this,
                    R.style.AppTheme_Dark_Dialog);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage(getResources().getString(R.string.Authenticating));
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(String s) {
            if (!s.equals("")) {
                try {
                    JSONObject response = new JSONObject(s);
                    String typeResponse = response.getString("RESPONSE").trim();
                    if (!typeResponse.equals("")){
                        switch (typeResponse){
                            case "2":
                                //usuario logueado satisfactoriamente
                                userLoggedIn(response);
                                break;
                            case "3":
                                showMessage(getResources().getString(R.string.invalidUsernameOrPassword));
                                progressDialog.hide();
                                break;
                            case "4":
                                showMessage(getResources().getString(R.string.requiredFields));
                                progressDialog.hide();
                                break;
                        }
                    }else{
                        showMessage(getResources().getString(R.string.authenticationError));
                        progressDialog.hide();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{
                showMessage(getResources().getString(R.string.authenticationError));
                progressDialog.hide();
            }
        }
    }

    @Override
    public void onClick(View v) {
        //boolean validate = isConnectedToServer(SERVER,15000);
        usernameEditText.setText("BRACHOEMIL@GMAIL.COM");
        passwordEditText.setText("sistemas_157916");
        if (!usernameEditText.getText().toString().trim().equals("")&&!passwordEditText.getText().toString().trim().equals("")) {
            //if (validate == true) {
            switch (v.getId()) {
                case R.id.enterLogin:
                    username = usernameEditText.getText().toString().toUpperCase();
                    password = passwordEditText.getText().toString();
                    new LoginAsyncTask().execute(url);
                    break;
            }
            /*}else{
                showMessage(getResources().getString(R.string.errorConnectionWithServer));
            }*/
        }else{
            showMessage(getResources().getString(R.string.usernameAndPasswordIsEmpty));
        }
    }
}
