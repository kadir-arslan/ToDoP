package com.mp.todop.adapter;

import com.mp.todop.model.Task;

public interface OnTodoClickListener {
    void onTodoClick(int adapterPosition, Task task);
}
