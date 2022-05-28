package com.mp.todop.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.google.firebase.database.ValueEventListener;
import com.mp.todop.R;
import com.mp.todop.util.Util;
import com.mp.todop.model.Task;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private final List<Task> taskList;
    private final OnTodoClickListener todoClickListener;

    public RecyclerViewAdapter(List<Task> taskList,OnTodoClickListener todoClickListener) {
        this.taskList = taskList;
        this.todoClickListener = todoClickListener;
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

    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public AppCompatRadioButton radioButton;
        public AppCompatTextView task;
        public Chip todaychip;

        OnTodoClickListener ontodoClickListener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            radioButton= itemView.findViewById(R.id.todo_radio_button);
            task=itemView.findViewById(R.id.todo_row_todo);
            todaychip=itemView.findViewById(R.id.todo_row_chip);
            this.ontodoClickListener = todoClickListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int id = view.getId();
            if (id == R.id.todo_row_layout){
                Task currTask = taskList.get(getAdapterPosition());
                ontodoClickListener.onTodoClick(getAdapterPosition(),currTask);
            }
        }
    }
}
