package com.example.reportpain;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UpdateDiary extends AppCompatActivity {
    Spinner location, feeling, level;
    EditText comment;
    Button save, delete;
    TextView question3;
    String date, time, id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.entry);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_diary);

        location = (Spinner) findViewById(R.id.location2);
        feeling = (Spinner) findViewById(R.id.feeling2);
        level = (Spinner) findViewById(R.id.level2);
        comment = (EditText) findViewById(R.id.comment2);
        save = (Button) findViewById(R.id.save);
        delete = (Button) findViewById(R.id.delete);

        question3 =(TextView) findViewById(R.id.question3);
        question3.setText("How painful is it on a scale from 1 to 10? (1 being the least and 10 being the most).");


        setLocation();
        setFeeling();
        setLevel();


        setSave();
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog();
            }
        });
        //db.updateData();
    }
    public void setSave(){
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
                finish();
            }
        });
    }

    public void setLocation(){
        String[] loc = new String[]{"Select...", "Head", "Neck", "Back", "Back (Upper Right)", "Back (Upper Left)",
                "Back (Lower Right)", "Back (Lower Left)", "Chest", "Chest (Upper Right)", "Chest (Upper Left)",
                "Chest (Lower Right)", "Chest (Lower Left)", "Abdominal (Upper Right)", "Abdominal (Upper Left)",
                "Abdominal (Lower Right)", "Abdominal (Lower Left)", "Shoulder (Right)", "Shoulder (Left)",
                "Arms (Upper Right)", "Arms (Forearm Right)", "Arms (Upper Left)", "Arms (Forearm Left)",
                "Hands (Right)", "Hands (Left)", "Legs (Right, Inner Thighs)", "Legs (Right, Outer Thighs)",
                "Legs (Right Calves)", "Legs (Left, Inner Thighs)", "Legs (Left, Outer Thighs)", "Legs (Left Calves)",
                "Feet (Right)", "Feet (Left)"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, loc);
        location.setAdapter(adapter);

    }
    public void setFeeling(){
        String[] feel = new String[]{"Select...", "Burning", "Sharp", "Dull", "Intense", "Aching",
                "Cramping", "Shooting", "Stabbing", "Gnawing", "Gripping", "Pressure", "Heavy",
                "Tender", "Prickly", "Stinging"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, feel);
        feeling.setAdapter(adapter);
    }
    public void setLevel(){
        String[] lev = new String[]{"Select...", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, lev);
        level.setAdapter(adapter);
    }
    public void addEntry(){
        DiaryDatabase db = new DiaryDatabase(this);
        int lev = Integer.parseInt(level.getSelectedItem().toString());
        if(getIntent().hasExtra("date")&&getIntent().hasExtra("time")) {

            date = getIntent().getStringExtra("date");
            time = getIntent().getStringExtra("time");
            id = getIntent().getStringExtra("id");
            db.updateData(id, date, time,  location.getSelectedItem().toString(),
                    feeling.getSelectedItem().toString(), lev, comment.getText().toString());
        }
        else{
            Toast.makeText(getApplicationContext(), "No data.", Toast.LENGTH_SHORT).show();
        }
    }
    public void save(){

        boolean checkIfSelected = verifyEmptyFields(new Spinner[]{location, feeling, level});
        if(!checkIfSelected){
            Toast.makeText(getApplicationContext(), "Please select appropriate fields.", Toast.LENGTH_SHORT).show();
        }
        else{
            if(comment.getText().toString().matches("")){
                comment.setText("N/A");
            }
            addEntry();
            Toast.makeText(getApplicationContext(), "Submitted.", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean verifyEmptyFields(Spinner[] f){
        for(int i = 0; i<f.length; i++){
            Spinner currentField = f[i];
            if(currentField.getSelectedItem().toString().equals("Select...")){
                return false;
            }
        }
        return true;
        /* This method simply iterates through the
         * text fields to verify if they are empty.
         */
    }
    public void confirmDialog(){
        //dialog to suggest whether user wants to delete an entry
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete an Entry.");
        builder.setMessage("Are you sure you want to delete the current entry?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DiaryDatabase db = new DiaryDatabase(UpdateDiary.this);
                id = getIntent().getStringExtra("id");
                db.deleteRow(id);
                finish();
                /*Intent intent = new Intent(UpdateDiary.this, PersonalDD.class);
                startActivity(intent);*/
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }

}
