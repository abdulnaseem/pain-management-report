package com.example.reportpain;

import android.app.Activity;
import android.app.Application;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.annotation.StyleRes;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class SignUp extends AppCompatActivity {

    EditText fullName, dateOfB, weight, height, bmi, userName, passWord;
    Button genBMI, signUp;
    String tBMI;
    ImageView pop_up;
    UserDatabase usrData;

    protected void onCreate(Bundle savedInstanceState){
        setTheme(R.style.LoginTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(Color.parseColor("#1C7293")));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //set up action bar color and enable back button

        fullName = (EditText) findViewById(R.id.fullname);
        dateOfB = (EditText) findViewById(R.id.dob);
        weight = (EditText) findViewById(R.id.weight);
        height = (EditText) findViewById(R.id.height);
        genBMI = (Button) findViewById(R.id.genB);
        bmi = (EditText) findViewById(R.id.bmi);
        userName = (EditText) findViewById(R.id.username2);
        passWord = (EditText) findViewById(R.id.password2);
        signUp = (Button) findViewById(R.id.signup2);

        pop_up = (ImageView) findViewById(R.id.info);

        usrData = new UserDatabase(this);

        GenBMI();
        SignUp();
        pop_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayPopupWindow(v);
            }
        });
    }
    public void SignUp() {
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean checkIfEmpty = verifyEmptyFields(new EditText[] {fullName, dateOfB, weight,
                        height, bmi, userName, passWord});
                if(checkIfEmpty == false){
                    Toast.makeText(getApplicationContext(), "Please fill in all the details above.", Toast.LENGTH_SHORT).show();
                    //if any of the text fields are empty, then notify user
                }
                else {
                    checkIfUsrExists();
                }
            }
        });
    }
    public void checkIfUsrExists(){
        User user = new User();

        user.setFullname(fullName.getText().toString());
        user.setDateOfBirth(dateOfB.getText().toString());
        user.setWeight(weight.getText().toString());
        user.setHeight(height.getText().toString());
        user.setBmi(bmi.getText().toString());
        user.setUsername(userName.getText().toString());
        user.setPassword(passWord.getText().toString());
        //set all user fields
        boolean usrnmeValidation = usrData.validateUsername(user.getUsername());
        if (usrnmeValidation == true) {
            Toast.makeText(getApplicationContext(), "Username already exists.", Toast.LENGTH_SHORT).show();
        }
        else{

            boolean isInserted = usrData.insertData(user);
            if (isInserted = true) {
                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
            }
        }
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
        genBMI.setOnClickListener(new View.OnClickListener() {
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
    private void displayPopupWindow(View anchorView) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.popup, null);

        // create the popup window
        final PopupWindow popupWindow = new PopupWindow(layout, 650, 150, true);
        // dismiss the popup window when touched
        layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            popupWindow.setElevation(30);
        }
        popupWindow.showAtLocation(anchorView, Gravity.CENTER, 0, 0);
    }


    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

}