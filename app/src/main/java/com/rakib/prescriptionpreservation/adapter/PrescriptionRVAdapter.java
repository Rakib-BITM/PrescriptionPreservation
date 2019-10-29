package com.rakib.prescriptionpreservation.adapter;

import android.content.Context;

import com.rakib.prescriptionpreservation.entities.Prescription;

import java.util.List;

public class PrescriptionRVAdapter {
    private Context context;
    private List<Prescription> prescriptionList;

    public PrescriptionRVAdapter(Context context, List<Prescription> prescriptionList) {
        this.context = context;
        this.prescriptionList = prescriptionList;
    }
}
