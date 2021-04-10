package com.example.planit;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class TaskRecyclerViewAdapter extends RecyclerView.Adapter<TaskRecyclerViewAdapter.ViewHolder>{
    private String[] taskData;
    private ItemClickListener clickListener;


    public TaskRecyclerViewAdapter(String[] data){taskData = data;}

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView taskDisplayTextView;

        public ViewHolder(View view){
            super(view);
            taskDisplayTextView = (TextView)view.findViewById(R.id.taskCheckBox);
            itemView.setOnClickListener(this);
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


    // creates a new view and inflates a view
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.taskrecycler_row, viewGroup, false);

        return new ViewHolder(view);
    } // end of onCreateViewHolder


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position){
        viewHolder.getTextView().setText(taskData[position]);
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
