package com.rakib.prescriptionpreservation.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.rakib.prescriptionpreservation.entities.Prescription;
import com.rakib.prescriptionpreservation.joinentity.PrescriptionWithDoctor;

import java.util.List;

@Dao
public interface PrescriptionDao {
    @Insert
    long insertPrescription(Prescription prescription);

    @Query("select * from Prescription order by id desc")
    List<Prescription> getAllPrescription();

    @Query("select * from Prescription where id like:noteID")
    Prescription getPrescriptionByID(long noteID);

    @Update
    int updatePrescription(Prescription prescription);

    @Delete
    int deletePrescription(Prescription prescription);


    //join queary

    @Query("select * from Prescription " +
            "inner join Doctor " +
            "on Doctor.presId = Prescription.id")
    List<PrescriptionWithDoctor> getAllPrescriptionWithDoctor();

    @Query("select * from Prescription " +
            "inner join Doctor " +
            "on Doctor.presId = Prescription.id " +
            "where Prescription.id like :presID")
    PrescriptionWithDoctor getPrescriptionWithDoctor(long presID);
}
