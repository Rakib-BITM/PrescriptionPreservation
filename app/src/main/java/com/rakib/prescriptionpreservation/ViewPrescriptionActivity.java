package com.rakib.prescriptionpreservation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.rakib.prescriptionpreservation.adapter.PrescriptionRVAdapter;
import com.rakib.prescriptionpreservation.db.PrescriptionDB;
import com.rakib.prescriptionpreservation.entities.Prescription;

import java.util.List;

public class ViewPrescriptionActivity extends AppCompatActivity {

    private RecyclerView prescriptionRV;
    private PrescriptionRVAdapter rvAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_prescription);
        setTitle("All Prescription");

        prescriptionRV = findViewById(R.id.prescriptionRV);

        List<Prescription> prescriptions = PrescriptionDB.getInstance(this).getPrescriptionDao().getAllPrescription();

        rvAdapter = new PrescriptionRVAdapter(this,prescriptions);

        GridLayoutManager glm = new GridLayoutManager(this, 1);
        prescriptionRV.setLayoutManager(glm);
        prescriptionRV.setAdapter(rvAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu,menu);
        MenuItem item = menu.findItem(R.id.searchId);
        SearchView searchView = (SearchView) item.getActionView();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.searchId:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
