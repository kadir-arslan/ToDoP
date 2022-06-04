package com.mp.todop;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.graphics.drawable.Drawable;
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

import com.google.android.material.chip.Chip;
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
    private Chip calendarButton;
    private Chip priorityButton;
    private RadioGroup priortyGrpoup;
    private RadioButton selectedRadioButton;
    private int seleectedBottunId;
    private Chip saveButton;
    private CalendarView calendarView;
    private Group calendarGroup;


    private Date dueDate;
    private Priority priority;
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
        priority = Priority.MEDIUM;

        if (getIntent().getExtras() != null){
            oldTask = (Task) getIntent().getSerializableExtra("task");
            taskText.setText(oldTask.getModel().getTask());
            dueDate = oldTask.getModel().getDueDate();
            priority = oldTask.getModel().getPriority();
            createDate = oldTask.getModel().getDateCreated();
            todo = oldTask.getModel().getTask();
            saveButton.setChipIconResource(R.drawable.ic_baseline_edit_24);
            saveButton.setText("Guncelle");
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

        priorityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                priortyGrpoup.setVisibility(
                        priortyGrpoup.getVisibility() == View.GONE ? View.VISIBLE : View.GONE
                );

                priortyGrpoup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        if (priortyGrpoup.getVisibility() == View.VISIBLE){
                            seleectedBottunId = i;
                            selectedRadioButton =radioGroup.findViewById(seleectedBottunId);
                            if (selectedRadioButton.getId() == R.id.radioButton_high){
                                priority = Priority.HIGH;
                            }else if (selectedRadioButton.getId() == R.id.radioButton_med){
                                priority = Priority.MEDIUM;
                            }else if (selectedRadioButton.getId() == R.id.radioButton_low){
                                 priority = Priority.LOW;
                            }else {
                                priority = Priority.MEDIUM;
                            }
                        }
                    }
                });
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