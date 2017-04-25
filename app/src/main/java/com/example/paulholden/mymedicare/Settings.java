package com.example.paulholden.mymedicare;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Settings extends AppCompatActivity {
    mediCareDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = new mediCareDB(this);

        setContentView(R.layout.activity_settings);

        Button SaveSettings = (Button)findViewById(R.id.SaveSettingBtn);
        SaveSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // get value of shared preferences file for line number
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(Settings.this);
                int rowValue = prefs.getInt("posValue", 0);
                Toast.makeText(getBaseContext(), "rowVal= " + rowValue, Toast.LENGTH_LONG).show();



                EditText NewNumber = (EditText)findViewById(R.id.NewNumberEdit);
                String NewNumberStr = NewNumber.getText().toString();

                db.open();
                Cursor c = db.getAccount(rowValue);
                db.DBupdate(c.getString(4),c.getString(1) , c.getString(3), c.getString(2),NewNumberStr ,rowValue);



                // get data values for current users data row

                db.close();

            }
        });

        Button DeleteAcc = (Button)findViewById(R.id.DeleteAccountBtn);
        DeleteAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(Settings.this);
                int rowValue = prefs.getInt("posValue", 0);
                Toast.makeText(getBaseContext(), "rowVal= " + rowValue, Toast.LENGTH_LONG).show();

                db.open();
                db.delete_byID(rowValue);
                db.close();
                Intent login = new Intent(Settings.this, LogIn.class);
                startActivity(login);
            }
        });

    }

}
