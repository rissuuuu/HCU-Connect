package com.example.hcuconnect;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hcu.uohonnect.R;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    List<getmessages> listarray;

    public MyAdapter(List<getmessages> list) {
        this.listarray = list;
    }

    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.chatbox,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position) {
        getmessages getm=listarray.get(position);
        holder.textView.setText(getm.getName());
        holder.textView1.setText(getm.getMessage());
    }

    @Override
    public int getItemCount() {
        return  listarray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView,textView1;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.id_Messagename);
            textView1=itemView.findViewById(R.id.id_message);
        }
    }
}
