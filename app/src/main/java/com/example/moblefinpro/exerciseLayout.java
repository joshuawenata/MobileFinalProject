package com.example.moblefinpro;

import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class exerciseLayout extends RecyclerView.ViewHolder {
    TextView nameView,repstitles,hourtitle;
    Switch exswitch;

    public exerciseLayout(@NonNull View itemView) {
        super(itemView);
        nameView = itemView.findViewById(R.id.exercizelayout_restitle);
        repstitles = itemView.findViewById(R.id.exercizelayout_repstext);
        hourtitle = itemView.findViewById(R.id.exercizelayout_hour);
    }
}