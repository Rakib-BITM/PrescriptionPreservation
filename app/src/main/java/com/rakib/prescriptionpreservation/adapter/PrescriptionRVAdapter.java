package com.rakib.prescriptionpreservation.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.rakib.prescriptionpreservation.PrescriptionDetailsActivity;
import com.rakib.prescriptionpreservation.PrescriptionUpdateActivity;
import com.rakib.prescriptionpreservation.R;
import com.rakib.prescriptionpreservation.db.PrescriptionDB;
import com.rakib.prescriptionpreservation.entities.Prescription;
import com.rakib.prescriptionpreservation.joinentity.PrescriptionWithDoctor;

import java.io.Serializable;
import java.util.List;

public class PrescriptionRVAdapter extends RecyclerView.Adapter<PrescriptionRVAdapter.PrescriptionViewHolder>{
    private Context context;
    private List<PrescriptionWithDoctor> prescriptionList;

    public PrescriptionRVAdapter(Context context, List<PrescriptionWithDoctor> prescriptionList) {
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
        holder.docname.setText(prescriptionList.get(position).doctor.getDoctorName());
        holder.docnumber.setText(prescriptionList.get(position).doctor.getDocNumber());
        holder.hosname.setText(prescriptionList.get(position).prescription.getHospitalName());
        holder.date.setText(prescriptionList.get(position).prescription.getDate());

        //set Prescription photo
        if (prescriptionList.get(position).prescription.getImage() != null){
            Bitmap bitmap = BitmapFactory.decodeFile(prescriptionList.get(position).prescription.getImage());
            holder.photo.setImageBitmap(bitmap);
        }
        // Prescription details
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //prescription details
                Prescription p = prescriptionList.get(position).prescription;
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

        holder.menuTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(context, view);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.item_edit:
                                Prescription pu = prescriptionList.get(position).prescription;
                                Intent intent = new Intent(context, PrescriptionUpdateActivity.class);
                                intent.putExtra("presUp",pu);
                                context.startActivity(intent);
                                break;
                            case R.id.item_delete:
                                showPrescriptionDeleteDialog(position);
                                break;
                        }
                        return false;
                    }
                });
            }
        });





    }

    @Override
    public int getItemCount() {
        return prescriptionList.size();
    }


    public class PrescriptionViewHolder extends RecyclerView.ViewHolder {
        ImageView photo;
        TextView docname,docnumber,hosname,date,menuTV;
        public PrescriptionViewHolder(@NonNull View itemView) {
            super(itemView);
            //step 2
            docname = itemView.findViewById(R.id.row_docname);
            docnumber = itemView.findViewById(R.id.row_docnumber);
            hosname = itemView.findViewById(R.id.row_hosname);
            date = itemView.findViewById(R.id.row_date);
            photo = itemView.findViewById(R.id.row_image);
            menuTV = itemView.findViewById(R.id.row_menu);
        }
    }

    public void updateList(List<PrescriptionWithDoctor> prescriptions){
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
                Prescription prescription = prescriptionList.get(p).prescription;
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
            List<PrescriptionWithDoctor> prescriptionWithDoctorList = PrescriptionDB.getInstance(context).getPrescriptionDao().getAllPrescriptionWithDoctor();
            updateList(prescriptionWithDoctorList);
        }
    }
}
