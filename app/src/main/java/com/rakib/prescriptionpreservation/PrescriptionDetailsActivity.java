package com.rakib.prescriptionpreservation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.rakib.prescriptionpreservation.entities.Prescription;

public class PrescriptionDetailsActivity extends AppCompatActivity {

    private TextView dname,dnumber,daddress,hname,date;
    private ImageView photo;

    private Prescription prescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_details);
        getSupportActionBar().hide();
        initialization();
        prescription = (Prescription) getIntent().getSerializableExtra("prescription");
        long presid = prescription.getId();

        dname.setText(prescription.getDoctorName());
        dnumber.setText(prescription.getDocNumber());
        daddress.setText(prescription.getDocAddress());
        hname.setText(prescription.getHospitalName());
        date.setText(prescription.getDate());


        //photo set is pending


    }


    private void initialization(){
        dname = findViewById(R.id.docnameD);
        dnumber = findViewById(R.id.docnumD);
        daddress = findViewById(R.id.docaddD);
        hname = findViewById(R.id.dochospitalD);
        date = findViewById(R.id.appDateD);
        photo = findViewById(R.id.presImage);
    }
}
