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

    private static final int DATABASE_VERSION =1;
    private static final String DATABASE_NAME = "users";
    private static final String TABLE_NAME = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_USERNAME = "uname";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "pass";
    private static final String COLUMN_NURSENUMBER = "nursenum";
    //SQLiteDatabase db;

    private static final String TABLE_CREATE = "create table users (id integer primary key autoincrement, " + "name text not null , uname text not null , " +
            "email text not null , pass text not null , nursenum text not null);";
    //variables for holding database context, helper and SQLite instances
    private final Context context;
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    //method to create instance of database helper
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
                db.execSQL(TABLE_CREATE);
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
            db.execSQL("DROP TABLE IF EXISTS login");
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
        ContentValues values = new ContentValues();


        values.put(COLUMN_EMAIL, u.getEmail());
        values.put(COLUMN_NAME, u.getName());
        values.put(COLUMN_NURSENUMBER, u.getNurseNum());
        values.put(COLUMN_PASSWORD, u.getPWord());
        values.put(COLUMN_USERNAME, u.getUName());

        db.insert(TABLE_NAME, null, values);
    }

    public void DBupdate(String email, String name, String uname, String pass, String nurseNumber, int row){

        ContentValues newValues = new ContentValues();

        newValues.put(COLUMN_EMAIL, email);
        newValues.put(COLUMN_NAME, name);
        newValues.put(COLUMN_NURSENUMBER,nurseNumber);
        newValues.put(COLUMN_PASSWORD , pass);
        newValues.put(COLUMN_USERNAME, uname);

        db.update(TABLE_NAME, newValues,COLUMN_ID + " = " + row, null);
    }

    public void delete_byID(int row)
    {
        db.delete(TABLE_NAME , COLUMN_ID + " = " + row, null);
    }

    public  String searchPass(String Uname)
    {

        String query  = "select uname, pass from " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        String a,b;
        int position = 0;
        b = "not found";
        if (cursor.moveToFirst())
        {

            do{
                a = cursor.getString(0);
               // b = cursor.getString(1);
                if(a.equals(Uname))
                {
                    b =cursor.getString(1);
                    break;
                }
                position++;
            }while(cursor.moveToNext());
        }

        position++;

        SharedPreferences share = PreferenceManager.getDefaultSharedPreferences(this.context);
        SharedPreferences.Editor editor = share.edit();
        editor.putInt("posValue", position);
        editor.apply();
        Toast.makeText(this.context, "position= " + position, Toast.LENGTH_LONG).show();

        return  b;
    }

    public Cursor getAccount(int i) throws SQLException {
        //query database for current row for data
        Cursor mCursor = db.query(true, TABLE_NAME, new String[]
                        {COLUMN_ID, COLUMN_NAME, COLUMN_PASSWORD, COLUMN_USERNAME, COLUMN_EMAIL,
                                COLUMN_NURSENUMBER}, COLUMN_ID + " like " + i , null,
                null, null, null, null);
        //if cursor exists, go to the first point in database
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        //return the cursor
        return mCursor;
    }



//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        db.execSQL(TABLE_CREATE);
//        this.db = db;
//    }
//
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        String qurey = "DROP TABLE IF ECISTS" +TABLE_NAME;
//        db.execSQL(qurey);
//        this.onCreate(db);
//    }
}
