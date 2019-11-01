package com.rakib.prescriptionpreservation.joinentity;

import androidx.room.Embedded;

import com.rakib.prescriptionpreservation.entities.Doctor;
import com.rakib.prescriptionpreservation.entities.Prescription;

public class PrescriptionWithDoctor {
    @Embedded
    public Prescription prescription;
    @Embedded
    public Doctor doctor;
}
