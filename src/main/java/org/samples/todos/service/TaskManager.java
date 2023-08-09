package org.samples.todos.service;

import org.samples.todos.model.Priority;
import org.samples.todos.model.Task;
import org.samples.todos.model.TaskGroup;
import org.samples.todos.repository.TaskRepository;

import java.util.ArrayList;
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
        for (TaskGroup taskGroup : taskGroups) {
            if (taskGroup.getName().equals(groupName)) {
                List<Task> tasks = taskGroup.getTasks();
                if (tasks != null) {
                    tasks.add(task);
                    return true;
                }
            }
        }

        System.out.println("Did not find given name of group: creating new group");
        List<Task> tasks = new ArrayList<>();
        tasks.add(task);
        TaskGroup newTaskGroup = new TaskGroup(groupName, tasks);
        taskGroups.add(newTaskGroup);
        return true;
    }

    public boolean updateTask(Task task) {
        for (TaskGroup taskGroup : taskGroups) {
            for (Task existingTask : taskGroup.getTasks()) {
                if (existingTask.getId().equals(task.getId())) {
                    existingTask.setTitle(task.getTitle());
                    existingTask.setDescription((task.getDescription()));
                    existingTask.setPriority(task.getPriority());
                    existingTask.setDone(task.isDone());
                    existingTask.setCreateDate(task.getCreateDate());
                    return true;
                }
            }
        }
        return false;
    }

    public boolean deleteTask(UUID id) {
        boolean deleted = false;

        for (TaskGroup taskGroup : taskGroups) {
            List<Task> updatedTasks = new ArrayList<>();

            for (Task task : taskGroup.getTasks()) {
                if (!task.getId().equals(id)) {
                    updatedTasks.add(task);
                } else {
                    deleted = true;
                }
            }
            taskGroup.setTasks(updatedTasks);
        }

        return deleted;
    }

    public boolean setDone(UUID id) {
        for (TaskGroup taskGroup : taskGroups) {
            for (Task task : taskGroup.getTasks()) {
                if (task.getId().equals(id)) {
                    task.setDone(true);
                    return true;
                }
            }
        }

        return false;
    }

    public List<TaskGroup> getAll() {
        return new ArrayList<>(taskGroups);
    }

    public List<Task> getBy(String groupName) {
        List<Task> tasks = new ArrayList<>();

        for (TaskGroup taskGroup : taskGroups) {
            if (taskGroup.getName().equals(groupName)) {
                tasks = taskGroup.getTasks();
            }
        }
        return tasks;
    }

    public List<Task> getBy(String groupName, Priority priority) {
        List<Task> tasks = new ArrayList<>();

        for (TaskGroup taskGroup : taskGroups) {
            if (taskGroup.getName().equals(groupName)) {
                for (Task task : taskGroup.getTasks()) {
                    if (task.getPriority() == priority) {
                        tasks.add(task);
                    }
                }
            }
        }
        return tasks;
    }

    public List<Task> getBy(String groupName, boolean done) {
        List<Task> tasks = new ArrayList<>();

        for (TaskGroup taskGroup : taskGroups) {
            if (taskGroup.getName().equals(groupName)) {
                for (Task task : taskGroup.getTasks()) {
                    if (task.isDone() == done) {
                        tasks.add(task);
                    }
                }
            }
        }
        return tasks;
    }

    public List<Task> getBy(String groupName, Date olderThan) {
        List<Task> tasks = new ArrayList<>();

        for (TaskGroup taskGroup : taskGroups) {
            if (taskGroup.getName().equals(groupName)) {
                for (Task task : taskGroup.getTasks()) {
                    if (task.getCreateDate().before(olderThan)) {
                        tasks.add(task);
                    }
                }
            }
        }
        return tasks;
    }

    public void saveTasksToFile() {
        taskRepository.save(taskGroups);
    }
}
