package com.example.health_app.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.health_app.SheduleAppointment;
import com.example.health_app.SheduleMedecine;
import com.example.health_app.model.Medecine;
import com.example.health_app.params.params;

import java.util.ArrayList;
import java.util.List;

public class MyDbHelper extends SQLiteOpenHelper {
    public static final String DBNAME = "login.db";

    public MyDbHelper(Context context) {
        super(context, params.DB_NAME, null, params.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table users(username TEXT primary key, password TEXT)");
        db.execSQL("create table medicineshedules(username TEXT, pname TEXT, mname TEXT, qty TEXT, date TEXT, nodays TEXT, time TEXT)");
        db.execSQL("create table appointmentshedules(username TEXT, pname TEXT, dhname TEXT, des TEXT, date TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    public boolean insertData(String username, String password) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("username", username);
        values.put("password", password);

        long result = db.insert("users", null, values);

        if(result == -1) {
            return false;
        }
        else {
            return true;
        }
    }

    public boolean checkusername(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from users where username = ?", new String[] {username});
        if(cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkusernamepassword(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from users where username = ? and password=?", new String[] {username, password});
        if(cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    //medicine shedule

    public boolean insertMedicineShedule(String username, String pname, String mname, String qty, String date, String nodays, String time) {
        Log.d("dbcheck", "bef init: ");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        Log.d("dbcheck", "after init ");

        values.put("username", username);
        values.put("pname", pname);
        values.put("mname", mname);
        values.put("qty", qty);
        values.put("date", date);
        values.put("nodays", nodays);
        values.put("time", time);

        Log.d("dbcheck", "after value push, before intent");


        long result = db.insert("medicineshedules", null, values);
        Log.d("dbcheck", "after push: ");

        if(result == -1) {
            return false;
        }
        else {
            return true;
        }
    }

    public boolean insertAppointmentShedule(String pname, String dhname, String desc, String date, String username) {
        Log.d("dbcheck", "bef init: ");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        Log.d("dbcheck", "after init ");

        values.put("username", username);
        values.put("pname", pname);
        values.put("dhname", dhname);
        values.put("des", desc);
        values.put("date", date);
        Log.d("dbcheck", "after value push, before intent");


        long result = db.insert("appointmentshedules", null, values);
        Log.d("dbcheck", "after push: ");

        if(result == -1) {
            return false;
        }
        else {
            return true;
        }
    }


    //reading
    public List<Medecine> getallmedecine(String username){
        List<Medecine> sheduledmedecinelist = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Generate the query to read from the database
        String select = "SELECT * FROM medicineshedules" ;
        Cursor cursor = db.rawQuery(select, null);

        //Loop through now
        if(cursor.moveToFirst()){
            do{
                if(username.endsWith(cursor.getString(0))) {
                    Medecine sm = new Medecine();
                    //String pname, String mname, String qty, String date, String nodays, String time
                    sm.setName(cursor.getString(1));
                    sm.setMname(cursor.getString(2));
                    sm.setQty(cursor.getString(3));
                    sm.setDate(cursor.getString(4));
                    sm.setNodays(cursor.getString(5));
                    sm.setTime(cursor.getString(6));
                    sheduledmedecinelist.add(sm);
                }

            }while(cursor.moveToNext());
        }
        return sheduledmedecinelist;
    }

}
