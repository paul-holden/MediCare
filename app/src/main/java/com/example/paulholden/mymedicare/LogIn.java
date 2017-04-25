package com.example.paulholden.mymedicare;

import android.content.Intent;
import android.database.CursorIndexOutOfBoundsException;
import android.database.SQLException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LogIn extends AppCompatActivity {
    public mediCareDB DBHelper;
    //Button b1;
    Button b2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        DBHelper = new mediCareDB(getBaseContext());

        setContentView(R.layout.activity_log_in);

        final Button b1 = (Button) findViewById(R.id.LogInBtn);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.LogInBtn) {
                    EditText a = (EditText) findViewById(R.id.editEmail);
                    String Uname = a.getText().toString();
                    EditText b = (EditText) findViewById(R.id.editPassword);
                    String Pass = b.getText().toString();
                    try{
                    DBHelper.open();
                    String password = DBHelper.searchPass(Uname);
                    DBHelper.close();

                    if (password.equals(Pass)) {

                        Intent i = new Intent(LogIn.this, Menu.class);
                        startActivity(i);
                    } else {
                        Toast.makeText(LogIn.this, "Password or user name is not correct", Toast.LENGTH_SHORT).show();
                    } }catch (CursorIndexOutOfBoundsException c){
                        Toast.makeText(LogIn.this, "PLZ REG Email", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        b2 = (Button) findViewById(R.id.NewAccBtn);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(LogIn.this, SignUp.class);
                startActivity(i1);
            }
        });
}

}
