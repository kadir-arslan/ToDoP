package com.mp.todop.util;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mp.todop.model.Task;
import com.mp.todop.model.TaskModel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DatabaseHandler {
    public DatabaseReference databaseReference;

    public DatabaseHandler() {
        FirebaseDatabase db = FirebaseDatabase.getInstance("https://todop-19db6-default-rtdb.europe-west1.firebasedatabase.app/");
        databaseReference = db.getReference("tasks");
    }

    public List<Task> updateList(DataSnapshot dataSnapshot) {
        List<Task> list = new ArrayList<Task>();
        Iterator<DataSnapshot> childIter = dataSnapshot.getChildren().iterator();
        while (childIter.hasNext()) {
            DataSnapshot child = childIter.next();
            Task task = new Task();
            task.setId(child.getKey());
            task.setModel(child.getValue(TaskModel.class));
            list.add(task);
        }
        return list;
    }

    public com.google.android.gms.tasks.Task<Void> add(Task task) {
        String key = databaseReference.push().getKey();
        task.setId(key);
        return databaseReference.child(key).setValue(task.getModel());
    }

    public com.google.android.gms.tasks.Task<Void> update(Task task) {

        return databaseReference.child(task.getId()).updateChildren(task.getModel().toMap());
    }

    public com.google.android.gms.tasks.Task<Void> remove(Task task) {
        return databaseReference.child(task.getId()).removeValue();
    }
}