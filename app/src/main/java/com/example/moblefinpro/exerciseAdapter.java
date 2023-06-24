package com.example.moblefinpro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moblefinpro.object.Exercise;

public class exerciseAdapter extends RecyclerView.Adapter<exerciseAdapter.ExerciseViewHolder> {

    Context context;
    ArrayList<Exercise> exercisesList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(View v, String key, String username, String judul, String kategori, String pertanyaan, String date, String star, String path);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public ArrayList<Exercise> getExercisesList() {
        return exercisesList;
    }

    public void setExercisesList(ArrayList<Exercise> exercisesList) {
        this.exercisesList = exercisesList;
    }

    public exerciseAdapter(Context context, ArrayList<Exercise> newList) {
        this.context = context;
        this.exercisesList = newList;
    }

    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercizelayout,parent,false);
        return new exerciseAdapter.ExerciseViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull exerciseAdapter.ExerciseViewHolder holder, int position) {
        holder.txtTitle.setText(exercisesList.get(position).getName());
        holder.txtTime.setText(exercisesList.get(position).getTime());
        holder.txtReps.setText(exercisesList.get(position).getRepetition()+" Reps");

    }

    @Override
    public int getItemCount() {
        return exercisesList.size();
    }

    public class ExerciseViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle, txtTime, txtReps;
        ExerciseViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.exercizelayout_title);
            txtTime = itemView.findViewById(R.id.exercizelayout_time);
            txtReps = itemView.findViewById(R.id.exercizelayout_reps);
        }
    }
}
