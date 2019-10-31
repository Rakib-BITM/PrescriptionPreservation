package com.rakib.prescriptionpreservation.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.rakib.prescriptionpreservation.PrescriptionDetailsActivity;
import com.rakib.prescriptionpreservation.R;
import com.rakib.prescriptionpreservation.db.PrescriptionDB;
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
        //step 1
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.prescription_row,parent,false);
        return new PrescriptionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PrescriptionViewHolder holder, final int position) {
        //step 3
        holder.docname.setText(prescriptionList.get(position).getDoctorName());
        holder.docnumber.setText(prescriptionList.get(position).getDocNumber());
        holder.hosname.setText(prescriptionList.get(position).getHospitalName());
        holder.date.setText(prescriptionList.get(position).getDate());

        //set Prescription photo

        // Prescription details
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //prescription details
                Prescription p = prescriptionList.get(position);
                Intent intent = new Intent(context, PrescriptionDetailsActivity.class);
                intent.putExtra("prescription",p);
                context.startActivity(intent);
            }
        });


        //delete prescription on long click
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showPrescriptionDeleteDialog(position);
                return false;
            }
        });



    }

    @Override
    public int getItemCount() {
        return prescriptionList.size();
    }


    public class PrescriptionViewHolder extends RecyclerView.ViewHolder {
        ImageView photo;
        TextView docname,docnumber,hosname,date;
        public PrescriptionViewHolder(@NonNull View itemView) {
            super(itemView);
            //step 2
            docname = itemView.findViewById(R.id.row_docname);
            docnumber = itemView.findViewById(R.id.row_docnumber);
            hosname = itemView.findViewById(R.id.row_hosname);
            date = itemView.findViewById(R.id.row_date);
            photo = itemView.findViewById(R.id.row_image);
        }
    }

    public void updateList(List<Prescription> prescriptions){
        this.prescriptionList = prescriptions;
        notifyDataSetChanged();
    }

    private void showPrescriptionDeleteDialog(final int p) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.delete_prescription_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(view);
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Prescription prescription = prescriptionList.get(p);
                deletePrescription(prescription);
            }
        });
        builder.setNegativeButton("Cancel", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void deletePrescription(Prescription prescription) {
        int deletedRow = PrescriptionDB.getInstance(context).getPrescriptionDao().deletePrescription(prescription);
        if (deletedRow > 0){
            Toast.makeText(context, "Prescription deleted", Toast.LENGTH_SHORT).show();
            prescriptionList.remove(prescription);
            notifyDataSetChanged();
        }
    }
}
