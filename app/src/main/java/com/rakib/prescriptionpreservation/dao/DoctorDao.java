package com.rakib.prescriptionpreservation.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.rakib.prescriptionpreservation.entities.Doctor;

import java.util.List;

@Dao
public interface DoctorDao {
    @Insert
    long insertDoctor(Doctor doctor);

    @Query("select * from Doctor order by doctorName asc")
    List<Doctor> getAllDoctor();

    @Query("select * from Doctor where presId like:presid")
    Doctor getDoctorByID(long presid);

    @Update
    int updateDoctor(Doctor doctor);

    @Delete
    int deleteDoctor(Doctor doctor);
}
