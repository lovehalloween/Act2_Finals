package com.example.act2_finals;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        EditText login_email = findViewById(R.id.Login_email);
        EditText login_password = findViewById(R.id.Login_pass);
        Button login_btn = findViewById(R.id.Loginbtn);
        TextView gotoReg = findViewById(R.id.gotoSignup);

        gotoReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent GotoRegisterPage = new Intent(Login.this, Signup.class);
                startActivity(GotoRegisterPage);
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Login_email = login_email.getText().toString();
                String Login_password = login_password.getText().toString();

                DocumentReference documentReference = db.collection("users").document(Login_email);

                documentReference.get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot documentSnapshot = task.getResult();
                        if (documentSnapshot.exists()) {
                            String specificpassword = documentSnapshot.getString("password");
                            if (specificpassword != null && specificpassword.equals(Login_password)) {
                                String user_type = documentSnapshot.getString("user-type");
                                if (user_type != null) {
                                    if (user_type.equals("admin")) {
                                        Intent GotoAdminDashboard = new Intent(Login.this, Admin.class);
                                        startActivity(GotoAdminDashboard);
                                        Toast.makeText(Login.this, "Welcome Admin", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Intent GoToStudentDashboard = new Intent(Login.this, Student.class);
                                        startActivity(GoToStudentDashboard);
                                        Toast.makeText(Login.this, "Welcome Student", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            } else {
                                Toast.makeText(Login.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(Login.this, "User does not exist", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(Login.this, "Error logging in", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
