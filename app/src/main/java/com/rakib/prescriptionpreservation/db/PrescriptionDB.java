package com.rakib.prescriptionpreservation.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.rakib.prescriptionpreservation.dao.PrescriptionDao;
import com.rakib.prescriptionpreservation.entities.Prescription;

@Database(entities = {Prescription.class},version = 1,exportSchema = false)
public abstract class PrescriptionDB extends RoomDatabase {
    private static PrescriptionDB db;
    public abstract PrescriptionDao getPrescriptionDao();

    public static PrescriptionDB getInstance(Context context){
        if (db != null){
            return db;
        }

        db = Room
                .databaseBuilder(context, PrescriptionDB.class, "prescription_db")
                .allowMainThreadQueries()
                .build();
        return db;
    }
}
