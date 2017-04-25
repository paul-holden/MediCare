package com.example.paulholden.mymedicare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Menu extends AppCompatActivity {
    Button b1;
    Button b2;
    Button b3;
    Button b4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        b1 = (Button) findViewById(R.id.RiskCalcBtn);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent (Menu.this, RiskCalc.class);
                startActivity(i);
            }
        });
        b2 = (Button) findViewById (R.id.PastMesumentsBtn);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent (Menu.this, PastCalculations.class);
                startActivity(i);
            }
        });
        b3 = (Button) findViewById(R.id.SettingsBtn);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent (Menu.this, Settings.class);
                startActivity(i);
            }
        });
        b4 = (Button) findViewById(R.id.SignOutBtn);
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent (Menu.this, LogIn.class);
                startActivity(i);
            }
        });

    }
}
