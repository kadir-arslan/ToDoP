package com.mp.todop;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;
import androidx.lifecycle.ViewModelProvider;

import com.mp.todop.adapter.OnTodoClickListener;
import com.mp.todop.model.Priority;
import com.mp.todop.model.SharedViewModel;
import com.mp.todop.model.Task;
import com.mp.todop.model.TaskModel;
import com.mp.todop.util.DatabaseHandler;

import java.util.Calendar;
import java.util.Date;


public class NewTaskActivity extends AppCompatActivity{

    DatabaseHandler db = new DatabaseHandler();
    Calendar calendar = Calendar.getInstance();
    private EditText taskText;
    private ImageButton calendarButton;
    private ImageButton priorityButton;
    private RadioGroup priortyGrpoup;
    private RadioButton selectedRadioButton;
    private int seleectedBottunId;
    private ImageButton saveButton;
    private CalendarView calendarView;
    private Group calendarGroup;


    private Date dueDate;
    private Priority priority = Priority.MEDIUM;
    private String todo;
    private Date createDate;
    private Task oldTask;
    private boolean isEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        taskText = findViewById(R.id.enter_todo_et);
        calendarButton = findViewById(R.id.today_calendar_button);
        calendarView = findViewById(R.id.calendar_view);
        calendarGroup = findViewById(R.id.calendar_group);
        saveButton = findViewById(R.id.save_todo_button);
        priorityButton = findViewById(R.id.priority_todo_button);
        priortyGrpoup = findViewById(R.id.radioGroup_priority);

        if (getIntent().getExtras() != null){
            oldTask = (Task) getIntent().getSerializableExtra("task");
            taskText.setText(oldTask.getModel().getTask());
            dueDate = oldTask.getModel().getDueDate();
            priority = oldTask.getModel().getPriority();
            createDate = oldTask.getModel().getDateCreated();
            todo = oldTask.getModel().getTask();
            saveButton.setImageResource(R.drawable.ic_baseline_edit_24);
            isEdit = true;
        }

        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendarGroup.setVisibility(
                        calendarGroup.getVisibility() == View.GONE ? View.VISIBLE : View.GONE
                );
            }
        });

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                calendar.clear();
                calendar.set(i, i1, i2);
                dueDate = calendar.getTime();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    todo = taskText.getText().toString();
                if (!isEdit) {
                    createDate = Calendar.getInstance().getTime();
                }

                if (!TextUtils.isEmpty(todo) && dueDate != null) {
                    TaskModel taskModel =
                            new TaskModel(todo, priority, dueDate, createDate, false);
                    if (isEdit){
                        oldTask.setModel(taskModel);
                        db.update(oldTask);
                    }else {
                        Task task = new Task();
                        task.setModel(taskModel);
                        db.add(task);
                    }
                startActivity(new Intent(NewTaskActivity.this,MainActivity.class));
                } else {
                    Toast.makeText(NewTaskActivity.this, "text ve bitis tarihi kismi bos birakilamaz", Toast.LENGTH_SHORT);
                }
            }
        });
    }


}