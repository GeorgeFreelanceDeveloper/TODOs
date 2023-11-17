package org.samples.todos.model



data class TaskGroup(
    val name:String,
    val tasks: MutableList<Task>
)