package ru.tapoch.todolist;

/**
 * Created by Влад on 15.06.2018.
 */

public class Task {
    private String name, description;
    private int counts;

    public Task() {
    }

    public Task(String name, String description, int counts) {
        this.name = name;
        this.description = description;
        this.counts = counts;
    }

    public void addCount(){
        this.counts++;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCounts() {
        return counts;
    }

    public void setCounts(int counts) {
        this.counts = counts;
    }
}
