package com.example.firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.fire.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LihatBarang extends AppCompatActivity {
    private Database db;
    private RecyclerView rv;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lm;
    private ArrayList<LihatBarang> daftarBrg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_barang);

        rv = findViewById(R.id.rv_main);
        rv.setHasFixedSize(true);
        lm = new LinearLayoutManager(this);
        rv.setLayoutManager(lm);
        db = FirebaseDatabase.getInstance().getReference();

        db.child("Barang").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                daftarBrg = new ArrayList<>();
                for (DataSnapshot ds : snapshot.getChildren()){
                    LihatBarang brg = ds.getValue(LihatBarang.class);
                    brg.setKode(ds.getKey());
                    daftarBrg.add(brg);
                }
                adapter = new LihatBarang(AdapterLihatBarang,LihatBarang.this);
                rv.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println(error.getDetails()+" "+error.getMessage());
            }
        });
    }
    public static Intent getActIntent(Activity a){
        return new Intent(a,LihatBarang.class);
    }
}