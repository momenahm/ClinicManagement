package com.example.me.doctorsclinics;

/**
 * Created by m.elshaeir on 10/6/2017.
 */

public class Patients {
    String patientname;
    String patientage;
    String patientphone;
    String patientdescription;

    public Patients(String patientname, String patientage, String patientphone, String patientdescription) {
        this.setPatientname(patientname);
        this.setPatientage(patientage);
        this.setPatientphone(patientphone);
        this.setPatientdescription(patientdescription);
    }



    public String getPatientname() {
        return patientname;
    }

    public void setPatientname(String patientname) {
        this.patientname = patientname;
    }

    public String getPatientage() {
        return patientage;
    }

    public void setPatientage(String patientage) {
        this.patientage = patientage;
    }

    public String getPatientphone() {
        return patientphone;
    }

    public void setPatientphone(String patientphone) {
        this.patientphone = patientphone;
    }

    public String getPatientdescription() {
        return patientdescription;
    }

    public void setPatientdescription(String patientdescription) {
        this.patientdescription = patientdescription;
    }
}

