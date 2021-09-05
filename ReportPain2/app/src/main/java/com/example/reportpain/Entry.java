package com.example.reportpain;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Entry extends AppCompatActivity {

    TextView question3;
    Spinner location, feeling, level;
    EditText comment;
    Button sub;

    protected void onCreate(Bundle bundle){
        setTheme(R.style.entry);
        super.onCreate(bundle);
        setContentView(R.layout.entry);

        question3 =(TextView) findViewById(R.id.question3);
        question3.setText("How painful is it on a scale from 1 to 10? (1 being the least and 10 being the most).");

        location = (Spinner) findViewById(R.id.location);
        feeling = (Spinner) findViewById(R.id.feeling);
        level = (Spinner) findViewById(R.id.level);
        comment = (EditText) findViewById(R.id.comment);
        sub = (Button) findViewById(R.id.submit);

        setLocation();
        setFeeling();
        setLevel();

        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
                Intent intent = new Intent(Entry.this, PersonalDD.class);
                startActivity(intent);
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
        /*
         * set up and display the dropdown list
         */
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
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("E, MMM dd yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        //get the current date and time and set the format
        String dt = String.valueOf(date.format(dateFormatter));
        String tm = String.valueOf(date.format(timeFormatter));
        //convert date and time to string type
        db.insertData(dt,tm, location.getSelectedItem().toString(), feeling.getSelectedItem().toString(),
                lev, comment.getText().toString());
    }
    public void submit(){

        boolean checkIfSelected = verifyEmptyFields(new Spinner[]{location, feeling, level});
        if(!checkIfSelected){
            //if the user has not selected an item from dropdown list:
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
}
