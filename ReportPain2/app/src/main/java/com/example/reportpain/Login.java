package com.example.reportpain;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity {

    EditText userName;
    EditText passWord;
    Button signIn;
    Button signUp;

    UserDatabase usrData;
    //User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.LoginTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        userName = (EditText) findViewById(R.id.username);
        passWord = (EditText) findViewById(R.id.password);
        signIn = (Button) findViewById(R.id.signin);
        signUp = (Button) findViewById(R.id.signup);

        usrData = new UserDatabase(this);

        Register();
        SignIn();
    }

    public void SignIn(){
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               boolean checkIfEmpty = verifyEmptyFields(new EditText[] {userName, passWord});

                if(checkIfEmpty == false){
                    Toast.makeText(getApplicationContext(), "Please fill in all the details above.",
                            Toast.LENGTH_SHORT).show();
                    //if any of the text fields are empty, then notify user
                }
                else {
                    userVerify();
                }
            }
        });
    }
    public void userVerify(){

        boolean chckUsr = usrData.validateUser(userName.getText().toString(), passWord.getText().toString());
        if (chckUsr == false) {
            Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
            Intent ha = new Intent(Login.this, HumanAnatomy.class);
            startActivity(ha);
        }
    }

    public void Register(){
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signup = new Intent(Login.this, SignUp.class);
                startActivity(signup);
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
}
