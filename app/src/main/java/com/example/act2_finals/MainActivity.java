package com.example.act2_finals;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText fullname = findViewById(R.id.fullname);
        EditText email = findViewById(R.id.Email);
        EditText password = findViewById(R.id.Password);
        EditText retypepass = findViewById(R.id.retypepass);
        Button signupbtn = findViewById(R.id.Signupbtn);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullNameText = fullname.getText().toString();
                String emailText = email.getText().toString();
                String passwordText = password.getText().toString();
                String retypePassText = retypepass.getText().toString();

                if (passwordText.equals(retypePassText)) {
                    Map<String, Object> user = new HashMap<>();
                    user.put("Name", fullNameText);
                    user.put("Email", emailText);
                    user.put("Password", passwordText);

                    db.collection("register")
                            .add(user)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(MainActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(MainActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    Toast.makeText(MainActivity.this, "The Password and Retyped Password did not match!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}