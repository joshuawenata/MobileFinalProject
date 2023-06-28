package com.example.moblefinpro;

import static androidx.core.content.ContextCompat.getSystemService;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moblefinpro.object.Exercise;
import com.example.moblefinpro.receiver.notificationReceiver;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class exerciseAdapter extends RecyclerView.Adapter<exerciseAdapter.ExerciseViewHolder> {

    Context context;
    ArrayList<Exercise> exercisesList;
    ArrayList<String> keys = new ArrayList<>();
    private OnItemClickListener mListener;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser firebaseUser = mAuth.getCurrentUser();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference().child("Users").child(firebaseUser.getUid()).
            child("exercise");

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

    public exerciseAdapter(Context context, ArrayList<Exercise> newList, ArrayList<String> keys) {
        this.context = context;
        this.exercisesList = newList;
        this.keys = keys;
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

        switch(exercisesList.get(holder.getAdapterPosition()).getStatus()) {
            case 0:
                holder.exerciseSwitch.setChecked(false);
                break;
            case 1:
                holder.exerciseSwitch.setChecked(true);
                break;
        }

        holder.exerciseSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    exercisesList.get(holder.getAdapterPosition()).setStatus(1);
                    myRef.child(keys.get(holder.getAdapterPosition())).child("status").setValue(1);

                    // logd for debugging
                    Log.d("DBKey", "onCheckedChanged: Key string: " + keys.get(holder.getAdapterPosition()) +
                            " at index: " + holder.getAdapterPosition());
                    Log.d("Status", "onCheckedChanged: List status: " +
                            exercisesList.get(holder.getAdapterPosition()).getStatus() + " at index: " + holder.getAdapterPosition());
                }
                else {
                    exercisesList.get(holder.getAdapterPosition()).setStatus(0);
                    myRef.child(keys.get(holder.getAdapterPosition())).child("status").setValue(0);

                    // logd for debugging
                    Log.d("DBKey", "onCheckedChanged: Key string: " + keys.get(holder.getAdapterPosition()) +
                            " at index: " + holder.getAdapterPosition());
                    Log.d("Status", "onCheckedChanged: List status: " +
                            exercisesList.get(holder.getAdapterPosition()).getStatus() + " at index: " + holder.getAdapterPosition());
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return exercisesList.size();
    }

    public static class ExerciseViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle, txtTime, txtReps;
        Switch exerciseSwitch;
        ExerciseViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.exercizelayout_title);
            txtTime = itemView.findViewById(R.id.exercizelayout_time);
            txtReps = itemView.findViewById(R.id.exercizelayout_reps);
            exerciseSwitch = itemView.findViewById(R.id.exercizelayout_switch);
        }
    }
}
