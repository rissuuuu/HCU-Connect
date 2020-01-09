package com.example.hcuconnect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hcu.uohonnect.R;

public class Login extends AppCompatActivity implements View.OnClickListener {
    EditText username,password;
    Button login;
    FirebaseAuth mFirebaseAuth;
    TextView forgotpassword;
    private FirebaseAuth.AuthStateListener mFirebaseAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username=findViewById(R.id.id_username);
        password=findViewById(R.id.id_password);
        login=findViewById(R.id.id_Login);
        forgotpassword=findViewById(R.id.id_forgotpassword);
        mFirebaseAuth=FirebaseAuth.getInstance();


        mFirebaseAuthListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser=mFirebaseAuth.getCurrentUser();

                if(firebaseUser!=null){
                    Toast.makeText(getApplicationContext(),"Logged in",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Login.this,Dashboard.class));
                }
                else{
                    Toast.makeText(getApplicationContext(),"Please Log in",Toast.LENGTH_SHORT).show();

                }
            }
        };
        login.setOnClickListener(this);
        forgotpassword.setOnClickListener(this);

    }
    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mFirebaseAuthListener);
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.id_Login:
                String user=username.getText().toString();
                String pass=password.getText().toString();
                if(user.isEmpty()){
                    username.setError("Please Enter Email id");
                    username.requestFocus();
                }
                else if(pass.isEmpty()){
                    password.setError("Please Enter Email id");
                    password.requestFocus();
                }
                else if(user.isEmpty()&&pass.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please enter username and password",Toast.LENGTH_SHORT).show();
                }
                else{
                    mFirebaseAuth.signInWithEmailAndPassword(user,pass).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                username.setError("Incorrect Username Password");
                                username.requestFocus();

                            }
                            else{
                                startActivity(new Intent(Login.this,Dashboard.class));
                            }
                        }
                    });
                }
                break;

            case R.id.id_forgotpassword:
                startActivity(new Intent(Login.this,SendPassword.class));
                break;

        }
    }

    @Override
    public void onBackPressed() {

    }
}
