package com.rakib.prescriptionpreservation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rakib.prescriptionpreservation.db.PrescriptionDB;
import com.rakib.prescriptionpreservation.entities.Doctor;
import com.rakib.prescriptionpreservation.entities.Prescription;
import com.rakib.prescriptionpreservation.joinentity.PrescriptionWithDoctor;

import org.w3c.dom.Text;

import java.util.List;

public class PrescriptionDetailsActivity extends AppCompatActivity {

    private TextView dname,dnumber,daddress,hname,date;
    private ImageView photo;

    private Prescription prescription;
    private Doctor doctor;

    private final int REQUEST_CALL_PHONE_CODE = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_details);
        getSupportActionBar().hide();
        initialization();
        prescription = (Prescription) getIntent().getSerializableExtra("prescription");
        long presid = prescription.getId();

        PrescriptionWithDoctor presDoc = PrescriptionDB.getInstance(this).getPrescriptionDao().getPrescriptionWithDoctor(presid);

        doctor = PrescriptionDB.getInstance(this).getDoctorDao().getDoctorByID(presid);

        dname.setText(doctor.getDoctorName());
        dnumber.setText(doctor.getDocNumber());
        daddress.setText(doctor.getDocAddress());
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
                TextView dialogButton = dialog.findViewById(R.id.okBtn);
                // if button is clicked, close the custom dialog
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
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


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CALL_PHONE_CODE && grantResults[0] ==
                PackageManager.PERMISSION_GRANTED){
            initiatePhoneCall();
        }
    }

    private boolean checkCallPermission(){
        String[] permissions = {Manifest.permission.CALL_PHONE};
        if (checkSelfPermission(Manifest.permission.CALL_PHONE) !=
                PackageManager.PERMISSION_GRANTED){
            requestPermissions(permissions, REQUEST_CALL_PHONE_CODE);
            return false;
        }
        return true;
    }

    private void initiatePhoneCall(){
        String phoneNumber = doctor.getDocNumber();
        Uri phoneUri = Uri.parse("tel:"+phoneNumber);
        Intent callIntent = new Intent(Intent.ACTION_CALL, phoneUri);
        if (callIntent.resolveActivity(getPackageManager()) != null){
            if (checkCallPermission()){
                startActivity(callIntent);
            }
        }else{
            Toast.makeText(this, "no components found", Toast.LENGTH_SHORT).show();
        }
    }


    public void callContact(View view) {
        initiatePhoneCall();
    }
}
