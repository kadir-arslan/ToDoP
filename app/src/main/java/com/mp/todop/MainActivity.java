package com.mp.todop;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mp.todop.adapter.OnTodoClickListener;
import com.mp.todop.adapter.RecyclerViewAdapter;
import com.mp.todop.model.Priority;
import com.mp.todop.model.Task;
import com.mp.todop.model.TaskModel;
import com.mp.todop.util.DatabaseHandler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnTodoClickListener {
    private List<Task> taskList = new ArrayList<Task>();
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DatabaseHandler db = new DatabaseHandler();
        setContentView(R.layout.activity_main);



        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //tasklarin listesi
        Query tasks = db.databaseReference.orderByPriority();
        tasks.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                taskList.clear();
                Iterator<DataSnapshot> childIter = dataSnapshot.getChildren().iterator();
                while (childIter.hasNext()) {
                    DataSnapshot child = childIter.next();
                    Task task = new Task();
                    task.setId(child.getKey());
                    task.setModel(child.getValue(TaskModel.class));
                    taskList.add(task);
                }
                recyclerViewAdapter = new RecyclerViewAdapter(taskList,MainActivity.this);
                recyclerView.setAdapter(recyclerViewAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Task task = new Task();
                //java.util.Date date = new java.util.Date();
                //task.setModel(new TaskModel("test", Priority.HIGH, date, date, false));
                //db.add(task);
                //db.remove(taskList.get(0));
                showBottomSheetDialog();
            }
        });


    }

    private void showBottomSheetDialog() {
        startActivity(new Intent(MainActivity.this, NewTaskActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onTodoClick(int adapterPosition, Task task) {
        Log.d(TAG, "onTodoClick: "+ adapterPosition + task.getModel().getTask());
    }
}