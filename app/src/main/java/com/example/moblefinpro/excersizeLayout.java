package com.example.moblefinpro;

import android.media.Image;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class excersizeLayout extends RecyclerView.ViewHolder {
    TextView nameView,repstitles,hourtitle;
    Switch exswitch;

    public excersizeLayout(@NonNull View itemView) {
        super(itemView);
        nameView = itemView.findViewById(R.id.exercizelayout_restitle);
        repstitles = itemView.findViewById(R.id.exercizelayout_repstext);
        hourtitle = itemView.findViewById(R.id.exercizelayout_hour);
    }
}