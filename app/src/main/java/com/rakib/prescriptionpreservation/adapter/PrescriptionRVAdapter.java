package com.rakib.prescriptionpreservation.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rakib.prescriptionpreservation.R;
import com.rakib.prescriptionpreservation.entities.Prescription;

import java.util.List;

public class PrescriptionRVAdapter extends RecyclerView.Adapter<PrescriptionRVAdapter.PrescriptionViewHolder>{
    private Context context;
    private List<Prescription> prescriptionList;

    public PrescriptionRVAdapter(Context context, List<Prescription> prescriptionList) {
        this.context = context;
        this.prescriptionList = prescriptionList;
    }

    @NonNull
    @Override
    public PrescriptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.prescription_row,parent,false);
        return new PrescriptionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PrescriptionViewHolder holder, final int position) {
        //step 3




    }

    @Override
    public int getItemCount() {
        return prescriptionList.size();
    }


    public class PrescriptionViewHolder extends RecyclerView.ViewHolder {
        public PrescriptionViewHolder(@NonNull View itemView) {
            super(itemView);
            //step 2

        }
    }

    public void updateList(List<Prescription> prescriptions){
        this.prescriptionList = prescriptions;
        notifyDataSetChanged();
    }
}
