package com.example.me.doctorsclinics;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
       EditText mloginname,mloginpassword;
    Button mbtnlogin;
    ProgressDialog progressDialog;
    AlertDialog alertDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);
        mloginname     = (EditText)findViewById(R.id.loginpersonalname);
        mloginpassword = (EditText)findViewById(R.id.loginpassword);
        mbtnlogin      = (Button)findViewById(R.id.btnlogin);

        mbtnlogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String login_name = mloginname.getText().toString();
                String login_pass = mloginpassword.getText().toString();
                if (!TextUtils.isEmpty(login_name)&& !TextUtils.isEmpty(login_pass))
                {
                    if (isOnline())
                    {
                        String method = "login";
                        Backgroundlogin backgroundlogin = new Backgroundlogin(LoginActivity.this);
                        backgroundlogin.execute(method, login_name, login_pass);
                        // startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    }else {
                        Toast.makeText(getApplicationContext(),"No Internet...",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                }
                else
                {
                    Toast.makeText(LoginActivity.this,"Please Enter Your Name and Password",Toast.LENGTH_LONG).show();
                }

            }
        });


    }
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public void Signup(View view) {
        if (isOnline())
        {
            Intent Reg = new Intent(LoginActivity.this,RegisterActivity.class);
            startActivity(Reg);
        }else
        {
            Toast.makeText(getApplicationContext(),"No Internet...",Toast.LENGTH_LONG).show();
            startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));

        }

    }

    class Backgroundlogin extends AsyncTask<String,Void,String>
    {

           Context ctx;

        public Backgroundlogin(Context ctx) {

            this.ctx = ctx;
        }


        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(ctx);
            alertDialog = new AlertDialog.Builder(ctx).create();
            alertDialog.setTitle("Login Information.......");
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... params) {
            String url_login          = "https://m123456m.000webhostapp.com/getdocname.php";
            String login_name = params[1];
            String login_password = params[2];

            try {

                URL url = new URL(url_login);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data =
                        URLEncoder.encode("login_name","UTF-8")+"="+ URLEncoder.encode(login_name,"UTF-8")+"&"+
                                URLEncoder.encode("login_password","UTF-8")+"="+ URLEncoder.encode(login_password,"UTF-8");

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                StringBuilder stringBuilder = new StringBuilder();
                String res = null;
                String response = "";
                String line = "";
                while ((line = bufferedReader.readLine())!=null)
                {
                    response+= line;
                    stringBuilder.append(line + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }



            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            try {
                JSONObject jsonObject = new JSONObject(s);
                String message = jsonObject.getString("message");
                String  doctorname = jsonObject.getString("doctorname");

                  alertDialog.setMessage(message);
                  alertDialog.show();
              //  Toast.makeText(ctx,doctorname,Toast.LENGTH_LONG).show();
                 Intent mainIntent1 = new Intent(ctx,MainActivity.class);
                 mainIntent1.putExtra("KEYY",doctorname);
                 ctx.startActivity(mainIntent1);



                //Toast.makeText(ctx, doctorname, Toast.LENGTH_LONG).show();

            } catch (Exception e) {
                alertDialog.setMessage("Login Failed ...... please Signup");
                alertDialog.show();
               // Toast.makeText(ctx, "Login Failed ...... please Signup", Toast.LENGTH_LONG).show();

            }

        }
    }

}

