package com.mp.todop.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
@IgnoreExtraProperties
public class TaskModel {

    private String task;
    private Priority priority;
    private Date dueDate;
    private Date dateCreated;
    private boolean isDone;

    public TaskModel(){}

    public TaskModel(String task, Priority priority, Date dueDate, Date dateCreated, boolean isDone) {
        this.task = task;
        this.priority = priority;
        this.dueDate = dueDate;
        this.dateCreated = dateCreated;
        this.isDone = isDone;
    }

     @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("task", task);
        result.put("priority", priority);
        result.put("dueDate", dueDate);
        result.put("dateCreated", dateCreated);
        result.put("done", isDone);

        return result;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

}
