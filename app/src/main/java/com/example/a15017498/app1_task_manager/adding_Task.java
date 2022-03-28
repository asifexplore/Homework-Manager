package com.example.a15017498.app1_task_manager;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.*;
import java.util.Calendar;

public class adding_Task extends AppCompatActivity {

    Spinner dropdown;
    EditText name,desc,time,date;
    String given_name,given_desc,given_time,given_date,givenStatus,old_name;
    Button btnSubmit,btnCancel;
    ImageButton imgDate, imgTime;
    TaskClass editTask;
    TextView textViewTitle;
    int num = 0;
    String task = "";

    Context context = this;
    TaskDBOperations dbOperations;
    SQLiteDatabase sqLiteDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding__task);

        dropdown  = (Spinner)findViewById(R.id.spinner1);
        String[] items = new String[]{"URGENT", "Important", "Warning"};
        ArrayAdapter<String> dropDownAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(dropDownAdapter);

        name = (EditText) findViewById(R.id.editTextName);
        desc = (EditText) findViewById(R.id.editTextDesc);
        time = (EditText) findViewById(R.id.editTextTime);
        date = (EditText) findViewById(R.id.editTextDate);
        btnCancel = (Button) findViewById(R.id.buttonCancel);
        imgTime = (ImageButton) findViewById(R.id.imageBtnTime);
        imgDate = (ImageButton) findViewById(R.id.imageBtnDate);
        btnSubmit = (Button) findViewById(R.id.buttonConfirm);
        textViewTitle = (TextView) findViewById(R.id.textView2);

        Intent i = getIntent();
        task = i.getStringExtra("task");

        if(task != null  && task.equalsIgnoreCase("Editing") == true  ){

            editTask = (TaskClass) i.getSerializableExtra("task_object");
            name.setText(editTask.getDate());
            desc.setText(editTask.getSubject());
            time.setText(editTask.getDesc());
            date.setText(editTask.getTime());

            old_name = editTask.getDate();

            String status = editTask.getName().toString();

            if (status.equalsIgnoreCase("URGENT")){
                num = 0;
            }else if (status.equalsIgnoreCase("Important")){
                num = 1;
            }else{
                num = 2;
            }

            dropdown.setSelection(num);


            textViewTitle.setText("Editing Task");
        }else{

        }

        imgDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener(){
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth){
                        String date1 = dayOfMonth + "/"+(monthOfYear+1)+"/"+year;
                        date.setText(date1);
                    }
                };

                java.util.Calendar calen = java.util.Calendar.getInstance();
                int date = calen.get(java.util.Calendar.DAY_OF_MONTH);
                int month = calen.get(java.util.Calendar.MONTH);
                int year = calen.get(java.util.Calendar.YEAR);

                DatePickerDialog myDateDialog = new DatePickerDialog(adding_Task.this,myDateListener,year,month,date);
                myDateDialog.show();

            }
        });

        imgTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String time1 = hourOfDay + ":" + minute;
                        time.setText(time1);
                    }
                };
                java.util.Calendar calen = java.util.Calendar.getInstance();
                int hour = calen.get(java.util.Calendar.HOUR_OF_DAY);
                int minute = calen.get(Calendar.MINUTE);

                TimePickerDialog myTimeDialog = new TimePickerDialog(adding_Task.this,myTimeListener,hour,minute,true);
                myTimeDialog.show();

            }
        });


        //------------------------------------------Buttons---------------------------------------------------------------------
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                given_name = name.getText().toString();
                given_date = date.getText().toString();
                given_desc = desc.getText().toString();
                given_time = time.getText().toString();
                givenStatus = dropdown.getSelectedItem().toString();
                //Toast.makeText(adding_Task.this, givenStatus, Toast.LENGTH_SHORT).show();

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(adding_Task.this);
                SharedPreferences.Editor prefEdit = prefs.edit();
                prefEdit.putString("task_name",given_name);
                prefEdit.commit();

                // Trial Codes start from here
                Calendar calendar = Calendar.getInstance();

                String CurrentString = given_time;
                String[] separated = CurrentString.split(":");

                int number1=Integer.parseInt(separated[0]);
                int number2=Integer.parseInt(separated[1]);

                // Hour is 24hrs
                calendar.set(Calendar.HOUR_OF_DAY,number1);
                calendar.set(Calendar.MINUTE,number2);

                Intent intent = new Intent(getApplicationContext(),Notification_receiever.class);
                // Allows other apps like Alarm Manager to perform codes which we have coded
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),100,intent,PendingIntent.FLAG_UPDATE_CURRENT);

                AlarmManager alarmManager =(AlarmManager) getSystemService(ALARM_SERVICE);
                // Only for 1 Day
                alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
                // TO Repeat multiple days
                //  alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);

                dbOperations = new TaskDBOperations(context);
                sqLiteDatabase = dbOperations.getWritableDatabase();

                if(task != null && task.equalsIgnoreCase("Editing")){
                   // dbOperations = new TaskDBOperations(context);

                    dbOperations.updateInformation(old_name,given_name,givenStatus,given_desc,given_time,given_date,sqLiteDatabase);
                    Toast.makeText(context, "Data has been Updated", Toast.LENGTH_SHORT).show();
                    dbOperations.close();
                }else{

                   // sqLiteDatabase = dbOperations.getWritableDatabase();
                    dbOperations.addInformation(sqLiteDatabase,given_name,givenStatus,given_desc,given_time,given_date);
                    Toast.makeText(context, "Data has been saved", Toast.LENGTH_SHORT).show();
                    dbOperations.close();
                }

                Intent intent123 = new Intent();
                setResult(RESULT_OK,intent123);

                finish();


            }
        });


    }
}
