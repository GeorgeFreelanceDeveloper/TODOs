package org.samples.todos.service

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.samples.todos.model.Priority
import org.samples.todos.model.Task
import org.samples.todos.model.TaskGroup
import org.samples.todos.repository.TaskRepository
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class TaskManagerTest {

    private var taskManager: TaskManager? = null

    private val taskRepository: TaskRepository

    private val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")

    constructor() {
        sdf.timeZone = TimeZone.getTimeZone("Europe/Prague")
        taskRepository = Mockito.mock(TaskRepository::class.java)
        Mockito.`when`(taskRepository.load()).thenReturn(createSampleTasks())
        taskManager = TaskManager(taskRepository)
    }

    @Test
    fun createTask() {
        val expectedResult: List<TaskGroup> = createExpectedResult1()
        val actualResult: List<TaskGroup>

        val result = taskManager!!.createTask(createSampleTask(), "Personal")
        actualResult = taskManager!!.getAll()

        assertTrue(result)
        assertEquals(expectedResult, actualResult)
    }

    @Throws(ParseException::class)
    private fun createSampleTasks(): MutableList<TaskGroup> {
        val task1 = Task(
            UUID.fromString("c5e128d1-24bf-4a4b-974d-72cbba71f9d3"),
            "Task 1",
            "Description for Task 1",
            Priority.LOW,
            true,
            sdf.parse("2023-08-01T10:00:00Z")
        )

        val task2 = Task(
            UUID.fromString("f327f935-89c9-43b2-9f18-87ac967035a6"),
            "Task 2",
            "Description for Task 2",
            Priority.HIGH,
            false,
            sdf.parse("2023-08-02T16:15:00Z")
        )

        val personalGroup = TaskGroup("Personal", mutableListOf(task1, task2))
        val workGroup = TaskGroup("Work", mutableListOf(task1, task2))

        return mutableListOf(personalGroup, workGroup)
    }

    @Throws(ParseException::class)
    private fun createSampleTask() = Task(
        UUID.fromString("c5e128d1-24bf-4a4b-974d-72cbba71f9d7"),
        "Sample Task",
        "Description for Sample Task",
        Priority.LOW,
        true,
        sdf.parse("2023-08-01T10:00:00Z")
    )

    @Throws(ParseException::class)
    private fun createExpectedResult1(): List<TaskGroup> {
        val taskGroups: List<TaskGroup> = createSampleTasks()
        val personalTaskGroup = taskGroups.first { it.name == "Personal" }

        val tasks: MutableList<Task> = personalTaskGroup.tasks.toMutableList()
        tasks.add(createSampleTask())
        personalTaskGroup.tasks = tasks
        return taskGroups
    }
}