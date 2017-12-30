package com.example.me.doctorsclinics;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

/**
 * Created by m.elshaeir on 9/15/2017.
 */

public class BackgroundTask extends AsyncTask<String,Void,String>
{
    Context ctx;
    AlertDialog alertDialog;
    String json_string ;
    String JSON_STRING;

    public BackgroundTask(Context ctx) {
        this.ctx = ctx;
    }




    @Override
    protected void onPreExecute() {


        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... args) {
        String method = args[0];
        String url_register       = "https://m123456m.000webhostapp.com/doctorinfo.php";
        String url_add_patient    = "https://m123456m.000webhostapp.com/test.php";
        String url_delete_patient = "https://m123456m.000webhostapp.com/deletepatient.php";



        if(method.equals("register"))
        {
            String name     = args[1];
            String password = args[2];
            String email    = args[3];
            String phone    = args[4];
            try {
                URL url = new URL(url_register);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
                String data_String =
                                URLEncoder.encode("name","UTF-8")+"="+ URLEncoder.encode(name,"UTF-8")+"&"+
                                URLEncoder.encode("password","UTF-8")+"="+ URLEncoder.encode(password ,"UTF-8")+"&"+
                                URLEncoder.encode("email","UTF-8")+"="+ URLEncoder.encode(email,"UTF-8")+"&"+
                                URLEncoder.encode("phone","UTF-8")+"="+ URLEncoder.encode(phone,"UTF-8");
                bufferedWriter.write(data_String);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                inputStream.close();
                httpURLConnection.disconnect();
                return "Registeration Success";

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }




        else if (method.equals("addpatient"))
        {
            String patientname   = args[1];
            String patientage    = args[2];
            String patientphone  = args[3];
            String description   = args[4];
            String docname    = args[5];
            try {
                URL url = new URL(url_add_patient);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setRequestMethod("POST");
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String addData =
                                URLEncoder.encode("patientname","UTF-8")+"="+URLEncoder.encode(patientname,"UTF-8")+"&"+
                                URLEncoder.encode("patientage","UTF-8")+"="+URLEncoder.encode(patientage,"UTF-8")+"&"+
                                URLEncoder.encode("patientphone","UTF-8")+"="+URLEncoder.encode(patientphone,"UTF-8")+"&"+
                                URLEncoder.encode("description","UTF-8")+"="+URLEncoder.encode(description,"UTF-8")+"&"+
                                URLEncoder.encode("doctorname","UTF-8")+"="+URLEncoder.encode(docname,"UTF-8");
                bufferedWriter.write(addData);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                inputStream.close();
                httpURLConnection.disconnect();
                return "patient Data Added .......";
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return null;
    }



    @Override
    protected void onPostExecute(String result) {
        if (result.equals("Registeration Success"))
        {
            Toast.makeText(ctx, result, Toast.LENGTH_LONG).show();
            Intent regpage = new Intent(ctx, LoginActivity.class);
            regpage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            ctx.startActivity(regpage);

        }
        else if (result.equals(" Error register please..... ")) {

            alertDialog.setMessage(result);
            alertDialog.show();



        }
       else if (result.equals("patient Data Added ......."))
        {
            try {
                Toast.makeText(ctx, "Patient Added Successfully.........", Toast.LENGTH_LONG).show();
                Intent maintntent2 = new Intent(ctx,MainActivity.class);
                ctx.startActivity(maintntent2);
            }catch (Exception e)
            {
                Toast.makeText(ctx, e.toString(), Toast.LENGTH_LONG).show();
            }
       //     Intent homepage = new Intent(ctx,MainActivity.class);
          //  homepage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            //ctx.startActivity(homepage);
        }

     //   else {
        //    Toast.makeText(ctx,result,Toast.LENGTH_LONG).show();
      //  }






    }
}

