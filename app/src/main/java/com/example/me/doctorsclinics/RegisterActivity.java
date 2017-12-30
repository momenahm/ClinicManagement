package com.example.me.doctorsclinics;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    EditText mEditPersonal, mEditPassword, mEditmatch, mEditemail, mEditPhone;
    Button mbtnRegiter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mEditPersonal = (EditText) findViewById(R.id.editpersonalname);
        mEditPassword = (EditText) findViewById(R.id.passwordedit);
        mEditmatch = (EditText) findViewById(R.id.matchpassword);
        mEditemail = (EditText) findViewById(R.id.editemail);
        mEditPhone = (EditText) findViewById(R.id.editphone);
        mbtnRegiter = (Button) findViewById(R.id.btnRegister);

        mbtnRegiter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mEditPersonal.getText().toString();
                String password = mEditPassword.getText().toString();
                String matchpassword = mEditmatch.getText().toString();
                String email = mEditemail.getText().toString();
                String phone = mEditPhone.getText().toString();



                if (!password.equals(matchpassword)) {
                    Toast.makeText(RegisterActivity.this, "Password Not Match ", Toast.LENGTH_LONG).show();
                    mEditPersonal.setText("");
                    mEditPassword.setText("");
                    mEditmatch.setText("");
                } else {
                    if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(matchpassword) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(phone)) {
                        String method = "register";
                        BackgroundTask backgroundTask = new BackgroundTask(RegisterActivity.this);
                        backgroundTask.execute(method, name, password, email, phone);
                    } else {
                        Toast.makeText(getApplicationContext(), "Please Fill All Field......", Toast.LENGTH_LONG).show();
                    }
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
}
