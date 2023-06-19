package com.example.health_app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
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

import com.example.health_app.database.MyDbHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SheduleAppointment extends AppCompatActivity {

    private TextView dateTextView;
    private Calendar calendar;
    private Date selectedDate;

    EditText patientname, dohname, disc;
    Button shedulebtn;

    MyDbHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shedule_appointment);

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");


        db = new MyDbHelper(this);


        dateTextView = findViewById(R.id.dateTextView);
        calendar = Calendar.getInstance();
        // Set the initial date on the TextView
        updateDateTextView();

        patientname = findViewById(R.id.patientname);
        dohname = findViewById(R.id.dohname);
        disc = findViewById(R.id.disc);
        shedulebtn = findViewById(R.id.shedulebtn);



        shedulebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pname = patientname.getText().toString();
                String dhname = dohname.getText().toString();
                String desc = disc.getText().toString();
                String date = dateTextView.getText().toString();
                String[] splitdate = date.split(" ");
                int cd = Integer.valueOf(splitdate[0]);
                String cm = splitdate[1];
                int cy = Integer.valueOf(splitdate[2]);
                String msg = "Appoint sheduled at "+dhname+" about "+desc;

                setAla4rm(cd, cm, cy, msg);
                boolean instatus = insertIntoDatabase(pname, dhname, desc, date, username);

                if(instatus) {
                    Log.d("t1", "bfcll: ");
                    setAla4rm(cd, cm, cy, msg);
                    Toast.makeText(SheduleAppointment.this, "appointment sheduled", Toast.LENGTH_SHORT).show();
                    Log.d("t1", "afcll: ");
                    Intent intent = new Intent(getApplicationContext(), Home.class);
                    intent.putExtra("username", username);
                    startActivity(intent);
                } else {
                    Toast.makeText(SheduleAppointment.this, "error in sheduling, retry", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(SheduleAppointment.this, "date selected is "+selectedDate, Toast.LENGTH_SHORT).show();
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

    //alarm
    public void setAla4rm(int cd, String cm, int cy, String msg) {
        Log.d("t1", "beg setalarm");
        Calendar cal_alarm = Calendar.getInstance();
        calendar.set(Calendar.YEAR, cy);  // Set the year
        calendar.set(Calendar.MONTH, 5);  // Set the month (0-11)
        calendar.set(Calendar.DAY_OF_MONTH, cd);  // Set the day
        cal_alarm.set(Calendar.HOUR_OF_DAY, 10);
        cal_alarm.set(Calendar.MINUTE, 0);
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

    public boolean insertIntoDatabase(String pname, String dhname, String desc, String date, String username) {



        if(TextUtils.isEmpty(pname) || TextUtils.isEmpty(dhname) || TextUtils.isEmpty(desc) || TextUtils.isEmpty(date) || TextUtils.isEmpty(username)  )    {
            Toast.makeText(SheduleAppointment.this, "all feilds required", Toast.LENGTH_SHORT).show();
        }
        else {
            Log.d("dbcheck", "bef call: ");
            Boolean insert = db.insertAppointmentShedule(pname, dhname, desc, date, username);
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