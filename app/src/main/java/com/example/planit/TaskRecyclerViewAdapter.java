package com.example.planit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;


import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TaskRecyclerViewAdapter extends RecyclerView.Adapter<TaskRecyclerViewAdapter.ViewHolder>{
    private String[] taskData;
    private ArrayList<Task> taskArray;
    private Context context;
    private ItemClickListener clickListener;
    Task task;

    // data pref?



    //adding context to this one
    public TaskRecyclerViewAdapter(String[] data, Context ctx){
        taskData = data;
        context = ctx;
    }

    public TaskRecyclerViewAdapter(String[] data){
        taskData = data;
    }

    public void setTasks (ArrayList<Task> myList){
        taskArray = myList;
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView taskDisplayTextView;
        // getting the checkbox from the view
        public CheckBox checkBox;

        public ViewHolder(View view){
            super(view);

            taskDisplayTextView = (TextView)view.findViewById(R.id.taskCheckBox);
            // the task check box
            checkBox = (CheckBox)view.findViewById(R.id.taskCheckBox);
            // item click event listener
           // view.setOnClickListener(this); // not sure if needed rn

            itemView.setOnClickListener(this);

            // check box click handling
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                   PlannerDBHandler handler = new PlannerDBHandler(context);
                   String taskName = buttonView.getText().toString();
                   handler.updateCompletion(taskName, isChecked);
                }
            });
        }

        //getter for GUI component you want to manipulate
        public TextView getTextView(){
            return taskDisplayTextView;
        }

        @Override
        public void onClick(View v) {
            if(clickListener != null){
                clickListener.onItemClick(v, getAdapterPosition());
            }
        }
    }

    // getting the task box checked ??


    // creates a new view and inflates a view
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        Context context = viewGroup.getContext();

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.taskrecycler_row, viewGroup, false);

        return new ViewHolder(view);
    } // end of onCreateViewHolder


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position){
        viewHolder.getTextView().setText(taskData[position]);
        PlannerDBHandler handler = new PlannerDBHandler(context);
        Task task = handler.findTask(taskData[position]);
        if(task.getTask_isCompleted()){
            viewHolder.checkBox.setChecked(true);
        }


    }// end of onBindViewHolder


    @Override
    public int getItemCount(){
        return taskData.length;
    } // end of getItemCount

    String getItem(int id){
        return taskData[id];
    }

    //setter connected to the private clicklistener at top
    void setClickListener(ItemClickListener i){
        this.clickListener = i;
    }


    public interface ItemClickListener{
        void onItemClick(View view, int position);
    }



}
