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

public class MainActivity2 extends AppCompatActivity {
private Button kir,reg;
private EditText email,parol1;
private FirebaseAuth auth;
 private Intent intent=new Intent();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
      initializa(savedInstanceState);
        if (auth.getCurrentUser() != null) {
            intent.setClass(MainActivity2.this, LogOn.class);
            startActivity(intent);
        }

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.setClass(MainActivity2.this,MainActivity.class);
                startActivity(intent);

            }
        });
        kir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!email.getText().toString().isEmpty()&&!parol1.getText().toString().isEmpty())
                {

                auth.signInWithEmailAndPassword(email.getText().toString(),parol1.getText().toString()).addOnCompleteListener(MainActivity2.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        final String error=task.getException()!=null?task.getException().getMessage():"";
                        if (task.isSuccessful()) {
                            intent.setClass(MainActivity2.this,LogOn.class);
                            startActivity(intent);
                            finish();
                        }
                    else {
                            Toast.makeText(MainActivity2.this, error, Toast.LENGTH_SHORT).show();
                    }
                    }
                });}
                else {
                    Toast.makeText(MainActivity2.this, "email va parol bo'sh bo'lmasin!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private void initializa(Bundle savedInstanceState) {
        email = findViewById(R.id.email);
        parol1 = findViewById(R.id.kod1);
        reg = findViewById(R.id.register);
        kir = findViewById(R.id.kirish);
        auth = FirebaseAuth.getInstance();

    }
}