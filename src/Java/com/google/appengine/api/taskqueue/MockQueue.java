package com.google.appengine.api.taskqueue;

import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.datastore.Transaction;

public class MockQueue implements Queue {
    List<TaskOptions> history = new ArrayList<TaskOptions>();

    public TaskHandle add() {
        return null;
    }

    public TaskHandle add(TaskOptions taskOptions) {
        history.add(taskOptions);
        return null;
    }

    public TaskHandle add(Transaction txn, TaskOptions taskOptions) {
        history.add(taskOptions);
        return null;
    }

    public String getQueueName() {
        return "testQueue";
    }

    public List<TaskOptions> getHistory() {
        return history;
    }

    public void resetHistory() {
        history.clear();
    }

    @Override
    public List<TaskHandle> add(Iterable<TaskOptions> arg0) {
        return null;
    }

    @Override
    public List<TaskHandle> add(Transaction arg0, Iterable<TaskOptions> arg1) {
        return null;
    }

    @Override
    public void purge() {
    }
}
