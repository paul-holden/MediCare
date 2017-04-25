package com.example.paulholden.mymedicare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {
    public mediCareDB DB;
    Button b1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DB = new mediCareDB(getBaseContext());
        setContentView(R.layout.activity_sign_up);

       b1 = (Button) findViewById(R.id.SignUp);



       b1.setOnClickListener(new View.OnClickListener() {
          @Override
            public void onClick(View v) {
              EditText name = (EditText) findViewById(R.id.Uname);
              EditText Uname = (EditText) findViewById(R.id.ScrName);
              EditText email = (EditText) findViewById(R.id.EmailEnterTxt);
              EditText pass1 = (EditText) findViewById(R.id.PasswordText);
              EditText pass2 = (EditText) findViewById(R.id.ConfirmPasswordText);
              EditText nurseNumber = (EditText) findViewById(R.id.NurseNumber);

              String nameStr = name.getText().toString();
              String UnameStr = Uname.getText().toString();
              String emailStr = email.getText().toString();
              String pass1Str = pass1.getText().toString();
              String pass2Str = pass2.getText().toString();
              String nurseNumberStr = nurseNumber.getText().toString();


                if (!pass1Str.equals(pass2Str))
                {
                    Toast.makeText(SignUp.this , "Passwords Dont match!" , Toast.LENGTH_SHORT).show();

                }
              else
                {
                    User u = new User();
                    u.setName(nameStr);
                    u.setUName(UnameStr);
                    u.setEmail(emailStr);
                    u.setPWord(pass1Str);
                    u.setNurseNum(nurseNumberStr);

                    DB.open();
                    DB.insertUser(u);
                    DB.close();

                    Toast.makeText(SignUp.this , "Account created" , Toast.LENGTH_SHORT).show();
                    Intent i = new Intent (SignUp.this, LogIn.class);
                    startActivity(i);

                }
            }
        });
    }
}
