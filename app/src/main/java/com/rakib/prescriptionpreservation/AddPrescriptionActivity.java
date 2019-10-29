package com.rakib.prescriptionpreservation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.rakib.prescriptionpreservation.db.PrescriptionDB;
import com.rakib.prescriptionpreservation.entities.Prescription;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddPrescriptionActivity extends AppCompatActivity {
    private EditText docName,docNumber,docAddress,hosName,date;
    private ImageView profilePic;
    private String currentPhotoPath;
    private final int REQUEST_CALL_PHONE_CODE = 123;
    private final int REQUEST_STORAGE_CODE = 456;
    private final int REQUEST_CAMERA_CODE = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_prescription);
        initialization();


    }

    public void showCameraPreview(View view) {
        if (checkStoragePermission()){
            dispatchCameraIntent();
        }
    }

    public void savePrescription(View view) {
        if (checkEmptyField()){
            String path = currentPhotoPath;
            String doc = docName.getText().toString();
            String dnum = docNumber.getText().toString();
            String dadd = docAddress.getText().toString();
            String dat = getCurrentDate();

            Prescription prescription = new Prescription(path,doc,dnum,dadd,dat);

            long insertedRow = PrescriptionDB.getInstance(this).getPrescriptionDao().insertPrescription(prescription);

            if (insertedRow>0){
                Toast.makeText(this, "Prescription saved", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this,MainActivity.class));
                finish();
            }
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
                        "com.rakib.contact",
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
        docName = findViewById(R.id.docnameIn);
        docNumber = findViewById(R.id.docnumIn);
        docAddress = findViewById(R.id.docaddIn);
        hosName = findViewById(R.id.hosnameIn);
        date = findViewById(R.id.dateIn);
        profilePic = findViewById(R.id.photo_in);
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

    private String getCurrentDate(){
        return new SimpleDateFormat("dd/MM/yyyy")
                .format(new Date());
    }
}
