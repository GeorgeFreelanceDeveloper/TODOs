package org.samples.todos.model;

import java.util.List;
import java.util.Objects;

public class TaskGroup {

    private String name;
    private List<Task> tasks;

    public TaskGroup(String name, List<Task> tasks) {
        this.name = name;
        this.tasks = tasks;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskGroup taskGroup = (TaskGroup) o;
        return Objects.equals(name, taskGroup.name) && Objects.equals(tasks, taskGroup.tasks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, tasks);
    }

    @Override
    public String toString() {
        return "TaskGroup{" +
                "name='" + name + '\'' +
                ", tasks=" + tasks +
                '}';
    }
}
