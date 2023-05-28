package com.example.moblefinpro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class excersizeAdapter extends RecyclerView.Adapter<excersizeLayout> {


    Context context;
    List<Item> items;

    public excersizeAdapter(Context context, List<Item> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public excersizeLayout onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        return new excersizeLayout(LayoutInflater.from(context).inflate(R.layout.exercizelayout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull  excersizeLayout holder, int position) {
        holder.nameView.setText(items.get(position).getName());
        holder.repstitles.setText(items.get(position).getReps());
        holder.hourtitle.setText(items.get(position).getduration());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}