package com.example.hcuconnect;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hcu.uohonnect.R;

import java.util.ArrayList;
import java.util.List;

public class Dashboard extends AppCompatActivity {
    getmessages data;
    Users users;
    ArrayAdapter<String> arrayAdapter;
    Button change_pw, bt_yes, bt_no;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference,dfsendmessage;
    RecyclerView recyclerView;
    MyAdapter myAdapter;
    List<getmessages> listdata;
    FirebaseAuth firebaseAuth;
    Button logout,send;
    ArrayList<String> arrayList = new ArrayList<>();
    private FirebaseAuth.AuthStateListener authStateListener;
    EditText text_message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        change_pw = findViewById(R.id.id_changePassword);
        logout = findViewById(R.id.id_logout);
        recyclerView = findViewById(R.id.listviewdashboard);
        text_message=findViewById(R.id.id_insertmessage);
        send=findViewById(R.id.id_send);
        firebaseAuth=FirebaseAuth.getInstance();



        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.HORIZONTAL));
        listdata=new ArrayList<>();
        myAdapter=new MyAdapter(listdata);
        firebaseDatabase=FirebaseDatabase.getInstance();
        dfsendmessage=FirebaseDatabase.getInstance().getReference().child("Users");
        GetFirebaseData();
        users=new Users();
        myAdapter.notifyItemRemoved(myAdapter.getItemCount());
        myAdapter.notifyDataSetChanged();
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String email = user.getEmail();
                String message=text_message.getText().toString();
                if(text_message.getText()!=null && !message.isEmpty()) {
                    users.setName(email);
                    users.setMessage(message);
                    dfsendmessage.push().setValue(users);
                    text_message.setText("");
                }

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(Dashboard.this);
                dialog.setContentView(R.layout.logout);
                bt_yes = dialog.findViewById(R.id.yes);
                bt_no = dialog.findViewById(R.id.no);
                dialog.show();
                bt_yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(Dashboard.this, Login.class));
                    }
                });
                bt_no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

            }
        });
        change_pw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dashboard.this, ChangePassword.class));
            }
        });

    }

    void GetFirebaseData(){
        databaseReference=firebaseDatabase.getReference("Users");
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    data=new getmessages();
                    data=dataSnapshot.getValue(getmessages.class);
                    listdata.add(data);
                    recyclerView.setAdapter(myAdapter);
                    recyclerView.smoothScrollToPosition(recyclerView.getBottom());
                    myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    @Override
    public void onBackPressed() {
        final Dialog dialog = new Dialog(Dashboard.this);
        dialog.setContentView(R.layout.exit);
        bt_yes = dialog.findViewById(R.id.yes);
        bt_no = dialog.findViewById(R.id.no);
        dialog.show();
        bt_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dashboard.this.finishAffinity();
            }
        });
        bt_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}
