package com.example.moblefinpro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class exerciseAdapter extends RecyclerView.Adapter<exerciseLayout> {


    Context context;
    List<Item> items;

    public exerciseAdapter(Context context, List<Item> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public exerciseLayout onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        return new exerciseLayout(LayoutInflater.from(context).inflate(R.layout.exercizelayout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull exerciseLayout holder, int position) {
        holder.nameView.setText(items.get(position).getName());
        holder.repstitles.setText(items.get(position).getReps());
        holder.hourtitle.setText(items.get(position).getduration());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}