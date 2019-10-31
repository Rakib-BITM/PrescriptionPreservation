package com.rakib.prescriptionpreservation;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
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
        if (prescription.getImage() !=null){
            Bitmap bitmap = BitmapFactory.decodeFile(prescription.getImage());
            photo.setImageBitmap(bitmap);
        }

        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(PrescriptionDetailsActivity.this);
                dialog.setContentView(R.layout.prescription_image_dialog);
                ImageView image = dialog.findViewById(R.id.presPhoto);
                if (prescription.getImage() !=null){
                    Bitmap bitmap = BitmapFactory.decodeFile(prescription.getImage());
                    image.setImageBitmap(bitmap);
                }
                Button dialogButton = dialog.findViewById(R.id.okBtn);
                // if button is clicked, close the custom dialog
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });



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
