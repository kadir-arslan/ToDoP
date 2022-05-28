package com.mp.todop.model;

public class Task {

    private String id;
    private TaskModel model;

    public Task() {
    }

    public Task(String id, TaskModel model) {
        this.id = id;
        this.model = model;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TaskModel getModel() {
        return model;
    }

    public void setModel(TaskModel model) {
        this.model = model;
    }
}
