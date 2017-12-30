package com.example.me.doctorsclinics;

import org.json.JSONArray;
import org.json.JSONException;
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
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.widget.Toast;

public class DisplayPatient extends AppCompatActivity {

    JSONArray jarr;
    PatientAdapter patientAdapter;
    RecyclerView recyclerView;
    LayoutManager layoutManager;
    List<Patients> patientdata;
    JSONObject jobj;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_patient);
        String doctorname = getIntent().getExtras().getString("VIEW");
        DisplayTask displayTask = new DisplayTask(this);
        displayTask.execute(doctorname);
      //  Toast.makeText(getApplicationContext(),displayTask.toString(),Toast.LENGTH_LONG).show();
        recyclerView = (RecyclerView)findViewById(R.id.recyclerv);
        patientdata = new ArrayList<>();
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        patientAdapter = new PatientAdapter(this,patientdata);
        recyclerView.setAdapter(patientAdapter);








    }
    class DisplayTask extends AsyncTask<String,Void,String>
    {
        Context ctx ;
        String jsonn;

        public DisplayTask(Context ctx) {
            this.ctx = ctx;
        }

        @Override
        protected String doInBackground(String... params) {
            String url_fetch_data     = "https://m123456m.000webhostapp.com/view.php";

            String doctorname = params[0];
            try {
                URL url = new URL(url_fetch_data);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                if (doctorname.length()>0) {
                    String data =
                            URLEncoder.encode("doctorname", "UTF-8") + "=" + URLEncoder.encode(doctorname, "UTF-8");
                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();


                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder stringBuilder = new StringBuilder();

                    while ((jsonn = bufferedReader.readLine()) != null) {
                        stringBuilder.append(jsonn + "\n");
                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    return stringBuilder.toString().trim();


                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
//            Toast.makeText(ctx,s,Toast.LENGTH_LONG).show();
            try {
                jobj = new JSONObject(s);
                jarr = jobj.getJSONArray("server_response");
                String patientname, patientage, patientphone, patientdescription;
                int count = 0;
                while (count < jarr.length()) {
                    JSONObject JO = jarr.getJSONObject(count);
                    patientname = JO.getString("patientname");
                    patientage = JO.getString("patientage");
                    patientphone = JO.getString("patientphone");
                    patientdescription = JO.getString("description");
                    Patients patients = new Patients(patientname, patientage, patientphone, patientdescription);
                    patientdata.add(patients);
                    count++;
                }


            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
            }
            //  Intent docintent = new Intent(ctx,DisplayPatient.class);
            //  docintent.putExtra("KEY",s);
            //ctx.startActivity(docintent);

        }
    }


}
