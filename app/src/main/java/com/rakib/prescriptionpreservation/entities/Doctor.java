package com.rakib.prescriptionpreservation.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import static androidx.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(entity = Prescription.class,parentColumns = "id",childColumns = "presId",onDelete = CASCADE,onUpdate = CASCADE),indices = {@Index("presId")})
public class Doctor implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private long docid;
    private long presId;
    private String doctorName;
    private String docNumber;
    private String docAddress;

    public Doctor(long presId, String doctorName, String docNumber, String docAddress) {
        this.presId = presId;
        this.doctorName = doctorName;
        this.docNumber = docNumber;
        this.docAddress = docAddress;
    }

    @Ignore
    public Doctor(long docid, long presId, String doctorName, String docNumber, String docAddress) {
        this.docid = docid;
        this.presId = presId;
        this.doctorName = doctorName;
        this.docNumber = docNumber;
        this.docAddress = docAddress;
    }

    public long getDocid() {
        return docid;
    }

    public void setDocid(long docid) {
        this.docid = docid;
    }

    public long getPresId() {
        return presId;
    }

    public void setPresId(long presId) {
        this.presId = presId;
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
}
