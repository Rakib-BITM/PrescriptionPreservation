package com.rakib.prescriptionpreservation.entities;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
@Entity
public class Prescription implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String image;
    private String doctorName;
    private String docNumber;
    private String docAddress;
    private String hospitalName;
    private String date;

    public Prescription(String image, String doctorName, String docNumber, String docAddress, String hospitalName, String date) {
        this.image = image;
        this.doctorName = doctorName;
        this.docNumber = docNumber;
        this.docAddress = docAddress;
        this.hospitalName = hospitalName;
        this.date = date;
    }

    @Ignore
    public Prescription(String doctorName, String docNumber, String docAddress, String hospitalName, String date) {
        this.doctorName = doctorName;
        this.docNumber = docNumber;
        this.docAddress = docAddress;
        this.hospitalName = hospitalName;
        this.date = date;
    }

    @Ignore
    public Prescription(long id, String image, String doctorName, String docNumber, String docAddress, String hospitalName, String date) {
        this.id = id;
        this.image = image;
        this.doctorName = doctorName;
        this.docNumber = docNumber;
        this.docAddress = docAddress;
        this.hospitalName = hospitalName;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    public String getDocAddress() {
        return docAddress;
    }

    public void setDocAddress(String docAddress) {
        this.docAddress = docAddress;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
