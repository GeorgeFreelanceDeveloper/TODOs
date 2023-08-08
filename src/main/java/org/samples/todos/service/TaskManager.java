package org.samples.todos.service;

import org.samples.todos.model.Priority;
import org.samples.todos.model.Task;
import org.samples.todos.model.TaskGroup;
import org.samples.todos.repository.TaskRepository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class TaskManager {

    private final TaskRepository taskRepository;
    private final List<TaskGroup> taskGroups;

    public TaskManager(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
        this.taskGroups = taskRepository.load();
    }

    public boolean createTask(Task task, String groupName) {
        //TODO: Lucka implement me please
        return false;
    }

    public boolean updateTask(Task task) {
        //TODO: Lucka implement me please
        return false;
    }

    public boolean deleteTask(UUID id) {
        //TODO: Lucka implement me please
        return false;
    }

    public boolean setDone(UUID id) {
        //TODO: Lucka implement me please
        return false;
    }

    public List<TaskGroup> getAll() {
        //TODO: Lucka implement me please
        return null;
    }

    public List<Task> getBy(String groupName) {
        //TODO: Lucka implement me please
        return null;
    }

    public List<Task> getBy(String groupName, Priority priority) {
        //TODO: Lucka implement me please
        return null;
    }

    public List<Task> getBy(String groupName, boolean done) {
        //TODO: Lucka implement me please
        return null;
    }

    public List<Task> getBy(String groupName, Date olderThan) {
        //TODO: Lucka implement me please
        return null;
    }


    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        taskRepository.save(taskGroups);
    }
}
