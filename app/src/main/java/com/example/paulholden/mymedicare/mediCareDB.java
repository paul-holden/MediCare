package com.example.paulholden.mymedicare;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import static android.content.ContentValues.TAG;

/**
 * Created by paul on 23/04/2017.
 */

public class mediCareDB{

    //columns of the users table
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_USER_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_USERNAME = "uname";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "pass";
    private static final String COLUMN_NURSENUMBER = "nursenum";

    //columns of the calculations table
    private static final String TABLE_CALCULATIONS = "calculations";
    private static final String COLUMN_CALCULATION_ID = COLUMN_USER_ID;
    private static final String COLUMN_TEMP = "temp";
    private static final String COLUMN_HBP = "hbp";
    private static final String COLUMN_LBP = "lbp";
    private static final String COLUMN_PULSE = "pulse";
    private static final String COLUMN_USER_CALCULATION_ID ="user_id";

    private static final String DATABASE_NAME = "mymedicare";
    private static final int DATABASE_VERSION =1;


    private static final String TABLE_CREATE_USERS = "create table " + TABLE_USERS + "("
            + COLUMN_USER_ID + " integer primary key autoincrement, "
            + COLUMN_NAME + " text not null, "
            + COLUMN_USERNAME + " text not null, "
            + COLUMN_EMAIL + " text not null, "
            + COLUMN_PASSWORD + " text not null, "
            + COLUMN_NURSENUMBER + " integer not null "
            +");";


    private static final String TABLE_CREATE_RISK = "create table" + TABLE_CALCULATIONS + "("
            + COLUMN_CALCULATION_ID + "integer primary key autoincrement, "
            + COLUMN_TEMP + " text not null, "
            + COLUMN_HBP + " text not null, "
            + COLUMN_LBP + " text not null, "
            + COLUMN_PULSE + " text not null, "
            + COLUMN_USER_CALCULATION_ID + " integer not null "
            +");";

    //variables for holding database context, helper and SQLite instances
    private final Context context;
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    //method to create instance of medicareDB
    public mediCareDB(Context ctx)
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        //generate instance of database helper with defined database name and version
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        //first method ran on load of class
        @Override
        public void onCreate(SQLiteDatabase db){
            //try and catch used to avoid errors
            try {
            //execute SQL command within database
                db.execSQL(TABLE_CREATE_USERS);
                db.execSQL(TABLE_CREATE_RISK);
            //Log.d("tag", "Twas Created");
            } catch (SQLException e) {
            //print exception error
            //   Log.d("tag", "NoDatabase Created");

                e.printStackTrace();
            }
        }

        //if the database is updated, wipe it to prevent conflicts
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");

            //clear tables
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
            db.execSQL("DROP TABLE IF EXISTS" + TABLE_CALCULATIONS);

            //recreate tables
            onCreate(db);
        }
    }

    //open database
    public mediCareDB open() throws SQLException {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    //close the database
    public void close() {
        DBHelper.close();
    }



    public void insertUser(User u)
    {
        ContentValues valuesu = new ContentValues();


        valuesu.put(COLUMN_EMAIL, u.getEmail());
        valuesu.put(COLUMN_NAME, u.getName());
        valuesu.put(COLUMN_NURSENUMBER, u.getNurseNum());
        valuesu.put(COLUMN_PASSWORD, u.getPWord());
        valuesu.put(COLUMN_USERNAME, u.getUName());

        db.insert(TABLE_USERS, null, valuesu);
    }

    public void inserCalculations(Calculations c)
    {
        ContentValues valuesC = new ContentValues();

        valuesC.put(COLUMN_TEMP, c.getTemp());
        valuesC.put(COLUMN_HBP, c.getHighbp());
        valuesC.put(COLUMN_LBP, c.getHighbp());
        valuesC.put(COLUMN_PULSE, c.getPulse());

        db.insert(TABLE_CALCULATIONS, null, valuesC);
    }

    public void DBupdate(String email, String name, String uname, String pass, String nurseNumber, int row){

        ContentValues newValues = new ContentValues();

        newValues.put(COLUMN_EMAIL, email);
        newValues.put(COLUMN_NAME, name);
        newValues.put(COLUMN_NURSENUMBER,nurseNumber);
        newValues.put(COLUMN_PASSWORD , pass);
        newValues.put(COLUMN_USERNAME, uname);

        db.update(TABLE_USERS, newValues, COLUMN_USER_ID + " = " + row, null);
    }

    public void delete_byID(int row)
    {
        db.delete(TABLE_USERS, COLUMN_USER_ID + " = " + row, null);
    }

    public  String searchPass(String Uname)
    {

        String query  = "select " + COLUMN_USER_ID  + ", "+ COLUMN_EMAIL + " , " + COLUMN_PASSWORD +" from " + TABLE_USERS;
        Cursor cursor = db.rawQuery(query, null);
        String a,b;
        int position = 0;
        b = "not found";
        if (cursor.moveToFirst())
        {

            do{
                a = cursor.getString(1);
               // b = cursor.getString(1);
                if(a.equals(Uname))
                {
                    b =cursor.getString(2);
                    position = Integer.parseInt(cursor.getString(0));
                    break;
                }

            }while(cursor.moveToNext());
        }



        SharedPreferences share = PreferenceManager.getDefaultSharedPreferences(this.context);
        SharedPreferences.Editor editor = share.edit();
        editor.putInt("posValue", position);
        editor.apply();
        Toast.makeText(this.context, "position= " + position, Toast.LENGTH_LONG).show();

        return  b;
    }

//    public Cursor getAccount(int i) throws SQLException {
//        //query database for current row for data
//        Cursor mCursor = db.query(true, TABLE_USERS, new String[]
//                        {COLUMN_USER_ID, COLUMN_NAME, COLUMN_PASSWORD, COLUMN_USERNAME, COLUMN_EMAIL,
//                                COLUMN_NURSENUMBER}, COLUMN_USER_ID + " like " + i , null,
//                null, null, null, null);
//        //if cursor exists, go to the first point in database
//        if (mCursor != null) {
//            mCursor.moveToFirst();
//        }
//        //return the cursor
//        return mCursor;
//    }



//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        db.execSQL(TABLE_CREATE_USERS);
//        this.db = db;
//    }
//
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        String qurey = "DROP TABLE IF ECISTS" +TABLE_USERS;
//        db.execSQL(qurey);
//        this.onCreate(db);
//    }
}
