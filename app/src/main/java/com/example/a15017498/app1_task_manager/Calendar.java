package com.example.a15017498.app1_task_manager;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class Calendar extends Fragment {

    TextView textViewTask;
    CalendarView calendar;
    String messageTask;

    TaskDBOperations  useDbOpertations;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;

    public Calendar() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_calendar, container, false);

        messageTask = "";
        calendar = (CalendarView) view.findViewById(R.id.calendarView);
        textViewTask = (TextView) view.findViewById(R.id.textView5);

        // For Task
        useDbOpertations = new TaskDBOperations(getActivity().getApplicationContext());
        sqLiteDatabase = useDbOpertations.getReadableDatabase();
        cursor = useDbOpertations.getInformations(sqLiteDatabase);


        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int year, int month, int day) {

                // To display task from database
                if(cursor.moveToFirst()){
                    do{
                        String name,status,desc,time,date;
                        name = cursor.getString(2);
                        status = cursor.getString(3);
                        desc = cursor.getString(4); // time
                        time = cursor.getString(5);
                        date = cursor.getString(1);
                        TaskClass task = new TaskClass(name,status,desc,time,date);

                        String[] separated = time.split("/");

                        int date1=Integer.parseInt(separated[0]);
                        int month1=Integer.parseInt(separated[1]);
                        int year1 = Integer.parseInt(separated[2]);

                        if (date1 == day && month1 == (month+1) && year1 == year){
                            messageTask += "Task Name: "+ date +"\n Task Subject: "+name +"\n Task Desc: "+status+"\n"+"\n";
                            //Toast.makeText(getActivity().getApplicationContext(), messageTask, Toast.LENGTH_SHORT).show();
                            textViewTask.setText(messageTask);
                        }
                        else{
                            textViewTask.setText("No Task for the day!!");
                        }

                    }while(cursor.moveToNext());
                }
                messageTask = "";

            }
        });

        return view;
    }

}
