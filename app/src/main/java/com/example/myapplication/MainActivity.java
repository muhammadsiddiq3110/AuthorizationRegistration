package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private EditText name;
    private EditText email;
    private EditText parol1;
    private EditText parol2;
    private Button reg;
    private Intent intent = new Intent();
    private FirebaseAuth auth;
    private String nm;
    private HashMap<String, Object> usrs = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializa(savedInstanceState);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().length() > 5 && email.getText().length() > 5 &&
                        parol1.getText().length() > 5 && parol1.getText().length() == parol2.getText().length()) {
                    auth.createUserWithEmailAndPassword(email.getText().toString().trim(), parol1.getText().
                            toString()).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            final String error = task.getException() != null ? task.getException().getMessage() : "";
                            if (task.isSuccessful()) {
                                nm = name.getText().toString();
                                usrs.put("name", nm);
                                FirebaseDatabase db = FirebaseDatabase.getInstance();
                                DatabaseReference kiritish = db.getReference(auth.getCurrentUser().getUid());
                                kiritish.child("user").updateChildren(usrs);

                                intent.setClass(MainActivity.this, LogOn.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } else if (parol1.getText().length() != parol2.getText().length()) {
                    Toast.makeText(MainActivity.this, "Parollar bir xil bo'lishi kerak",
                            Toast.LENGTH_SHORT).show();
                } else if (name.getText().toString().isEmpty()
                        || email.getText().toString().isEmpty()
                        || parol1.getText().toString().isEmpty()
                        || parol2.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Malumot etarli emas ",
                            Toast.LENGTH_SHORT).show();

                }
            }
        });

    }


    private void initializa(Bundle savedInstanceState) {
        name = findViewById(R.id.ism);
        email = findViewById(R.id.email);
        parol1 = findViewById(R.id.kod1);
        parol2 = findViewById(R.id.kod2);
        reg = findViewById(R.id.register);
        auth = FirebaseAuth.getInstance();


    }
}