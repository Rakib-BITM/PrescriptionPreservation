package com.rakib.prescriptionpreservation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rakib.prescriptionpreservation.adapter.PrescriptionRVAdapter;
import com.rakib.prescriptionpreservation.db.PrescriptionDB;
import com.rakib.prescriptionpreservation.entities.Doctor;
import com.rakib.prescriptionpreservation.entities.Prescription;
import com.rakib.prescriptionpreservation.joinentity.PrescriptionWithDoctor;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PrescriptionUpdateActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText docName,docNumber,docAddress,hosName;
    private TextView date;
    private ImageView profilePic;

    private final int REQUEST_STORAGE_CODE = 456;
    private final int REQUEST_CAMERA_CODE = 999;
    private String currentPhotoPath;

    private DatePickerDialog datePickerDialog;

    private Prescription prescription;
    private Doctor doctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_update);
        getSupportActionBar().hide();
        initialization();
        date.setOnClickListener(this);

        prescription = (Prescription) getIntent().getSerializableExtra("presUp");
        long presId = prescription.getId();
        doctor = PrescriptionDB.getInstance(this).getDoctorDao().getDoctorByID(presId);
        setValues();
    }

    private void setValues(){
        String path = prescription.getImage();
        if (path !=null){
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            profilePic.setImageBitmap(bitmap);
        }

        docName.setText(doctor.getDoctorName());
        docNumber.setText(doctor.getDocNumber());
        docAddress.setText(doctor.getDocAddress());
        hosName.setText(prescription.getHospitalName());
        date.setText(prescription.getDate());
    }

    public void showCameraPreview(View view) {
        if (checkStoragePermission()){
            dispatchCameraIntent();
        }
    }

    public void updatePrescription(View view) {
            String path = currentPhotoPath;
            String doc = docName.getText().toString();
            String dnum = docNumber.getText().toString();
            String dadd = docAddress.getText().toString();
            String hosn = hosName.getText().toString();
            String dat = date.getText().toString();

            Prescription prescription = new Prescription(path,hosn,dat);

            int insertedRow = PrescriptionDB.getInstance(this).getPrescriptionDao().updatePrescription(prescription);

            if (insertedRow>0){
                Toast.makeText(this, "Prescription Updated", Toast.LENGTH_SHORT).show();
                doctor.setDoctorName(doc);
                doctor.setDocNumber(dnum);
                doctor.setDocAddress(dadd);
                List<PrescriptionWithDoctor> prescriptionWithDoctorList = PrescriptionDB.getInstance(this).getPrescriptionDao().getAllPrescriptionWithDoctor();
                PrescriptionRVAdapter rvAdapter = new PrescriptionRVAdapter(this,prescriptionWithDoctorList);
                rvAdapter.updateList(prescriptionWithDoctorList);
                startActivity(new Intent(this,ViewPrescriptionActivity.class));
                finish();
            }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_STORAGE_CODE && grantResults[0] ==
                PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this, "Permission accepted", Toast.LENGTH_SHORT).show();
            dispatchCameraIntent();
        }
    }



    private boolean checkStoragePermission(){
        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED){
            requestPermissions(permissions, REQUEST_STORAGE_CODE);
            return false;
        }
        return true;
    }

    private void dispatchCameraIntent(){
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.rakib.prescriptionpreservation",
                        photoFile);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(cameraIntent, REQUEST_CAMERA_CODE);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CAMERA_CODE && resultCode == RESULT_OK){
            Bitmap bmp = BitmapFactory.decodeFile(currentPhotoPath);
            profilePic.setImageBitmap(bmp);
        }
    }


    private void initialization(){
        docName = findViewById(R.id.docnameUp);
        docNumber = findViewById(R.id.docnumUp);
        docAddress = findViewById(R.id.docaddUp);
        hosName = findViewById(R.id.hosnameUp);
        date = findViewById(R.id.dateUp);
        profilePic = findViewById(R.id.photo_up);
    }

    @Override
    public void onClick(View v) {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = new DatePickerDialog(this,android.R.style.Theme_Holo_Light_Dialog_MinWidth, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                String dat = dayOfMonth + "/" + month + "/" + year;
                date.setText(dat);

            }
        },year,month,day);
        datePickerDialog.show();
    }


    private boolean checkEmptyField(){
        if (docName.getText().toString().isEmpty()){
            docName.setError("Please enter doctor name");
            return false;
        }
        if (docNumber.getText().toString().isEmpty()){
            docNumber.setError("Enter doctor phone no");
            return false;
        }

        if (docAddress.getText().toString().isEmpty()){
            docAddress.setError("Enter doctor address");
            return false;
        }

        if (hosName.getText().toString().isEmpty()){
            hosName.setError("Enter hospital name");
            return false;
        }

        if (date.getText().toString().isEmpty()){
            date.setError("Enter appointment date");
            return false;
        }
        return true;
    }
}
