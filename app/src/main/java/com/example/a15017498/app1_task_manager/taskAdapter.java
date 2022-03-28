package com.example.a15017498.app1_task_manager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 15017498 on 13/11/2016.
 */

public class taskAdapter extends ArrayAdapter {
    List<TaskClass> list = new ArrayList();
//  List list = new ArrayList();
    public taskAdapter(Context context, int resource) {
        super(context, resource);
    }


    public void add(TaskClass object) {
        list.add(object);
        super.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }


    @Override
    public TaskClass getItem(int position) {
        return list.get(position);
    }

//    @Override
//    public Object getItem(int position) {
//        return list.get(position);
//    }


    public TaskClass delete(int position){return list.remove(position);}

//    public Object delete(int position){return list.remove(position);}

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ProductHolder productHolder;
        LayoutInflater layoutInflater =(LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(row == null){
            row = layoutInflater.inflate(R.layout.display_task_row,parent,false);
            productHolder =  new ProductHolder();
            productHolder.imgView = (ImageView) row.findViewById(R.id.imgViewSign);

            productHolder.tx_name = (TextView) row.findViewById(R.id.textViewName);
            productHolder.tx_desc = (TextView) row.findViewById(R.id.textViewDesc);
            row.setTag(productHolder);
        }
        else{
            productHolder =(ProductHolder)row.getTag();
        }

        TaskClass task = (TaskClass) getItem(position);
        //Toast.makeText(getContext(), task.getName().toString(), Toast.LENGTH_SHORT).show();
        int id = row.getResources().getIdentifier(list.get(position).getName().toString().toLowerCase(),"drawable",layoutInflater.getContext().getPackageName());
        productHolder.imgView.setImageResource(id);

        productHolder.tx_name.setText("Task Name: "+task.getDate().toString());
        productHolder.tx_desc.setText("Desc: "+task.getSubject().toString());

        return row;
    }
    static class ProductHolder{
        TextView tx_name,tx_desc;
        ImageView imgView;
    }
}
