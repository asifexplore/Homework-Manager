package com.example.a15017498.app1_task_manager;

/**
 * Created by 15017498 on 13/11/2016.
 */

public final class TaskContract {
// This is for TASK
    TaskContract(){}

    public static abstract class ProductEntry{
        public static final String ID = "id";
        public static final String Name = "name";
        public static final String Status = "status";
        public static final String Desc = "description";
        public static final String Time = "time";
        public static final String Date = "date";
        public static final String TABLE_NAME = "task_table";

    }

}
