package com.example.hcuconnect;

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
import com.google.firebase.auth.FirebaseAuth;
import com.hcu.uohonnect.R;

public class SendPassword extends AppCompatActivity {
    Button resetps;
    private EditText editText;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_password);
        resetps=findViewById(R.id.id_ResetPw);
        editText=findViewById(R.id.id_resetpassword);

        firebaseAuth=FirebaseAuth.getInstance();
        resetps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=editText.getText().toString();
                if(email.isEmpty()){
                    editText.setError("Please enter your email id");
                    editText.requestFocus();
                }
                else{
                    firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                startActivity(new Intent(SendPassword.this,Login.class));
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"Error Occured",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });





    }
}
