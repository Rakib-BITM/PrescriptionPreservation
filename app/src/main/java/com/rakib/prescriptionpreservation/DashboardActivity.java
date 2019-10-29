package com.rakib.prescriptionpreservation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        getSupportActionBar().hide();
    }

    public void addPrescription(View view) {
        startActivity(new Intent(this,AddPrescriptionActivity.class));
    }

    public void viewPrescription(View view) {
        startActivity(new Intent(this,ViewPrescriptionActivity.class));
    }
}
