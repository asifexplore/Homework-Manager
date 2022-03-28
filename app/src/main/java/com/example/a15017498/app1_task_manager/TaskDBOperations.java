package com.example.a15017498.app1_task_manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by 15017498 on 13/11/2016.
 */

public class TaskDBOperations extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME="new_TASK_DB.db";
    private static final String CREATE_QUERY = "CREATE TABLE "+TaskContract.ProductEntry.TABLE_NAME+
           "("+TaskContract.ProductEntry.ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+ TaskContract.ProductEntry.Name+" TEXT,"+TaskContract.ProductEntry.Status+" TEXT,"+
            TaskContract.ProductEntry.Desc+" TEXT,"+TaskContract.ProductEntry.Time+" TEXT,"+TaskContract.ProductEntry.Date+" TEXT);";

    // creating database
    TaskDBOperations(Context ctx){
        super(ctx,DB_NAME,null,DB_VERSION);
        Log.d("Database Operation","Database created...");
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(CREATE_QUERY);
        Log.d("Database Operation","Table created...");
    }

    public void addInformation(SQLiteDatabase sqLiteDatabase, String name, String status, String desc, String time, String date){

            ContentValues contentValues = new ContentValues();
            contentValues.put(TaskContract.ProductEntry.Name,name);
            contentValues.put(TaskContract.ProductEntry.Status,status);
            contentValues.put(TaskContract.ProductEntry.Desc,desc);
            contentValues.put(TaskContract.ProductEntry.Time,time);
            contentValues.put(TaskContract.ProductEntry.Date,date);


            // adds 1 row of things inside
            sqLiteDatabase.insert(TaskContract.ProductEntry.TABLE_NAME,null,contentValues);
            Log.d("Database Operation","One Row Inserted.....");
        sqLiteDatabase.close();
    }

    public void deleteInformation(String user_name, SQLiteDatabase sqLiteDatabase){
        // QUery
        String selection = TaskContract.ProductEntry.Name+" = ?";
           // The username of the person to be deleted
            String[] selection_args = {user_name};
        // Executing the delete

        sqLiteDatabase.delete(TaskContract.ProductEntry.TABLE_NAME,selection,selection_args);
        Log.d("Database Operation","Data Row Deleted.....");
        sqLiteDatabase.close();
    }

        public int updateInformation(String old_name, String new_name, String new_subject, String new_desc, String new_time, String new_date, SQLiteDatabase sqLiteDatabase){
            ContentValues contentValues = new ContentValues();

            contentValues.put(TaskContract.ProductEntry.Name,new_name);
            contentValues.put(TaskContract.ProductEntry.Status,new_subject);
            contentValues.put(TaskContract.ProductEntry.Desc,new_desc);
            contentValues.put(TaskContract.ProductEntry.Time,new_time);
            contentValues.put(TaskContract.ProductEntry.Date,new_date);

            String selection = TaskContract.ProductEntry.Name +" LIKE ?";
            String[] selection_args = {old_name};
           int count = sqLiteDatabase.update(TaskContract.ProductEntry.TABLE_NAME,contentValues,selection,selection_args);

            return count;

        }

            // GET ALL INFO INSIDE DATABASE AND PLACE INSIDE CURSOR
            public Cursor getInformations(SQLiteDatabase db){
                String[] projections = {TaskContract.ProductEntry.ID, TaskContract.ProductEntry.Name, TaskContract.ProductEntry.Status, TaskContract.ProductEntry.Desc, TaskContract.ProductEntry.Time, TaskContract.ProductEntry.Date};
                Cursor cursor = db.query(TaskContract.ProductEntry.TABLE_NAME,projections,null,null,null,null,null);

                return cursor;
            }



    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TaskContract.ProductEntry.TABLE_NAME);
            onCreate(sqLiteDatabase);
    }
}
