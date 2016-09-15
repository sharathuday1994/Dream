package com.nihalpradeep.carapp;

/* Login Page. User can Login using existing account, create new account or use app as a guest */

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nihalpradeep.carapp.serverPitShop.QueryBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class LoginActivity extends AppCompatActivity {

    private Button bSignIn, bDialogDismiss;
    private TextView tvSkip, tvDialogText, tvRegister;
    private Dialog dialogLogin;
    private View llLoginForm;
    private EditText etUserNameDialog,etUserPasswordDialog;
    //private View vDialogMessage,vDialogSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        tvRegister = (TextView) findViewById(R.id.login_register_text);
        bSignIn = (Button) findViewById(R.id.login_signin);
        tvSkip = (TextView) findViewById(R.id.login_skip);
        llLoginForm = findViewById(R.id.login_form);
        etUserNameDialog = (EditText) findViewById(R.id.emailid_login);
        etUserPasswordDialog = (EditText) findViewById(R.id.password_login);
        dialogLogin = new Dialog(LoginActivity.this);
        dialogLogin.setContentView(R.layout.login_signin_dialog);

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,Register_Activity.class));
            }
        });

        tvDialogText = (TextView) dialogLogin.findViewById(R.id.login_dialog_text);
        bDialogDismiss = (Button) dialogLogin.findViewById(R.id.login_dialog_dismiss_button);

       /* bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,Register_Activity.class));
                return;
            }
        });*/
        tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            }
        });

        llLoginForm.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        bSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // vDialogMessage.setVisibility(View.GONE);
                //  vDialogSignIn.setVisibility(View.VISIBLE);
                //  dialogLogin.show();
                attemptLogin();
            }
        });
        bDialogDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogLogin.dismiss();
            }
        });
        llLoginForm.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                View view = LoginActivity.this.getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                return false;
            }

        });






    }
    public void attemptLogin(){
        String emailid,password;
        emailid = etUserNameDialog.getText().toString();
        password = etUserPasswordDialog.getText().toString();
        View focusView = null;
        boolean cancel = false;

        if(TextUtils.isEmpty(emailid)){
            etUserNameDialog.setError(getString(R.string.field_required));
            cancel = true;
            focusView = etUserNameDialog;
        }else{
            if(!emailid.contains("@")){
                etUserNameDialog.setError(getString(R.string.invalid_email));
                cancel = true;
                focusView = etUserNameDialog;
            }
        }
        if(TextUtils.isEmpty(password)){
            etUserPasswordDialog.setError(getString(R.string.field_required));
            cancel = true;
            focusView = etUserPasswordDialog;
        }

        if(cancel){
            focusView.requestFocus();
        }
        else{
            new UserLoginTask(emailid,password).execute();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class UserLoginTask extends AsyncTask<Void, Void, String> {

        private final String mEmail;
        private final String mPassword;
        private String error = "";
        private final String login_url = getString(R.string.php_url) + "login.php";


        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected String doInBackground(Void... params) {

            HttpURLConnection httpURLConnection = null;
            InputStream IS = null;
            int temp;

            try {
                // Simulate network access.

                URL url = new URL(login_url);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setConnectTimeout(10000);
                httpURLConnection.setReadTimeout(10000);

                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));

                ContentValues parameters = new ContentValues();
                parameters.put("emailid", mEmail);
                parameters.put("password", mPassword);
                String data = QueryBuilder.getQuery(parameters);

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();

                IS = httpURLConnection.getInputStream();

                while ((temp = IS.read()) != -1) {
                    error += (char) temp;
                }
                return error;
            }  catch (MalformedURLException e) {
                e.printStackTrace();
                error = "Sorry! Cannot Currently Render Service";
            } catch (IOException e) {
                e.printStackTrace();
                error = "Sorry! Cannot Currently Render Service";
            } finally {
                if (httpURLConnection != null)

                {
                    httpURLConnection.disconnect();
                }
                try {
                    if (IS != null) {
                        IS.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


            return error;
        }


        protected void onPostExecute(String success) {


            try {

                JSONObject root = new JSONObject(success);
                JSONObject user_data = root.getJSONObject("user_data");
                String check = user_data.getString("registered");
                if(check.equals("Successful")) {
                    dialogLogin.setTitle(getString(R.string.login_success));
                    tvDialogText.setText(getString(R.string.login_success_message));
                    bDialogDismiss.setVisibility(View.GONE);
                    dialogLogin.show();
                    Thread.sleep(8000);
                    //dialogLogin.dismiss();
                    Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                    i.putExtra("emailid",mEmail);
                    startActivity(i);
                }
                else
                {
                    dialogLogin.setTitle(getString(R.string.login_failed));
                    tvDialogText.setText(getString(R.string.password_incorrect));
                    bDialogDismiss.setVisibility(View.VISIBLE);
                    dialogLogin.show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}

