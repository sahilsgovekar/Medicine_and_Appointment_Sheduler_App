package com.example.health_app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import android.content.BroadcastReceiver;

import com.example.health_app.database.MyDbHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SheduleMedecine extends AppCompatActivity {

    private TextView dateTextView;
    private Calendar calendar;
    private Date selectedDate;
    private TextView timeTextView;
    String selectedTime;

    EditText patientname, medicinename, quantity, ndays;
    Button shedulebtn;

    MyDbHelper db;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shedule_medecine);
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");

        db = new MyDbHelper(this);

        Log.d("t1", "beg: ");


        dateTextView = findViewById(R.id.dateTextView);
        calendar = Calendar.getInstance();
        // Set the initial date on the TextView
        updateDateTextView();
        timeTextView = findViewById(R.id.timeTextView);

        patientname = findViewById(R.id.patientname);
        medicinename = findViewById(R.id.medicinename);
        quantity = findViewById(R.id.quantity);
        ndays = findViewById(R.id.ndays);
        shedulebtn = findViewById(R.id.shedulebtn);


        shedulebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("t1", "btnbeg: ");
                String pname = patientname.getText().toString();
                String mname = medicinename.getText().toString();
                String qty = quantity.getText().toString();
                String date = dateTextView.getText().toString();
                String nodays = ndays.getText().toString();
                String time = timeTextView.getText().toString();

                int minute = 56;
                int hour = 1;

                Log.d("t1", "afthrinit: ");

                if(time.equals("Morning")) {
                    hour = 8;
                }
                else if(time.equals("Afternoon")) {
                    hour = 14;
                }
                else if(time.equals("Evening")) {
                    hour = 17;
                }
                else {
                    hour = 20;
                }

                Log.d("t1", "hour :"+hour);

                String msg = mname +" of "+qty+"..!!";
                Log.d("t1", "after str");

                boolean instatus = insertIntoDatabase(pname, mname, qty, date, nodays, time);

                if(instatus) {
                        Log.d("t1", "bfcll: ");
                        setAla4rm(minute, hour, msg);
                        Toast.makeText(SheduleMedecine.this, "medicine sheduled", Toast.LENGTH_SHORT).show();
                        Log.d("t1", "afcll: ");
                        Intent intent = new Intent(getApplicationContext(), Home.class);
                        intent.putExtra("username", username);
                        startActivity(intent);
                        finish();
                } else {
                    Toast.makeText(SheduleMedecine.this, "error in sheduling, retry", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public void showDatePickerDialog(View view) {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, day);

                // Storing the selected date in the selectedDate variable
                selectedDate = calendar.getTime();
                Toast.makeText(SheduleMedecine.this, "date selected is "+selectedDate, Toast.LENGTH_SHORT).show();
                // Update the TextView with the selected date
                updateDateTextView();
            }
        };

        // Create a new DatePickerDialog instance
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));

        // Show the DatePickerDialog
        datePickerDialog.show();
    }

    // Method to update the TextView with the selected date
    private void updateDateTextView() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        String selectedDate = sdf.format(calendar.getTime());
        dateTextView.setText(selectedDate);
    }

    // Method to show the popup menu
    public void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());

        // Set a listener to handle menu item selection
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // Update the TextView with the selected option
                selectedTime = item.getTitle().toString();
                timeTextView.setText(item.getTitle());
                return true;
            }
        });

        // Show the popup menu
        popupMenu.show();
    }

    public void setAla4rm(int min, int hour, String msg) {
        Log.d("t1", "beg setalarm");
        Calendar cal_alarm = Calendar.getInstance();
        cal_alarm.set(Calendar.HOUR_OF_DAY, hour);
        cal_alarm.set(Calendar.MINUTE, min);
        cal_alarm.set(Calendar.SECOND, 0);
        cal_alarm.set(Calendar.MILLISECOND, 0);

        Log.d("t1", "bef intent");
        Intent intent = new Intent(getApplicationContext(), broadcastForReminder.class);
        intent.putExtra("rMsg", msg);
        Log.d("t1", "before pending intent");
//        PendingIntent pi = PendingIntent.getBroadcast(getApplicationContext(), 1, intent, 0);
        PendingIntent pi = PendingIntent.getBroadcast(getApplicationContext(), 1, intent, PendingIntent.FLAG_IMMUTABLE);
        Log.d("t1", "after pending intent, before alarm manager");
        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        Log.d("t1", "after alarm manager, before if");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Log.d("t1", "if cond 1");
            am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, cal_alarm.getTimeInMillis(), pi);
        } else {
            Log.d("t1", "else cond 1");
            am.setExact(AlarmManager.RTC_WAKEUP, cal_alarm.getTimeInMillis(), pi);
        }
        Log.d("t1", "after if, end block");


    }

    public boolean insertIntoDatabase(String pname, String mname, String qty, String date, String nodays, String time) {

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");

        if(TextUtils.isEmpty(pname) || TextUtils.isEmpty(mname) || TextUtils.isEmpty(qty) || TextUtils.isEmpty(date) || TextUtils.isEmpty(nodays) || TextUtils.isEmpty(time) )    {
            Toast.makeText(SheduleMedecine.this, "all feilds required", Toast.LENGTH_SHORT).show();
        }
        else {
            Log.d("dbcheck", "bef call: ");
            Boolean insert = db.insertMedicineShedule(username, pname, mname, qty, date, nodays, time);
            Log.d("dbcheck", "after call");

            if(insert) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }



}