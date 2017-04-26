package com.example.paulholden.mymedicare;


        import android.Manifest;
        import android.content.SharedPreferences;
        import android.database.Cursor;
        import android.os.Bundle;
        import android.preference.PreferenceManager;
        import android.support.v4.app.ActivityCompat;
        import android.support.v7.app.AppCompatActivity;
        import android.telephony.SmsManager;
        import android.view.View;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Spinner;
        import android.widget.TextView;
        import android.widget.Toast;

/**

 */

public class RiskCalc extends AppCompatActivity {
    //variable to hold instance of aplication database
    public mediCareDB db;


    //first method ran in the application
    protected void onCreate(Bundle savedInstanceState) {
        //set db to value of application databse
        db = new mediCareDB(getBaseContext());
        //get permissions to send sms messages from user
        ActivityCompat.requestPermissions(RiskCalc.this, new String[]{Manifest.permission.SEND_SMS}, 1);

        super.onCreate(savedInstanceState);
        //set layout to id values of medical readings page
        setContentView(R.layout.activity_risk_calc);
        //create variable to hold value of spinner id for choosing temp type
        Spinner tempDropdown = (Spinner) findViewById(R.id.tempSpinner);
        //array to hold different temp types
        String[] items = new String[]{"°C", "°F"};
        //set spinner to contain values of items string above
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        //initiablise spinner
        tempDropdown.setAdapter(adapter);

        //create variables for all text view boxes
        TextView tempView = (TextView) findViewById(R.id.TempRiskView);
        TextView highBPView = (TextView) findViewById(R.id.HighBloodRisk);
        TextView lowBPView = (TextView) findViewById(R.id.LowBloodRisk);
        TextView hearRateView = (TextView) findViewById(R.id.PulseRisk);
        TextView TempDisp = (TextView) findViewById(R.id.RiskViewTemp);
        TextView highBPDisp = (TextView) findViewById(R.id.RiskViewHB);
        TextView lowBPDisp = (TextView) findViewById(R.id.RiskViewLB);
        TextView PulseDisp = (TextView) findViewById(R.id.RiskViewPulse);

        //set results text view boxes to invisible until information entered and button pressed
        tempView.setVisibility(View.INVISIBLE);
        highBPView.setVisibility(View.INVISIBLE);
        lowBPView.setVisibility(View.INVISIBLE);
        hearRateView.setVisibility(View.INVISIBLE);
        TempDisp.setVisibility(View.INVISIBLE);
        highBPDisp.setVisibility(View.INVISIBLE);
        lowBPDisp.setVisibility(View.INVISIBLE);
        PulseDisp.setVisibility(View.INVISIBLE);


        //create variable for results button
        Button results = (Button) findViewById(R.id.resultsBtn);
        //action listener to run if button is clicked
        results.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //try and catch to prevent possible errors
                try {
                    //setup variables for all text fields for input values
                    EditText Temp = (EditText) findViewById(R.id.TempTxt);
                    EditText Hibp = (EditText) findViewById(R.id.BloodPressureHigh);
                    EditText Lobp = (EditText) findViewById(R.id.LowBloodPresure);
                    EditText Pulse = (EditText) findViewById(R.id.Pulse);
                    //setup variable to hold spinner id value holding temp type selected
                    Spinner tempDropdown = (Spinner) findViewById(R.id.tempSpinner);

                    //variables hold the values of all data input to text views
                    double temperatInt = Integer.parseInt(Temp.getText().toString());
                    int HighbloodPresureInt = Integer.parseInt(Hibp.getText().toString());
                    int LowbloodPresureInt = Integer.parseInt(Lobp.getText().toString());
                    int hearRateInt = Integer.parseInt(Pulse.getText().toString());
                    String degreeOrFar = (String) tempDropdown.getSelectedItem();
                    //set up strings to holds results value of calculations
                    String verdictTemp = null;
                    String verdictHBP = null;
                    String verdictLBP = null;
                    String verdictHR = null;
                    //if the degrees value is in fahrenhight
                    if (degreeOrFar.equals("°F")) {

                        //follow statistic struction to determin where value sits
                        if (temperatInt > 100.4) {
                            verdictTemp = "HIGH";
                        } else if (temperatInt <= 100.4 && temperatInt > 98.6) {
                            verdictTemp = "LOW";
                        } else if (temperatInt <= 98.6) {
                            verdictTemp = "NORMAL";
                        }
                        //follow statistic struction to determin where value sits
                        if (HighbloodPresureInt >= 180) {
                            verdictHBP = "HIGH";
                        } else if (HighbloodPresureInt < 180 && HighbloodPresureInt > 120) {
                            verdictHBP = "LOW";
                        } else if (HighbloodPresureInt <= 120) {
                            verdictHBP = "NORMAL";
                        }
                        //follow statistic struction to determin where value sits
                        if (LowbloodPresureInt >= 110) {
                            verdictLBP = "HIGH";
                        } else if (LowbloodPresureInt < 110 && LowbloodPresureInt > 80) {
                            verdictLBP = "LOW";
                        } else if (LowbloodPresureInt <= 80) {
                            verdictLBP = "NORMAL";
                        }
                        //follow statistic struction to determin where value sits
                        if (hearRateInt > 160) {
                            verdictHR = "HIGH";
                        } else if (hearRateInt <= 160 && hearRateInt >= 72) {
                            verdictHR = "LOW";
                        } else if (hearRateInt < 72) {
                            verdictHR = "NORMAL";
                        }

                        //if the degrees value is in celcius
                    } else if (degreeOrFar.equals("°C")) {
                        //follow statistic struction to determin where value sits
                        if (temperatInt > 38) {
                            verdictTemp = "HIGH";
                        } else if (temperatInt <= 38 && temperatInt > 37) {
                            verdictTemp = "LOW";
                        } else if (temperatInt <= 37) {
                            verdictTemp = "NORMAL";
                        }
                        //follow statistic struction to determin where value sits
                        if (HighbloodPresureInt >= 180) {
                            verdictHBP = "HIGH";
                        } else if (HighbloodPresureInt < 180 && HighbloodPresureInt > 120) {
                            verdictHBP = "LOW";
                        } else if (HighbloodPresureInt <= 120) {
                            verdictHBP = "NORMAL";
                        }
                        //follow statistic struction to determin where value sits
                        if (LowbloodPresureInt >= 110) {
                            verdictLBP = "HIGH";
                        } else if (LowbloodPresureInt < 110 && LowbloodPresureInt > 80) {
                            verdictLBP = "LOW";
                        } else if (LowbloodPresureInt <= 80) {
                            verdictLBP = "NORMAL";
                        }
                        //follow statistic struction to determin where value sits
                        if (hearRateInt > 160) {
                            verdictHR = "HIGH";
                        } else if (hearRateInt <= 160 && hearRateInt > 72) {
                            verdictHR = "LOW";
                        } else if (hearRateInt <= 72) {
                            verdictHR = "NORMAL";
                        }
                    }
                    //create variables for all text view boxes
                    TextView tempView = (TextView) findViewById(R.id.TempRiskView);
                    TextView highBPView = (TextView) findViewById(R.id.HighBloodRisk);
                    TextView lowBPView = (TextView) findViewById(R.id.LowBloodRisk);
                    TextView hearRateView = (TextView) findViewById(R.id.PulseRisk);
                    TextView TempDisp = (TextView) findViewById(R.id.RiskViewTemp);
                    TextView highBPDisp = (TextView) findViewById(R.id.RiskViewHB);
                    TextView lowBPDisp = (TextView) findViewById(R.id.RiskViewLB);
                    TextView PulseDisp = (TextView) findViewById(R.id.RiskViewPulse);

                    //make text values visible to display results to user
                    tempView.setVisibility(View.VISIBLE);
                    highBPView.setVisibility(View.VISIBLE);
                    lowBPView.setVisibility(View.VISIBLE);
                    hearRateView.setVisibility(View.VISIBLE);
                    TempDisp.setVisibility(View.VISIBLE);
                    highBPDisp.setVisibility(View.VISIBLE);
                    lowBPDisp.setVisibility(View.VISIBLE);
                    PulseDisp.setVisibility(View.VISIBLE);

                    //setting text to the values of the verdict of calculation
                    TempDisp.setText(verdictTemp);
                    highBPDisp.setText(verdictHBP);
                    lowBPDisp.setText(verdictLBP);
                    PulseDisp.setText(verdictHR);

                    String tempstr = Temp.getText().toString();
                    String hibpstr = Hibp.getText().toString();
                    String lobpstr = Lobp.getText().toString();
                    String pulsestr = Pulse.getText().toString();

                    Calculations ca = new Calculations();
                    ca.setTemp(tempstr);
                    ca.setHighbp(hibpstr);
                    ca.setLowBP(lobpstr);
                    ca.setPulse(pulsestr);

                    db.open();
                    db.inserCalculations(ca);
                    db.close();


                    //if any of the verdicts are HIGH send text to GP
                    if (verdictTemp.equals("HIGH") || verdictLBP.equals("HIGH") || verdictHBP.equals("HIGH") || verdictHR.equals("HIGH")) {

                        try {
                            // open the database
                            db.open();
                            // get value of shared preferences file for line number
                            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(RiskCalc.this);
                            int rowValue = prefs.getInt("posValue", 0);
                            Toast.makeText(getBaseContext(), "rowVal= " + rowValue, Toast.LENGTH_LONG).show();
                            // get data values for current users data row
                            Cursor c = db.getAccount(rowValue);
                            // if a GP number exists, send message
                            if (!c.getString(5).toString().equals(null) || !c.getString(5).toString().equals("")) {
                                //compile warning message which includes all readings
                                String textMsg = (c.getString(1) + " has high readings: Temperature = " + temperatInt + " " + verdictTemp + ", High Blood Pressure: " + HighbloodPresureInt + " " + verdictHBP + ", Low Blood Pressure: " + LowbloodPresureInt + " " + verdictLBP + ", Heart Rate: " + hearRateInt + " " + verdictHR);
                                //tell user a message has been sent to the GP
                                Toast.makeText(getBaseContext(), "a message has been sent to your GP warning them of HIGH RISK reading", Toast.LENGTH_LONG).show();
                                //open new instance of messanger applicaiton
                                SmsManager sms = SmsManager.getDefault();
                                //send message to GP's number with textMsg string
                                sms.sendTextMessage(c.getString(5), null, textMsg, null, null);
                            }
                            //if GP's number does not exist then tell user to add number and try again.
                            else {
                                Toast.makeText(getBaseContext(), "Please enter GP information on 'Your Details' page then re-enter this information", Toast.LENGTH_LONG).show();
                            }
                            //close databsae
                            db.close();
                        }
                        catch(Exception e){
                            Toast.makeText(getBaseContext(), "Message Failed to Send", Toast.LENGTH_LONG).show();
                        }

                    }
                    //if error occurs and user does not add values to text fields
                } catch (Exception e) {

                    Toast.makeText(getBaseContext(), "Please Use Int Values", Toast.LENGTH_LONG).show();

                }
            }
        });
    }

}
