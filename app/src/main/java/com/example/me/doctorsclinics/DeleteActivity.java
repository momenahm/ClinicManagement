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

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class DeleteActivity extends AppCompatActivity {
    ArrayList<String> listitem = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    String show;
    TextView textView;
    Spinner sp;
    String docname;
ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
        docname = getIntent().getExtras().getString("KEYYYY");
      //  Toast.makeText(getApplicationContext(),docname,Toast.LENGTH_LONG).show();
        sp = (Spinner) findViewById(R.id.sp1);
        arrayAdapter = new ArrayAdapter<String>(this, R.layout.spinnertext, R.id.txt, listitem);
        textView = (TextView)findViewById(R.id.txt);
        sp.setAdapter(arrayAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        String method = "getpatient";
        new BackTask(this).execute(method,docname);
    }

    public void deleterr(View view)
    {
        String method = "deletepatient";
        String doctorname = docname;
      //  Toast.makeText(getApplicationContext(),doctorname,Toast.LENGTH_LONG).show();
        String data = sp.getSelectedItem().toString();
        new BackTask(this).execute(method,docname,data);
    }


    class  BackTask extends AsyncTask<String,Void,String>
    {

        Context ctx;

        public BackTask(Context ctx) {
            this.ctx = ctx;
        }
        ArrayList<String> list;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            list = new ArrayList<>();
            progressDialog = new ProgressDialog(ctx);
        }


        @Override
        protected String doInBackground(String... params) {
            String method = params[0];
            String get_url = "https://m123456m.000webhostapp.com/memo.php";
            String delete_url = "https://m123456m.000webhostapp.com/deletepatient.php";
            String json;

            if (method.equals("getpatient"))
                try {
                    String dotorrname = params[1];
                    URL url1 = new URL(get_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection)url1.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                    String docnamedata=
                            URLEncoder.encode("doctorname","UTF-8")+"="+URLEncoder.encode(dotorrname,"UTF-8");
                    bufferedWriter.write(docnamedata);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((json = bufferedReader.readLine())!=null)
                    {
                        stringBuilder.append(json + "\n");
                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    String result = stringBuilder.toString();
                    try {
                        JSONObject jobj = new JSONObject(result);
                        JSONArray jarr = jobj.getJSONArray("server_response");
                        String patientname;
                        int count = 0;
                        while (count < jarr.length()) {
                            JSONObject JO = jarr.getJSONObject(count);
                            patientname = JO.getString("patientname");
                            list.add(patientname);

                            count++;
                        }

                    } catch (JSONException e1) {
                        e1.printStackTrace();
                        Toast.makeText(getApplicationContext(),e1.toString(),Toast.LENGTH_LONG).show();
                    }
                    return "view data";



                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            else if (method.equals("deletepatient"))
            {
                String doctorrrname = params[1];
                String dataa = params[2];
                try {
                    URL url = new URL(delete_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoInput(true);
                    httpURLConnection.setDoOutput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                    String dataaa=
                            URLEncoder.encode("doctorname","UTF-8")+"="+URLEncoder.encode(doctorrrname,"UTF-8")+"&"+
                            URLEncoder.encode("patientname","UTF-8")+"="+URLEncoder.encode(dataa,"UTF-8");
                    bufferedWriter.write(dataaa);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    inputStream.close();
                    httpURLConnection.disconnect();


                    return "Record Deleted";


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            if (s.equals("view data"))
            {
                show = s;
                listitem.addAll(list);
                arrayAdapter.notifyDataSetChanged();
            }
            else if (s.equals("Record Deleted")) {
                try {

                    Toast.makeText(ctx,s,Toast.LENGTH_LONG).show();

                }
                catch (Exception e)
                {
                    Toast.makeText(ctx,e.toString(),Toast.LENGTH_LONG).show();
                }
            }



            super.onPostExecute(s);
        }
    }
}
