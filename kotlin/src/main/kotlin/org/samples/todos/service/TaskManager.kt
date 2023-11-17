package org.samples.todos.service

import org.samples.todos.model.Priority
import org.samples.todos.model.Task

import org.samples.todos.model.TaskGroup
import org.samples.todos.repository.TaskRepository
import java.util.*

class TaskManager {

    val taskRepository: TaskRepository
    val taskGroups: MutableList<TaskGroup>

    constructor(taskRepository: TaskRepository) {
        this.taskRepository = taskRepository
        this.taskGroups = taskRepository.load()
    }

    fun createTask(task: Task, groupName: String): Boolean {
        val existingGroup = taskGroups.find { it.name == groupName }

        if (existingGroup != null) {
            existingGroup.tasks.add(task)
        } else {
            println("Did not find given name of group: creating new group")
            val tasks = mutableListOf(task)
            taskGroups.add(TaskGroup(groupName, tasks))
        }
        return true
    }

    fun updateTask(task: Task): Boolean {
        val existingTask = taskGroups
            .flatMap { it -> it.tasks }
            .find { it -> it.id == task.id }

        if (existingTask != null) {
            existingTask.title = task.title
            existingTask.description = task.description
            existingTask.priority = task.priority
            existingTask.done = task.done
            existingTask.createDate = task.createDate
            return true;
        } else {
            return false;
        }
    }

    fun deleteTask(id: UUID): Boolean = taskGroups.any { it.tasks.removeIf { it.id == id } }

    fun setDone(id: UUID): Boolean {
        val task = taskGroups
            .flatMap { it.tasks }
            .filter { it.id == id }
            .firstOrNull()

        if (task != null) {
            task.done = true
            return true
        } else {
            return false
        }
    }

    fun getAll(): List<TaskGroup> = taskGroups.toList()

    fun getBy(groupName: String): List<Task> = taskGroups.firstOrNull { it.name == groupName }?.tasks ?: emptyList()

    fun getBy(groupName: String, priority: Priority): List<Task> {
        return taskGroups
            .filter { it.name == groupName }
            .flatMap { it.tasks }
            .filter { it.priority == priority }
    }

    fun getBy(groupName: String, done: Boolean): List<Task> {
        return taskGroups
            .filter { it.name == groupName }
            .flatMap { it.tasks }
            .filter { it.done == done }
    }

    fun getBy(groupName: String, olderThan: Date): List<Task> {
        return taskGroups
            .filter { it.name == groupName }
            .flatMap { it.tasks }
            .filter { it.createDate.before(olderThan) }
    }

    fun saveTasksToFile() = taskRepository.save(taskGroups)

}