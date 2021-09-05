package com.example.reportpain;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder>{

    private Context context;
    private ArrayList pain_loc, pain_feel, pain_lev, add_comment, date, time, id;
    int positions;
    Activity activity;

    CustomAdapter(Activity activity, Context context, ArrayList id, ArrayList date, ArrayList time,
                  ArrayList pain_loc, ArrayList pain_feel, ArrayList pain_lev, ArrayList add_comment){
        //pass through arraylist variables from PersonalDD class
        this.activity = activity;
        this.context = context;
        this.id = id;
        this.date = date;
        this.time = time;
        this.pain_loc = pain_loc;
        this.pain_feel = pain_feel;
        this.pain_lev = pain_lev;
        this.add_comment = add_comment;

    }
    @NonNull
    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.diary_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder holder, int position) {
        this.positions = position;
        //set the fields with the recycler from getting the position of arraylist
        //holder.id_txt.setText("ID: " + String.valueOf(id.get(position)));
        holder.pain_loc_txt.setText("Location: " + String.valueOf(pain_loc.get(position)));
        holder.pain_feel_txt.setText("Feel: " + String.valueOf(pain_feel.get(position)));
        holder.pain_lev_txt.setText("Level: " + String.valueOf(pain_lev.get(position)));
        holder.add_comm_txt.setText("Comment: \n" + String.valueOf(add_comment.get(position)));
        holder.date.setText(String.valueOf(date.get(position)));
        holder.time.setText(String.valueOf(time.get(position)));
        holder.row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateDiary.class);
                //used to edit an entry, pass data to UpdateDiary class
                intent.putExtra("id", String.valueOf(id.get(position)));
                intent.putExtra("date", String.valueOf(date.get(position)));
                intent.putExtra("time", String.valueOf(time.get(position)));
                intent.putExtra("pain location", String.valueOf(pain_loc.get(position)));
                intent.putExtra("pain feeling", String.valueOf(pain_feel.get(position)));
                intent.putExtra("pain level", String.valueOf(pain_lev.get(position)));
                intent.putExtra("comment", String.valueOf(add_comment.get(position)));
                activity.startActivityForResult(intent, 1);
            }
        });


    }

    @Override
    public int getItemCount() {
        return id.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView pain_loc_txt, pain_feel_txt, pain_lev_txt, add_comm_txt, date, time, id_txt;
        RelativeLayout row;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            //set the recycler view fields
            //id_txt = itemView.findViewById(R.id.id);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
            pain_loc_txt = itemView.findViewById(R.id.painLoc);
            pain_feel_txt = itemView.findViewById(R.id.painFeel);
            pain_lev_txt = itemView.findViewById(R.id.painLev);
            add_comm_txt = itemView.findViewById(R.id.addComm);
            row = itemView.findViewById(R.id.row);


        }
    }
}
