package com.mp.todop.adapter;

import static android.content.ContentValues.TAG;

import android.content.res.ColorStateList;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.google.firebase.database.ValueEventListener;
import com.mp.todop.R;
import com.mp.todop.util.Util;
import com.mp.todop.model.Task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> implements Filterable {
    private final List<Task> taskList;
    private final OnTodoClickListener todoClickListener;
    List<Task> taskListAll = new ArrayList<Task>();

    public RecyclerViewAdapter(List<Task> taskList,OnTodoClickListener todoClickListener) {
        this.taskList = taskList;
        this.todoClickListener = todoClickListener;
        taskListAll.addAll(taskList);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Task task = taskList.get(position);


        holder.task.setText(task.getModel().getTask());
        holder.todaychip.setText(Util.formatDate(task.getModel().getDueDate()));
        holder.priortyChip.setText(Util.priortyToString(task.getModel().getPriority()));
        if (task.getModel().isDone()){
            holder.radioButton.setChecked(true);
        }
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }
    @Override
    public Filter getFilter() {

        return myFilter;
    }

    Filter myFilter = new Filter() {

        //Automatic on background thread
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            List<Task> filteredList = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(taskListAll);
            } else {
                for (Task task: taskListAll) {
                    if (task.getModel().getTask().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                        filteredList.add(task);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        //Automatic on UI thread
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            taskList.clear();
            taskList.addAll((Collection<? extends Task>) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public AppCompatRadioButton radioButton;
        public AppCompatTextView task;
        public Chip todaychip;
        public Chip priortyChip;

        OnTodoClickListener ontodoClickListener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            radioButton= itemView.findViewById(R.id.todo_radio_button);
            task=itemView.findViewById(R.id.todo_row_todo);
            todaychip=itemView.findViewById(R.id.todo_row_chip);
            priortyChip=itemView.findViewById(R.id.todo_row_chip_priorty);
            this.ontodoClickListener = todoClickListener;

            itemView.setOnClickListener(this);
            radioButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int id = view.getId();
            Task currTask = taskList.get(getAdapterPosition());
            Log.d(TAG, "onClick: metod");

            if (id == R.id.todo_row_layout){
                ontodoClickListener.onTodoClick(getAdapterPosition(),currTask);
                Log.d(TAG, "onClick: lyou");

            }else if(id == R.id.todo_radio_button){
                ontodoClickListener.onTodoRadioButtonClick(currTask);
                Log.d(TAG, "onClick: radio ");
            }
        }
    }
}
