package com.example.hcuconnect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hcu.uohonnect.R;

public class ChangePassword extends AppCompatActivity {
    EditText oldpw,newpw;
    Button changepassword;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        oldpw=findViewById(R.id.id_oldpassword);
        newpw=findViewById(R.id.id_newpassword);
        changepassword=findViewById(R.id.id_bt_changepassword);
        auth=FirebaseAuth.getInstance();
        changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user=oldpw.getText().toString();
                String pass=newpw.getText().toString();
                if(user.isEmpty()){
                    oldpw.setError("Please Enter Old Password");
                    oldpw.requestFocus();
                }
                else if(pass.isEmpty()){
                    newpw.setError("Please Enter New Password");
                    newpw.requestFocus();
                }
                else {
                    change();
                }
            }
        });



    }
    public void change(){
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        if (user!=null){
            user.updatePassword(newpw.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"Password updated",Toast.LENGTH_SHORT).show();
                            FirebaseAuth.getInstance().signOut();
                            startActivity(new Intent(ChangePassword.this,Login.class));

                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Password updation failed",Toast.LENGTH_SHORT).show();

                        }
                }
            });
        }
    }
}
