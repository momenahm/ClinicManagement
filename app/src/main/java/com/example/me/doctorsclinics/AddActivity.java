package com.example.me.doctorsclinics;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class AddActivity extends AppCompatActivity {
    EditText mpatientname,mpatientage,mpatientphone,mpatientstatus;
    Button mbtnaddpatient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        mpatientname = (EditText)findViewById(R.id.patientname);
        mpatientage = (EditText)findViewById(R.id.patientage);
        mpatientphone=(EditText)findViewById(R.id.patientphone);
        mpatientstatus = (EditText)findViewById(R.id.patientstatus);
        mbtnaddpatient = (Button)findViewById(R.id.btnaddpatient);
        final String docname = getIntent().getExtras().getString("KEYYY");
        //Toast.makeText(AddActivity.this,docname, Toast.LENGTH_LONG).show();

        mbtnaddpatient.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String patientname = mpatientname.getText().toString();
                String patientage = mpatientage.getText().toString();
                String patientphone = mpatientphone.getText().toString();
                String description = mpatientstatus.getText().toString();
                String method = "addpatient";
                BackgroundTask backgroundTask = new BackgroundTask(AddActivity.this);
                backgroundTask.execute(method,patientname,patientage,patientphone,description,docname);

            }
        });

    }
}
