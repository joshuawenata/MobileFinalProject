package com.example.moblefinpro;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moblefinpro.object.Exercise;
import com.example.moblefinpro.receiver.notificationReceiver;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class exerciseAdapter extends RecyclerView.Adapter<exerciseAdapter.ExerciseViewHolder> {

    Context context;
    ArrayList<Exercise> exercisesList;

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

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull exerciseAdapter.ExerciseViewHolder holder, int position) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        Switch mySwitch = holder.itemView.findViewById(R.id.exercizelayout_switch);

        DatabaseReference myRef = database.getReference().child("Users").
                child(firebaseUser.getUid()).child("exercise");
        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    myRef.child(exercisesList.get(position).getKey()).child("status").setValue(1);
                    Intent intent = new Intent(exerciseAdapter.this.context, notificationReceiver.class);

                    intent.putExtra("notifType", "Exercise");
                    intent.putExtra("message", exercisesList.get(position).getName().toLowerCase());
                    intent.putExtra("activity", "exercise");

                    PendingIntent alarmIntent = PendingIntent.getBroadcast(exerciseAdapter.this.context, 1,
                            intent, PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_IMMUTABLE);

                    AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

                    String time = exercisesList.get(position).getTime();
                    String[] timeArray = time.split(":");

                    Integer hour = Integer.parseInt(timeArray[0]);
                    Integer minute = Integer.valueOf(timeArray[1]);

                    // Create time
                    Calendar startTime = Calendar.getInstance();
                    startTime.set(Calendar.HOUR_OF_DAY, hour);
                    startTime.set(Calendar.MINUTE, minute);
                    startTime.set(Calendar.SECOND, 0);

                    if (startTime.getTimeInMillis() <= System.currentTimeMillis()) {
                        // Add a day to the alarm time
                        startTime.add(Calendar.DAY_OF_MONTH, 1);
                    }

                    long alarmStartTime = startTime.getTimeInMillis();

                    // Set alarm
                    alarm.set(AlarmManager.RTC_WAKEUP, alarmStartTime, alarmIntent);

                    Toast.makeText(exerciseAdapter.this.context, "Exercise reminder has been set.", Toast.LENGTH_SHORT).show();

                } else {
                    myRef.child(exercisesList.get(position).getKey()).child("status").setValue(0);
                }
            }
        });

        holder.txtTitle.setText(exercisesList.get(position).getName());
        holder.txtTime.setText(exercisesList.get(position).getTime());
        holder.txtReps.setText(exercisesList.get(position).getRepetition() + " Reps");

        if(exercisesList.get(position).getStatus() == 0) {
            holder.txtStatus.setChecked(false);
        } else {
            holder.txtStatus.setChecked(true);
        }
    }

    @Override
    public int getItemCount() {
        return exercisesList.size();
    }

    public class ExerciseViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle, txtTime, txtReps;
        Switch txtStatus;
        ExerciseViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.exercizelayout_title);
            txtTime = itemView.findViewById(R.id.exercizelayout_time);
            txtReps = itemView.findViewById(R.id.exercizelayout_reps);
            txtStatus = itemView.findViewById(R.id.exercizelayout_switch);
        }
    }
}