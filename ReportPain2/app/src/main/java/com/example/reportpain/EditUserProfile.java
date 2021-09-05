package com.example.reportpain;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditUserProfile extends AppCompatActivity {

    EditText fullname, dob, weight, height, bmi, username;
    Button save, genB;
    User user = new User();
    String tBMI;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.LoginTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_prof_edit);

        getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(Color.parseColor("#1C7293")));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fullname = (EditText)findViewById(R.id.fullname);
        dob = (EditText)findViewById(R.id.dob);
        weight = (EditText)findViewById(R.id.weight);
        height = (EditText)findViewById(R.id.height);
        bmi = (EditText)findViewById(R.id.bmi);
        username = (EditText)findViewById(R.id.username2);
        genB = (Button) findViewById(R.id.genB);
        save = (Button) findViewById(R.id.save);

        fullname.setText(user.getFullname());
        dob.setText(user.getDateOfBirth());
        weight.setText(user.getWeight());
        height.setText(user.getHeight());
        bmi.setText(user.getBmi());
        username.setText(user.getUsername());

        GenBMI();
        setSave();

    }
    public void setUpdateUser(){
        UserDatabase usrData = new UserDatabase(this);

        user.setFullname(fullname.getText().toString());
        user.setDateOfBirth(dob.getText().toString());
        user.setWeight(weight.getText().toString());
        user.setHeight(height.getText().toString());
        user.setBmi(bmi.getText().toString());

        usrData.updateUser(user);

    }
    public void setSave() {
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean checkIfEmpty = verifyEmptyFields(new EditText[] {fullname, weight,
                        height, bmi});
                if(checkIfEmpty == false){
                    Toast.makeText(getApplicationContext(), "Please fill in all the details above.", Toast.LENGTH_SHORT).show();
                    //if any of the text fields are empty, then notify user
                }
                else {
                    setUpdateUser();
                    Toast.makeText(getApplicationContext(), "Saved.", Toast.LENGTH_SHORT).show();
                    Intent userProfile = new Intent(EditUserProfile.this, UserProfile.class);
                    startActivity(userProfile);
                }
            }
        });
    }
    public boolean verifyEmptyFields(EditText[] f){
        for(int i = 0; i<f.length; i++){
            EditText currentField = f[i];
            if(currentField.getText().toString().length() <= 0){
                return false;
            }
        }
        return true;
        /* This method simply iterates through the
         * text fields to verify if they are empty.
         */
    }
    public void GenBMI(){
        genB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String eWeight = weight.getText().toString();
                String eHeight = height.getText().toString();

                if (eWeight.matches("") && eHeight.matches("")) {
                    Toast.makeText(getApplicationContext(), "Please enter your weight and height.", Toast.LENGTH_SHORT).show();
                    bmi.setText("BMI");
                }
                else if(eWeight.matches("")){
                    Toast.makeText(getApplicationContext(), "Please enter your weight.", Toast.LENGTH_SHORT).show();
                }
                else if (eHeight.matches("")) {
                    Toast.makeText(getApplicationContext(), "Please enter your height.", Toast.LENGTH_SHORT).show();
                }
                else {
                    calcBMI();
                    bmi.setText(tBMI);
                }
                /* If either or both weight & height fields are empty, tell user
                 * to fill those fields in.
                 * Call the method once the user generates the bmi
                 * and set the text to calculate the bmi.
                 */
            }
        });
    }
    public String calcBMI(){
        double wBmi = Double.parseDouble(weight.getText().toString());
        double hBmi = Double.parseDouble(height.getText().toString());
        //get weight and height in string and convert it to double type

        double cBmi = wBmi/(hBmi*hBmi);
        String twoDec = String.format("%.2f", cBmi);
        tBMI = String.valueOf(twoDec);
        //calculate the bmi, make number format as 2 decimal places, convert it back to string
        return tBMI;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
