package org.samples.todos.model



data class TaskGroup(
    val name:String,
    var tasks: MutableList<Task>
)