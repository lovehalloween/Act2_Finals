package com.example.act2_finals;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Signup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        EditText fullname = findViewById(R.id.fullname);
        EditText email = findViewById(R.id.Email);
        EditText password = findViewById(R.id.Password);
        EditText retypepass = findViewById(R.id.retypepass);
        EditText usertype = findViewById(R.id.Usertype);
        Button signupbtn = findViewById(R.id.Signupbtn);
        TextView gotoLogin = findViewById(R.id.gotoLogin);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        gotoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent GotoLoginPage = new Intent(Signup.this, Login.class);
                startActivity(GotoLoginPage);
            }
        });

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullNameText = fullname.getText().toString();
                String emailText = email.getText().toString();
                String passwordText = password.getText().toString();
                String retypePassText = retypepass.getText().toString();
                String userText = usertype.getText().toString();

                if (passwordText.equals(retypePassText)) {
                    Map<String, Object> user = new HashMap<>();
                    user.put("Name", fullNameText);
                    user.put("Email", emailText);
                    user.put("Password", passwordText);
                    user.put("User_Type", userText);

                    db.collection("register")
                            .document(emailText)
                            .set(user)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(Signup.this, "Success!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(Signup.this, "Failed!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else {
                    Toast.makeText(Signup.this, "The Password and Retyped Password did not match!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}