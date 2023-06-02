package com.example.myapplication;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class LogOn extends AppCompatActivity {
    private Button exit;

    private Intent intent;

    private FirebaseAuth auth;
    private TextView ismv;
    private ArrayList<HashMap<String,Object>>   listmap =new ArrayList<>();
    private HashMap<String,Object> ism1 =new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_on);
        initialize(savedInstanceState);
        String uid = auth.getCurrentUser().getUid();
        FirebaseDatabase database= FirebaseDatabase.getInstance();;
        DatabaseReference ref = database.getReference().child(uid);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listmap = new ArrayList<>();
                try {
                    GenericTypeIndicator<HashMap<String,Object>> ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
                    for (DataSnapshot data : snapshot.getChildren()){
                        HashMap<String,Object> map =data.getValue(ind);
                        listmap.add(map);
                        ism1 = listmap.get((int) 0);
                        ismv.setText(ism1.get("name").toString());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                intent.setClass(getApplicationContext(),MainActivity2.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void initialize(Bundle savedInstanceState) {
        exit = findViewById(R.id.logout);
        ismv = findViewById(R.id.Ism);
        auth= FirebaseAuth.getInstance();
        intent =new Intent();
    }
}