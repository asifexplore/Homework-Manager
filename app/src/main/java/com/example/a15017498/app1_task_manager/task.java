package com.example.a15017498.app1_task_manager;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class task extends Fragment {

    ListView lvTask;
    taskAdapter taskAdapter;
    TaskDBOperations  useDbOpertations;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;
    private int REQUEST_CODE=1;
    private int REQUEST_CODEDIT=2;

    public task() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_task, container, false);

        useDbOpertations = new TaskDBOperations(getActivity().getApplicationContext());
        sqLiteDatabase = useDbOpertations.getReadableDatabase();
        cursor = useDbOpertations.getInformations(sqLiteDatabase);

        lvTask = (ListView) view.findViewById(R.id.TaskListView);
        taskAdapter = new taskAdapter(getActivity().getApplicationContext(),R.layout.display_task_row);
        lvTask.setAdapter(taskAdapter);


        lvTask.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TaskClass clickedTask  = taskAdapter.getItem(position);
                Intent helpIntent = new Intent(getActivity().getApplicationContext(), adding_Task.class);
                helpIntent.putExtra("task","Editing");
                helpIntent.putExtra("task_object",clickedTask);
                startActivityForResult(helpIntent,REQUEST_CODEDIT);

            }
        });


        // MOVE TO FIRST RETURNS IF THERE ARE VALUES IN THE DATABASE OR NOT
        // MOVE TO NEXT WOULD BE TRUE IF THERE ARE OTHER ROWS INSIDE THE DATABASE
        if(cursor.moveToFirst()){
            do{
                String name,status,desc,time,date;
                name = cursor.getString(2);
                status = cursor.getString(3);
                desc = cursor.getString(4);
                time = cursor.getString(5);
                date = cursor.getString(1);
                TaskClass task = new TaskClass(name,status,desc,time,date);
                taskAdapter.add(task);

            }while(cursor.moveToNext());
        }
        lvTask.setAdapter(taskAdapter);
        registerForContextMenu(lvTask);


        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        lvTask.setAdapter(taskAdapter);

    }

    // OPTIONS MENU WHEN PRESSED FOR LONG
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu,v,menuInfo);
        AdapterView.AdapterContextMenuInfo infoItem=(AdapterView.AdapterContextMenuInfo) menuInfo;
        int index = infoItem.position;

        if(v.getId()==R.id.TaskListView){
            menu.add(0,0,0,"Delete "+taskAdapter.getItem(index).getDate());

        }
        else{

        }
    }

    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo infoItem=(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int index=infoItem.position;
        if(item.getItemId()==0) {
            useDbOpertations = new TaskDBOperations(getActivity().getApplicationContext());
            sqLiteDatabase = useDbOpertations.getReadableDatabase();
            Cursor c = useDbOpertations.getInformations(sqLiteDatabase);

            //Codes which can work
            TaskClass name = taskAdapter.getItem(index);
            String name1 = name.getDate().toString();
            Log.d("Database Operation",name1);
            useDbOpertations.deleteInformation(name1,sqLiteDatabase);
            taskAdapter.remove(index);
            taskAdapter.delete(index);
            taskAdapter.notifyDataSetChanged();

            return true;
        }

        return super.onContextItemSelected(item);
    }
}
