package com.example.me.doctorsclinics;

import java.util.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by m.elshaeir on 10/6/2017.
 */

public class PatientAdapter extends RecyclerView.Adapter <PatientAdapter.Reholder>{

     List <Patients> patientdata;
    Context context;

    public PatientAdapter(Context context, List<Patients> patientdata) {
        this.patientdata = patientdata;
        this.context = context;
    }

    @Override
    public Reholder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_listview,parent,false);
        Reholder reholder = new Reholder(view);
        return reholder;
    }

    @Override
    public void onBindViewHolder(Reholder holder, int position)
    {


         holder.miname.setText(patientdata.get(position).getPatientname());
        holder.miage.setText(patientdata.get(position).getPatientage());
        holder.miphone.setText(patientdata.get(position).getPatientphone());
        holder.midesc.setText(patientdata.get(position).getPatientdescription());



    }

    @Override
    public int getItemCount() {
        return patientdata.size();
    }
    class Reholder extends RecyclerView.ViewHolder
    {
      TextView miname,miage,miphone,midesc;
        public Reholder(View itemView) {
            super(itemView);
            miname = (TextView)itemView.findViewById(R.id.lname);
            miage  = (TextView)itemView.findViewById(R.id.lage);
            miphone= (TextView)itemView.findViewById(R.id.lphone);
            midesc = (TextView)itemView.findViewById(R.id.ldescription);
        }
    }

}
