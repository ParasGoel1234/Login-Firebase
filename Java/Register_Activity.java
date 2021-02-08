package com.codewithparas.whatsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Register_Activity extends AppCompatActivity {
    private EditText UserName, Password, email;
    private Button Register;
    FirebaseAuth auth;
    DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register_);

        UserName = findViewById(R.id.UserName);
        Password = findViewById(R.id.Password);
        email = findViewById(R.id.email);
        Register = findViewById(R.id.buttonRegister);

        auth = FirebaseAuth.getInstance();

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String UserName_txt = UserName.getText().toString();
                String Password_txt = Password.getText().toString();
                String email_txt = email.getText().toString();
                if(TextUtils.isEmpty(UserName_txt) || TextUtils.isEmpty(Password_txt) || TextUtils.isEmpty(email_txt) ){
                    Toast.makeText(Register_Activity.this, "Please fill details", Toast.LENGTH_SHORT).show();
                }
                else{
                    RegisterNow(UserName_txt, email_txt, Password_txt);
                }
            }
        });
    }

    private void RegisterNow(final String UserName, String email, String Password){
    auth.createUserWithEmailAndPassword(email, Password)
            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        FirebaseUser firebaseUser = auth.getCurrentUser();
                        String UserId = firebaseUser.getUid();

                        myRef = FirebaseDatabase.getInstance().getReference("MyUser").child(UserId);

                        // HashMap
                        HashMap<String, String>hashMap = new HashMap<>();
                        hashMap.put("Id",UserId);
                        hashMap.put("UserName",UserName);
                        hashMap.put("imageURL","default");

                        myRef.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Intent intent = new Intent(Register_Activity.this, MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }
                            }
                        });
                    }
                    else {
                        Toast.makeText(Register_Activity.this, "Invalid email Id or Password", Toast.LENGTH_SHORT).show();
                    }
                }
            });
    }
}